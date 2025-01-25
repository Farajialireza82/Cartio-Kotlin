package com.cromulent.cartio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.ui.component.ItemInput
import com.cromulent.cartio.ui.component.ListEmptyState
import com.cromulent.cartio.ui.component.ListPageTopBar
import com.cromulent.cartio.ui.component.ShopItemRow
import com.cromulent.cartio.viewmodel.ListPageViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import org.koin.androidx.compose.koinViewModel
import java.time.Duration

@Composable
fun ListPage(
    modifier: Modifier = Modifier
        .imePadding()
    ,
    viewModel: ListPageViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItems()
    }

    Scaffold(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xCCF0FDF4),
                        Color(0x80F0FDF4),
                        Color(0xFFF3F4F6)
                    )
                )
            ),
        containerColor = Color.Transparent,
        topBar = { ListPageTopBar(shopItemCount = state.shopItems.size) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = state.shopItems.isEmpty(),
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ListEmptyState(
                            modifier = Modifier
                                .padding(horizontal = 24.dp),
                            icon = Icons.AutoMirrored.Filled.List,
                            title = "Start your shopping list",
                            description = "Add items you need to buy and share the list with others",
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = state.shopItems.isNotEmpty(),
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = rememberLazyListState()
                    ) {
                        items(
                            items = state.shopItems.sortedBy { it.isBought },
                            key = { it.id ?: it.hashCode() },
                        ) { item ->
                            AnimatedShopItem(
                                item = item,
                                onItemChanged = { shopItem ->
                                    viewModel.editShopItem(shopItem)
                                },
                                onDeleteClicked = { id ->
                                    viewModel.deleteShopItem(id)
                                }
                            )
                        }
                    }
                }
            }

            ItemInput(
                modifier = Modifier
                    .padding(16.dp)
            ) { name, quantity ->
                viewModel.addItem(name, quantity)
            }
        }
    }
}


@Composable
private fun AnimatedShopItem(
    item: ShopItem,
    onItemChanged: (ShopItem) -> Unit,
    onDeleteClicked: (Long?) -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    var shouldAnimate by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        shouldAnimate = true
    }

    AnimatedVisibility(
        visible = isVisible && shouldAnimate,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) +
                slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
    ) {
        Column {
            ShopItemRow(
                shopItem = item,
                onItemChanged = onItemChanged,
                onDeleteClicked = { id ->
                    isVisible = false
                    coroutineScope.launch {
                        delay(Duration.ofMillis(300))
                        onDeleteClicked(id)
                    }
                }
            )
            if (!item.isBought) {
                HorizontalDivider(
                    modifier = Modifier.alpha(0.4f),
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun ListPagePrev() {
    ListPage()
}