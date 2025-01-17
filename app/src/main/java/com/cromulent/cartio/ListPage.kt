package com.cromulent.cartio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cromulent.cartio.ui.component.ItemInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(text = "Household Groceries")
                        Text(
                            text = "2 items",
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                }
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            ItemInput(
                modifier = Modifier
                    .padding(16.dp),
            ) { }

        }

    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
private fun ListPagePrev() {
    ListPage()
}