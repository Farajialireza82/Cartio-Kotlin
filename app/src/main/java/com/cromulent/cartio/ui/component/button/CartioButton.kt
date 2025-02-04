package com.cromulent.cartio.ui.component.button

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cromulent.cartio.ui.theme.CartioTheme
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

@Composable
fun CartioButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    title: String? = null,
    icon: ImageVector? = null,
    titleSize: TextUnit? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        modifier = modifier
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
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.4f)
        ),
    ) {
        if (!isLoading) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                icon?.let {
                    Icon(
                        it,
                        null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                title?.let {
                    Text(
                        text = title,
                        fontSize = titleSize ?: 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            DotLottieAnimation(
                source = DotLottieSource.Asset("loading.json"), // from asset .lottie / .json
                autoplay = true,
                loop = true,
                speed = 1f,
                useFrameInterpolation = false,
                playMode = Mode.FORWARD,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun CartioButtonPrev() {
    CartioTheme {
        CartioButton(
            title = "Add Item",
            icon = Icons.Default.Add,
            onClick = {}
        )
    }

}