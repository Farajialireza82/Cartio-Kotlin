package com.cromulent.cartio.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cromulent.cartio.R
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                AnimatedVisibility(shopItemCount != 0 || selectedItemCount != 0) {
                    Text(
                        text = if (selectedItemCount != 0)
                            stringResource(R.string.selected, getItemsText(selectedItemCount))
                        else getItemsText(shopItemCount),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        navigationIcon = {
            AnimatedVisibility(selectedItemCount != 0) {
                IconButton(onClick = onClearClicked) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear selection",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = {
            Row {
                AnimatedVisibility(shopItemCount != 0) {
                    IconButton(onClick = onShareClicked) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share list",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                AnimatedVisibility(selectedItemCount != 0) {
                    Row {
                        IconButton(onClick = onDeleteClicked) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete items",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = onMarkAsBoughtClicked) {
                            Icon(
                                Icons.Default.Done,
                                contentDescription = "Mark as done",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )
}