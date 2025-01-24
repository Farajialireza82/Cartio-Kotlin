package com.cromulent.cartio

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cromulent.cartio.ui.component.ItemInput
import com.cromulent.cartio.ui.component.ListPageTopBar
import com.cromulent.cartio.ui.component.ShopItemRow
import com.cromulent.cartio.viewmodel.ListPageViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    viewModel: ListPageViewModel = koinViewModel()
) {

    val state by viewModel.items.collectAsState()

    viewModel.loadItems()

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

        topBar = { ListPageTopBar(shopItemCount = state.size) }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(state) {
                    ShopItemRow(
                        shopItem = it,
                        modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                        onItemChanged = { shopItem ->
                            viewModel.editShopItem(shopItem)
                        })
                }
            }

            ItemInput(
                modifier = Modifier
                    .padding(16.dp),
            ) { name, quantity ->
                viewModel.addItem(
                    name = name,
                    quantity = quantity
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