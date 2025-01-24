package com.cromulent.cartio.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
            Column {
                Text(
                    text = "Household Groceries",
                    color = Color(0xff1F2937),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$shopItemCount items",
                    fontSize = 18.sp,
                    color = Color(0xFF374151),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .background(color = Color.Transparent)
    )
}