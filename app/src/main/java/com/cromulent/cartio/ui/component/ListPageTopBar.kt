package com.cromulent.cartio.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPageTopBar(modifier: Modifier = Modifier, shopItemCount: Int) {
    LargeTopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Household Groceries",
                    color = Color(0xff1F2937),
                    fontWeight = FontWeight.Medium
                )
                AnimatedVisibility(shopItemCount != 0) {
                    Text(
                        text = "$shopItemCount items",
                        fontSize = 18.sp,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        actions = {
            AnimatedVisibility(shopItemCount != 0) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Default.Share, "")
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .background(color = Color.Transparent)
    )
}