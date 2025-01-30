package com.cromulent.cartio.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cromulent.cartio.CartioRoutes
import com.cromulent.cartio.state.LoginScreenUiMode
import com.cromulent.cartio.ui.component.textField.CartioButton
import com.cromulent.cartio.ui.component.textField.CartioTextField
import com.cromulent.cartio.ui.theme.CartioTheme
import com.cromulent.cartio.viewmodel.LoginScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val viewModel: LoginScreenViewModel = koinViewModel()

    val state by viewModel.state.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = Color(0xFFf7f9fa))
                .padding(vertical = 24.dp, horizontal = 48.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Welcome back",
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Please enter your details",
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            CartioTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                value = username,
                onValueChanged = { username = it },
                icon = Icons.Outlined.Email,
                placeHolderText = "Username or email"
            )
            CartioTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                value = password,
                onValueChanged = { password = it },
                icon = Icons.Outlined.Lock,
                placeHolderText = "Password"
            )
            Spacer(modifier = Modifier.size(8.dp))

            CartioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                title = "Sign in",
                titleSize = 16.sp,
                icon = null,
            ) {
                viewModel.userLogin(username, password)
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row() {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(CartioRoutes.SIGNUP.name)
                        }
                )
            }

        }
    }
    if(state.uiMode == LoginScreenUiMode.SUCCESS){
        navController.popBackStack()
        navController.navigate(CartioRoutes.LIST.name)
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun SignUpPrev() {
    CartioTheme {
        LoginScreen(navController = rememberNavController())
    }
}