package com.cromulent.cartio.ui.component.textField

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cromulent.cartio.ui.theme.CartioTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.LayoutDirection
import com.cromulent.cartio.R
import com.cromulent.cartio.utils.getDeviceLayoutDirection

@Composable
fun ItemInput(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    onAddClicked: (name: String, quantity: String) -> Unit
) {
    var shopItemText by remember { mutableStateOf("") }
    var quantityText by remember { mutableStateOf("") }
    var showQuantity by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                modifier = Modifier.padding(bottom = if (showQuantity) 8.dp else 0.dp)
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides getDeviceLayoutDirection()) {
                    TextField(
                        value = shopItemText,
                        onValueChange = { shopItemText = it },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        suffix = {
                            if (shopItemText.isNotBlank()) {
                                Text(
                                    text = stringResource(R.string.qty),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.clickable { showQuantity = !showQuantity }
                                )
                            }
                        },
                        placeholder = { Text(stringResource(R.string.add_item)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                onAddClicked(shopItemText, quantityText)
                                shopItemText = ""
                                quantityText = ""
                                showQuantity = false
                            }
                        )
                    )
                }

                CartioButton(
                    modifier = Modifier
                        .size(54.dp),
                    enabled = shopItemText.isNotBlank(),
                    title = "",
                    icon = Icons.Default.Add
                ) {
                    onAddClicked(
                        shopItemText,
                        quantityText
                    )
                    shopItemText = ""
                    quantityText = ""
                    showQuantity = false
                }
            }
        }

        AnimatedVisibility(showQuantity) {
            TextField(
                value = quantityText,
                onValueChange = { quantityText = it },
                modifier = textFieldModifier
                    .height(54.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text(stringResource(R.string.quantity)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onAddClicked(shopItemText, quantityText)
                        shopItemText = ""
                        quantityText = ""
                        showQuantity = false
                    }
                )
            )
        }
    }
}


@Preview(showBackground = true, locale = "fa")
@Composable
private fun ItemInputPrev() {
    CartioTheme {
        ItemInput { _, _ -> }
    }
}