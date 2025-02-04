package com.cromulent.cartio.ui.component.textField

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cromulent.cartio.R
import com.cromulent.cartio.ui.theme.CartioTheme

@Composable
fun CartioTextField(
    modifier: Modifier = Modifier,
    value: String,
    type: TextFieldType = TextFieldType.NORMAL,
    isError: Boolean = false,
    @StringRes errorMessage: Int = -1,
    onValueChanged: (value: String) -> Unit,
    icon: ImageVector,
    placeHolderText: String
) {
    // State to manage password visibility
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            isError = isError,
            onValueChange = onValueChanged,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                errorContainerColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(16.dp),
            prefix = {
                Icon(
                    icon,
                    null,
                    modifier = Modifier
                        .padding(end = 4.dp),
                    tint = Color.Gray
                )
            },
            placeholder = {
                Text(placeHolderText)
            },
            // Handle visual transformation for password fields
            visualTransformation = when (type) {
                TextFieldType.PASSWORD -> if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            // Handle keyboard options based on the type
            keyboardOptions = when (type) {
                TextFieldType.EMAIL -> KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                TextFieldType.PASSWORD -> KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                else -> KeyboardOptions.Default
            },
            // Add a trailing icon for password fields to toggle visibility
            trailingIcon = {
                if (type == TextFieldType.PASSWORD) {
                    val image =
                        if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                    IconButton(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
        )
        if (isError) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 8.dp),
                text = stringResource(errorMessage),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

enum class TextFieldType {
    NORMAL, EMAIL, PASSWORD
}

@Preview
@Composable
private fun TextFieldPrev() {

    var textInput by remember { mutableStateOf("") }

    CartioTheme {
        CartioTextField(
            modifier = Modifier
                .fillMaxWidth(),
            isError = true,
            errorMessage = R.string.invalid_email_address,
            value = textInput,
            onValueChanged = { textInput = it },
            icon = Icons.Outlined.Email,
            placeHolderText = "Email address"
        )
    }

}