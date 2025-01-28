package com.cromulent.cartio.ui.component.textField

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CartioTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (value: String) -> Unit,
    icon: ImageVector,
    placeHolderText: String
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color(0xFFe4e6eb),
            unfocusedIndicatorColor = Color(0xFFe4e6eb),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
        shape = RoundedCornerShape(16.dp),
        prefix = {
            Icon(
                icon,
                null,
                modifier = Modifier
                    .padding(end = 4.dp),
                tint = Color(0xFF9da4b0)
            )
        },
        placeholder = {
            Text(placeHolderText)
        }

    )
}