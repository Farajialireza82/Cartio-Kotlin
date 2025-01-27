package com.cromulent.cartio.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.ui.theme.CartioTheme

@Composable
fun ShopItemRow(
    modifier: Modifier = Modifier,
    shopItem: ShopItem,
    isSelectionMode: Boolean = false,
    onItemChanged: (shopItem: ShopItem) -> Unit,
    onDeleteClicked: (itemId: Long?) -> Unit
) {
    var isChecked by remember { mutableStateOf(shopItem.isBought) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(!isSelectionMode) {
                RoundedCornerCheckbox(
                    modifier = Modifier.padding(end = 8.dp),
                    isChecked = isChecked,
                    onValueChange = {
                        isChecked = !isChecked
                        shopItem.isBought = !shopItem.isBought
                        onItemChanged(shopItem.copy())
                    }
                )
            }

            Text(
                text = shopItem.name,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isChecked)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else
                    MaterialTheme.colorScheme.onSurface,
                textDecoration = if (isChecked) TextDecoration.LineThrough else null
            )

            if (!shopItem.amount.isNullOrEmpty() && shopItem.amount != "0") {
                Text(
                    text = shopItem.amount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        AnimatedVisibility(!isSelectionMode) {
            IconButton(onClick = { onDeleteClicked(shopItem.id) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete item",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(end = 16.dp)
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
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(
        targetValue = if (isChecked)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surface
    )

    val density = LocalDensity.current
    val duration = 200

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.toggleable(
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
                    shape = RoundedCornerShape(percent = 100)
                )
                .border(
                    width = 1.5.dp,
                    color = if (isChecked)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(percent = 100)
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
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun ShopItemRowPrev() {
    CartioTheme(
        darkTheme = true
    ) {
        ShopItemRow(
            shopItem = ShopItem(12L, "Bread", "2 Loaves", isBought = true),
            onItemChanged = {}
        ) { _ ->

        }
    }
}