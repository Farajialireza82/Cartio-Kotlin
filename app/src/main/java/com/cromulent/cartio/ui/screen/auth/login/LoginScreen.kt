package com.cromulent.cartio.ui.screen.auth.login

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cromulent.cartio.CartioRoutes
import com.cromulent.cartio.R
import com.cromulent.cartio.ui.component.button.CartioButton
import com.cromulent.cartio.ui.component.textField.CartioTextField
import com.cromulent.cartio.ui.component.textField.TextFieldType
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenUiMode.ERROR
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenUiMode.LOADING
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenUiMode.NORMAL
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenUiMode.SUCCESS
import com.cromulent.cartio.ui.screen.auth.signup.isValidEmail
import com.cromulent.cartio.ui.screen.auth.signup.isValidPassword
import com.cromulent.cartio.ui.theme.CartioTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val viewModel: LoginScreenViewModel = koinViewModel()

    val state by viewModel.state.collectAsState()


    var usernameInputText by rememberSaveable { mutableStateOf("") }
    var usernameInputIsError by remember { mutableStateOf(false) }
    var usernameInputErrorMessage by remember { mutableIntStateOf(R.string.enter_an_email_address) }

    var passwordInputText by rememberSaveable { mutableStateOf("") }
    var passwordInputIsError by remember { mutableStateOf(false) }
    var passwordInputErrorMessage by remember { mutableIntStateOf(R.string.enter_a_password) }

    var showButtonLoading by remember { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 48.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.welcome_back),
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

            Text(
                text = stringResource(R.string.please_enter_your_details),
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            CartioTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                type = TextFieldType.EMAIL,
                value = usernameInputText,
                onValueChanged = { usernameInputText = it },
                isError = usernameInputIsError,
                errorMessage = usernameInputErrorMessage,
                icon = Icons.Outlined.Email,
                placeHolderText = stringResource(R.string.username_or_email)
            )

            Spacer(modifier = Modifier.size(16.dp))

            CartioTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                type = TextFieldType.PASSWORD,
                value = passwordInputText,
                onValueChanged = { passwordInputText = it },
                isError = passwordInputIsError,
                errorMessage = passwordInputErrorMessage,
                icon = Icons.Outlined.Lock,
                placeHolderText = stringResource(R.string.title_password)
            )

            Spacer(modifier = Modifier.size(16.dp))

            CartioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                isLoading = showButtonLoading,
                title = "Sign in",
                titleSize = 16.sp,
                icon = null,
            ) {

                //Validate email input
                if (usernameInputText.isEmpty()) {
                    usernameInputIsError = true
                    usernameInputErrorMessage = R.string.enter_an_email_address
                    return@CartioButton
                } else if (!usernameInputText.isValidEmail()) {
                    usernameInputIsError = true
                    usernameInputErrorMessage = R.string.invalid_email_address
                    return@CartioButton
                } else usernameInputIsError = false

                //Validate password input
                if (passwordInputText.isEmpty()) {
                    passwordInputIsError = true
                    passwordInputErrorMessage = R.string.enter_a_password
                    return@CartioButton
                } else if (passwordInputText.length < 8) {
                    passwordInputIsError = true
                    passwordInputErrorMessage =
                        R.string.passwords_must_contain_at_least_8_characters
                    return@CartioButton
                } else if (!passwordInputText.isValidPassword()) {
                    passwordInputIsError = true
                    passwordInputErrorMessage =
                        R.string.passwords_must_contain_at_least_one_letter_and_one_number
                    return@CartioButton
                } else passwordInputIsError = false

                viewModel.userLogin(usernameInputText, passwordInputText)
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row() {
                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(R.string.sign_up),
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
    when (state.uiMode) {
        SUCCESS -> {
            showButtonLoading = false
            navController.popBackStack()
            navController.navigate(CartioRoutes.LIST.name)
        }

        NORMAL -> {
            showButtonLoading = false
        }

        ERROR -> {
            showButtonLoading = false
            state.snackMessage?.let { message ->
                LaunchedEffect(state.uiMode) {
                    snackBarHostState.showSnackbar(message)
                }
            }

        }

        LOADING -> {
            showButtonLoading = true

        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun SignUpPrev() {
    CartioTheme {
        LoginScreen(navController = rememberNavController())
    }
}