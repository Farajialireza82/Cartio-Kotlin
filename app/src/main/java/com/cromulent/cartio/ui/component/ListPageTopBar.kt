package com.cromulent.cartio.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cromulent.cartio.state.ListPageUiMode
import com.cromulent.cartio.utils.getItemsText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPageTopBar(
    modifier: Modifier = Modifier,
    title: String,
    shopItemCount: Int,
    selectedItemCount: Int,
    onDeleteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onClearClicked: () -> Unit,
    onMarkAsBoughtClicked: () -> Unit
) {


    LargeTopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    color = Color(0xff1F2937),
                    fontWeight = FontWeight.Medium
                )
                AnimatedVisibility(shopItemCount != 0 || selectedItemCount != 0) {
                    Text(
                        text = if (selectedItemCount != 0)
                            "${getItemsText(selectedItemCount)} selected"
                        else getItemsText(shopItemCount),

                        fontSize = 18.sp,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        navigationIcon = {
            AnimatedVisibility(selectedItemCount != 0) {
                IconButton(
                    onClick = { onClearClicked() }
                ) {
                    Icon(Icons.Default.Close, "Close")
                }
            }
        },
        actions = {
            Row {

                androidx.compose.animation.AnimatedVisibility(shopItemCount != 0) {

                    IconButton(
                        onClick = { onShareClicked() }
                    ) { Icon(Icons.Default.Share, "") }

                }

                AnimatedVisibility(selectedItemCount != 0) {
                    Row {
                        IconButton(
                            onClick = {
                                onDeleteClicked()
                            }
                        ) {
                            Icon(Icons.Default.Delete, "Delete")
                        }
                        IconButton(
                            onClick = { onMarkAsBoughtClicked() }
                        ) {
                            Icon(Icons.Default.Done, "Mark as done")
                        }
                    }
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