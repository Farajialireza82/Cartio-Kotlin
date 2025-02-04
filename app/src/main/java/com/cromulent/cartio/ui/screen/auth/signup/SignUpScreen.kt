package com.cromulent.cartio.ui.screen.auth.signup

import android.util.Patterns
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cromulent.cartio.CartioRoutes
import com.cromulent.cartio.R
import com.cromulent.cartio.ui.screen.auth.login.LoginScreenUiMode
import com.cromulent.cartio.ui.component.common.ListPageTopBar
import com.cromulent.cartio.ui.component.button.CartioButton
import com.cromulent.cartio.ui.component.textField.CartioTextField
import com.cromulent.cartio.ui.component.textField.TextFieldType
import com.cromulent.cartio.ui.screen.auth.signup.SignupScreenUiMode.*
import com.cromulent.cartio.ui.theme.CartioTheme
import com.cromulent.cartio.ui.theme.listPageGradient
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val viewModel: SignupViewModel = koinViewModel<SignupViewModel>()

    val state by viewModel.state.collectAsState()

    var showButtonLoading by remember { mutableStateOf(false) }

    var fullNameInputText by remember { mutableStateOf("") }
    var fullNameInputIsError by remember { mutableStateOf(false) }
    var fullNameInputErrorMessage by remember { mutableIntStateOf(R.string.enter_a_name) }

    var emailInputText by remember { mutableStateOf("") }
    var emailInputIsError by remember { mutableStateOf(false) }
    var emailInputErrorMessage by remember { mutableIntStateOf(R.string.enter_an_email_address) }

    var passwordInputText by remember { mutableStateOf("FE") }
    var passwordInputIsError by remember { mutableStateOf(false) }
    var passwordInputErrorMessage by remember { mutableIntStateOf(R.string.enter_a_password) }

    var confirmPasswordInputText by remember { mutableStateOf("qw123456") }
    var confirmPasswordInputIsError by remember { mutableStateOf(false) }
    var confirmPasswordInputErrorMessage by remember { mutableIntStateOf(R.string.enter_a_password) }

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            ListPageTopBar(
                title = stringResource(R.string.create_account),
                description = stringResource(R.string.join_cartio_today)
            )
        },
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CartioTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = fullNameInputText,
                onValueChanged = { fullNameInputText = it },
                isError = fullNameInputIsError,
                errorMessage = fullNameInputErrorMessage,
                icon = Icons.Outlined.Face,
                placeHolderText = stringResource(R.string.full_name)
            )

            Spacer(modifier = Modifier.size(16.dp))

            CartioTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                type = TextFieldType.EMAIL,
                value = emailInputText,
                isError = emailInputIsError,
                errorMessage = emailInputErrorMessage,
                onValueChanged = { emailInputText = it },
                icon = Icons.Outlined.Email,
                placeHolderText = stringResource(R.string.title_email)
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
                placeHolderText = stringResource(R.string.create_password)
            )

            Spacer(modifier = Modifier.size(16.dp))


            CartioTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                type = TextFieldType.PASSWORD,
                value = confirmPasswordInputText,
                onValueChanged = { confirmPasswordInputText = it },
                isError = confirmPasswordInputIsError,
                errorMessage = confirmPasswordInputErrorMessage,
                icon = Icons.Outlined.Lock,
                placeHolderText = stringResource(R.string.confirm_password)
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = stringResource(R.string.create_account_agree_tos),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            CartioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                isLoading = showButtonLoading,
                title = stringResource(R.string.title_create_account),
                titleSize = 16.sp,
                icon = null,
            ) {

                //Validate full name input
                if (fullNameInputText.isEmpty()) {
                    fullNameInputIsError = true
                    fullNameInputErrorMessage = R.string.enter_a_name
                    return@CartioButton
                } else fullNameInputIsError = false

                //Validate email input
                if (emailInputText.isEmpty()) {
                    emailInputIsError = true
                    emailInputErrorMessage = R.string.enter_an_email_address
                    return@CartioButton
                } else if (!emailInputText.isValidEmail()) {
                    emailInputIsError = true
                    emailInputErrorMessage = R.string.invalid_email_address
                    return@CartioButton
                } else emailInputIsError = false

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

                //Validate confirm password
                //Validate password input
                if (confirmPasswordInputText.isEmpty()) {
                    confirmPasswordInputIsError = true
                    confirmPasswordInputErrorMessage = R.string.confirm_your_password
                    return@CartioButton
                } else if (confirmPasswordInputText != passwordInputText) {
                    confirmPasswordInputIsError = true
                    confirmPasswordInputErrorMessage =
                        R.string.passwords_not_the_same
                    return@CartioButton
                } else confirmPasswordInputIsError = false

                viewModel.createUser(
                    fullName = fullNameInputText,
                    username = emailInputText,
                    password = passwordInputText
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row {
                Text(
                    text = stringResource(R.string.already_have_an_account),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(R.string.sign_in),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(CartioRoutes.LOGIN.name)
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

        LOADING -> {
            showButtonLoading = true

        }

        ERROR -> {
            showButtonLoading = false
            LaunchedEffect(state.uiMode) {
                state.errorMessage?.let {
                    snackBarHostState.showSnackbar(it)
                }
            }
        }

        NORMAL -> {
            showButtonLoading = false

        }
    }
}

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence?.isValidPassword(): Boolean {
    return !isNullOrEmpty() && length >= 8 && contains(Regex(".*[A-Za-z].*")) && contains(Regex(".*\\d.*"))
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun SignUpPrev() {
    CartioTheme {
        SignUpScreen(
            navController = rememberNavController()
        )
    }
}