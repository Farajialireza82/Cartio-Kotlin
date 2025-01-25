package com.cromulent.cartio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cromulent.cartio.ui.component.ItemInput
import com.cromulent.cartio.ui.component.ListEmptyState
import com.cromulent.cartio.ui.component.ListPageTopBar
import com.cromulent.cartio.ui.component.ShopItemRow
import com.cromulent.cartio.viewmodel.ListPageViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListPage(
    modifier: Modifier = Modifier,
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
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.shopItems.sortedBy { it.isBought },
                            key = { it.id!! },
                            // Add enter/exit animations for individual items
                            itemContent = { item ->
                                AnimatedItemContent {
                                    Column {
                                        ShopItemRow(
                                            shopItem = item,
                                            modifier = Modifier.animateContentSize(
                                                animationSpec = spring(
                                                    dampingRatio = 0.8f,
                                                    stiffness = Spring.StiffnessLow
                                                )
                                            ),
                                            onItemChanged = { shopItem ->
                                                viewModel.editShopItem(shopItem)
                                            },
                                            onDeleteClicked = { id ->
                                                viewModel.deleteShopItem(id)
                                            }
                                        )
                                        if (state.shopItems.last() != item) {
                                            HorizontalDivider(
                                                modifier = Modifier.alpha(0.4f),
                                                color = Color.LightGray
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }

            ItemInput(
                modifier = Modifier
                    .padding(16.dp)
            ) { name, quantity ->
                viewModel.addItem(
                    name = name,
                    quantity = quantity
                )
            }
        }
    }
}

@Composable
private fun AnimatedItemContent(
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(
                dampingRatio = 0.8f,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = spring(
                dampingRatio = 0.8f,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeOut(),
        content = content
    )
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun ListPagePrev() {
    ListPage()
}