package com.cromulent.cartio.ui.component

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cromulent.cartio.ui.theme.CartioTheme
import androidx.compose.animation.core.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventType


@Composable
fun ItemInput(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    onAddClicked: (name: String, quantity: String) -> Unit
) {

    val colors = object {
        val background = Color.Transparent
        val container = Color(0xFFE8E8EA)
        val text = Color(0xFF111827)
        val placeholder = Color(0xFF6B7280)
        val primary = Color(0xFF16A34A)
        val primaryDisabled = Color(0x6616A34A)
    }
    var shopItemText by remember { mutableStateOf("") }
    var quantityText by remember { mutableStateOf("") }
    var showQuantity by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        Row(
            modifier =
            Modifier
                .padding(bottom = if (showQuantity) 8.dp else 0.dp)
        ) {

            TextField(
                value = shopItemText,
                onValueChange = { shopItemText = it },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = colors.container,
                    focusedContainerColor = colors.container,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colors.text,
                    unfocusedPlaceholderColor = colors.placeholder,
                    focusedPlaceholderColor = colors.placeholder,
                    cursorColor = colors.primary
                ),
                suffix = {
                    if (shopItemText.isNotBlank()) {
                        Text(
                            text = "QTY",
                            color = colors.placeholder,
                            modifier = Modifier
                                .clickable {
                                    showQuantity = !showQuantity
                                }
                        )
                    }
                },
                placeholder = { Text("Add item...") }
            )

            Button(
                onClick = {
                    onAddClicked(
                        shopItemText,
                        quantityText
                    )
                    shopItemText = ""
                    quantityText = ""
                    showQuantity = false
                },
                shape = RoundedCornerShape(16.dp),
                enabled = shopItemText.isNotBlank(),
                modifier = Modifier
                    .size(54.dp)
                    .graphicsLayer {
                        scaleX = 1f
                        scaleY = 1f
                    }
                    .composed {
                        var isHovered by remember { mutableStateOf(false) }
                        var isPressed by remember { mutableStateOf(false) }

                        pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    when (event.type) {
                                        PointerEventType.Enter -> {
                                            isHovered = true
                                        }
                                        PointerEventType.Exit -> {
                                            isHovered = false
                                        }
                                        PointerEventType.Press -> {
                                            isPressed = true
                                        }
                                        PointerEventType.Release -> {
                                            isPressed = false
                                        }
                                    }
                                }
                            }
                        }
                            .scale(
                                animateFloatAsState(
                                    targetValue = when {
                                        isPressed -> 0.9f
                                        isHovered -> 1.1f
                                        else -> 1f
                                    },
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                ).value
                            )
                            .graphicsLayer {
                                shadowElevation = if (isHovered && !isPressed) 8f else 0f
                                shape = RoundedCornerShape(16.dp)
                                clip = true
                            }
                    },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = colors.primary,
                    disabledContainerColor = colors.primaryDisabled
                ),
            ) {
                Icon(
                    Icons.Default.Add,
                    null,
                    tint = Color.White
                )
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
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = colors.container,
                    focusedContainerColor = colors.container,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colors.text,
                    unfocusedPlaceholderColor = colors.placeholder,
                    focusedPlaceholderColor = colors.placeholder,
                    cursorColor = colors.primary
                ),
                placeholder = { Text("Quantity...") }
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun ItemInputPrev() {
    CartioTheme {
        val focusRequester = remember { FocusRequester() }
        ItemInput { _, _ -> }
    }
}