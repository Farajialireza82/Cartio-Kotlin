package com.cromulent.cartio.ui.component.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPageTopBar(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    showClearButton: Boolean = false,
    showShareButton: Boolean = false,
    showDoneButton: Boolean = false,
    showDeleteButton: Boolean = false,
    showLogoutButton: Boolean = false,
    onLogoutClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onClearClicked: () -> Unit = {},
    onMarkAsBoughtClicked: () -> Unit = {}
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
                AnimatedVisibility(description.isNotBlank()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        navigationIcon = {
            Row {
                AnimatedVisibility(showClearButton) {
                    IconButton(onClick = onClearClicked) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Clear selection",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                AnimatedVisibility(!showClearButton && showLogoutButton){
                    IconButton(onLogoutClicked) {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        actions = {
            Row {

                AnimatedVisibility(showDoneButton) {
                    IconButton(onClick = onMarkAsBoughtClicked) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = "Mark as done",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                AnimatedVisibility(showShareButton) {
                    IconButton(onClick = onShareClicked) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share list",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                AnimatedVisibility(showDeleteButton) {
                    IconButton(onClick = onDeleteClicked) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete items",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
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