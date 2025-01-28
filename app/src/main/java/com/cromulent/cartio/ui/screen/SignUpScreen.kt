package com.cromulent.cartio.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.cromulent.cartio.ui.component.ListPageTopBar
import com.cromulent.cartio.ui.component.textField.CartioButton
import com.cromulent.cartio.ui.component.textField.CartioTextField
import com.cromulent.cartio.ui.theme.CartioTheme
import com.cromulent.cartio.ui.theme.listPageGradient
import com.cromulent.cartio.viewmodel.SignupViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {

    val viewModel: SignupViewModel = koinViewModel<SignupViewModel>()

    var fullName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var createPassword by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            ListPageTopBar(
                modifier = modifier
                    .background(
                        brush = listPageGradient()
                    ),
                title = "Create Account",
                description = "Join Cartio today"
            )
        },
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .background(color = Color(0xFFf7f9fa))
                .padding(vertical = 24.dp, horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CartioTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                value = fullName,
                onValueChanged = { fullName = it },
                icon = Icons.Outlined.Face,
                placeHolderText = "Full name"
            )
            CartioTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                value = emailAddress,
                onValueChanged = { emailAddress = it },
                icon = Icons.Outlined.Email,
                placeHolderText = "Email address"
            )
            CartioTextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                value = createPassword,
                onValueChanged = { createPassword = it },
                icon = Icons.Outlined.Lock,
                placeHolderText = "Create password"
            )
            Text(
                text = "By creating an account, you agree to our Terms of Service and Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                )

            Spacer(modifier = Modifier.size(8.dp))

            CartioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                title = "Create account",
                titleSize = 16.sp,
                icon = null,
            ) {
                viewModel.createUser(
                    fullName = fullName,
                    username = emailAddress,
                    password = createPassword
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row {
                Text(
                    text = "Already have an account? " ,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Sign in" ,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .clickable {
                        }
                )
            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun SignUpPrev() {
    CartioTheme {
        SignUpScreen()
    }
}