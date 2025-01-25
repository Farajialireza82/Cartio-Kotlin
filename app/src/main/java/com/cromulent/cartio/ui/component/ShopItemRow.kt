package com.cromulent.cartio.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cromulent.cartio.data.ShopItem

@Composable
fun ShopItemRow(
    shopItem: ShopItem,
    modifier: Modifier = Modifier,
    onItemChanged: (shopItem: ShopItem) -> Unit,
    onDeleteClicked: (itemId: Long?) -> Unit
) {
    var isChecked by remember { mutableStateOf(shopItem.isBought) }

    val colors = object {
        val background = Color.Transparent
        val container = Color(0xFFE8E8EA)
        val text = Color(0xFF111827)
        val placeholder = Color(0xFF6B7280)
        val primary = Color(0xFF16A34A)
        val primaryDisabled = Color(0x6616A34A)
    }

    Column {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCornerCheckbox(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp),
                    isChecked = isChecked,
                    checkedColor = colors.primary,
                    uncheckedBorderColor = Color(0xFF9CA3AF)
                ) {
                    isChecked = !isChecked
                    shopItem.isBought = !shopItem.isBought
                    onItemChanged(shopItem.copy())
                }
                Text(
                    shopItem.name,
                    fontSize = 18.sp,
                    color = if (isChecked) Color(0xFF9CA3AF) else Color(0xFF111827),
                    textDecoration = if (isChecked) TextDecoration.LineThrough else null
                )
                if (shopItem.amount != "0" && !shopItem.amount.isNullOrEmpty()) {
                    Text(
                        shopItem.amount.toString(),
                        fontSize = 12.sp,
                        color = Color(0xFF4B5563),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            }
            IconButton(
                onClick = { onDeleteClicked(shopItem.id) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color(0xFFE75959),
                    modifier = Modifier
                        .padding(end = 16.dp)
                )
            }
        }
    }

}

@Composable
fun RoundedCornerCheckbox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    size: Float = 24f,
    checkedColor: Color,
    uncheckedBorderColor: Color,
    uncheckedColor: Color = Color.White,
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor)
    val density = LocalDensity.current
    val duration = 200

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onValueChange
            )
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(
                    color = checkboxColor,
                    shape = RoundedCornerShape(100)
                )
                .border(
                    width = 1.5.dp,
                    color = if (isChecked) checkedColor else uncheckedBorderColor,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) { (size * -0.5).dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration)
                ),
                exit = fadeOut()
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = uncheckedColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun ShopItemRowPrev() {
    ShopItemRow(
        shopItem = ShopItem(12L, "Bread", "2 Loaves", isBought = true),
        onItemChanged = {}
    ) { _ ->

    }
}