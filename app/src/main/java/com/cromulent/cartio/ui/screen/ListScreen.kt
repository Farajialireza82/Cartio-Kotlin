package com.cromulent.cartio.ui.screen

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cromulent.cartio.CartioRoutes
import com.cromulent.cartio.R
import com.cromulent.cartio.data.ShopItem
import com.cromulent.cartio.state.ListPageUiMode
import com.cromulent.cartio.ui.component.textField.ItemInput
import com.cromulent.cartio.ui.component.ListEmptyState
import com.cromulent.cartio.ui.component.ListPageTopBar
import com.cromulent.cartio.ui.component.ShopItemRow
import com.cromulent.cartio.ui.component.dialog.ConfirmDialog
import com.cromulent.cartio.ui.theme.listPageGradient
import com.cromulent.cartio.utils.getItemsText
import com.cromulent.cartio.viewmodel.ListPageViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import org.koin.androidx.compose.koinViewModel
import java.time.Duration

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier
        .imePadding(),
    viewModel: ListPageViewModel = koinViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    var showConfirmDeleteDialog by remember { mutableStateOf(false) }
    var showConfirmLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadItems()
    }

    if(state.uiMode == ListPageUiMode.LOGOUT){
        navController.navigate(CartioRoutes.LOGIN.name)
        navController.popBackStack()
    }

    Scaffold(
        modifier = modifier
            .background(
                brush = listPageGradient()
            ),
        containerColor = Color.Transparent,
        topBar = {
            ListPageTopBar(
                title = stringResource(R.string.household_groceries),
                showShareButton = state.shopItems.isNotEmpty(),
                description = if (state.shopItems.isEmpty()) "" else if (state.selectedItems.isNotEmpty())
                    stringResource(R.string.selected, getItemsText(state.selectedItems.size))
                else getItemsText(state.shopItems.size),
                showClearButton = state.selectedItems.isNotEmpty(),
                showLogoutButton = true,
                showDoneButton = state.selectedItems.any { !it.isBought },
                showDeleteButton = state.selectedItems.isNotEmpty(),
                onLogoutClicked = {
                    showConfirmLogoutDialog = true
                },
                onDeleteClicked = {
                    showConfirmDeleteDialog = true
                },
                onMarkAsBoughtClicked = {
                    viewModel.markSelectedAsBought(state.selectedItems)
                    viewModel.clearItems()
                },
                onClearClicked = { viewModel.clearItems() },
                onShareClicked = {
                    val shareText = viewModel.createShareText()
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareText)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                    viewModel.clearItems()
                }
            )
        }
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
                            title = stringResource(R.string.start_your_shopping_list),
                            description = stringResource(R.string.add_items_you_need_to_buy_and_share_the_list_with_others),
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = state.shopItems.isNotEmpty(),
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = rememberLazyListState()
                    ) {
                        items(
                            items = state.shopItems,
                            key = { "${it.id}${it.isBought}" },
                        ) { item ->
                            AnimatedShopItem(
                                modifier = Modifier
                                    .combinedClickable(
                                        onClick = {
                                            if (state.uiMode == ListPageUiMode.SELECTING) {
                                                viewModel.toggleSelected(item)
                                            }
                                        },
                                        onLongClick = {
                                            viewModel.toggleSelected(item)
                                        }
                                    )
                                    .background(
                                        color = if (state.selectedItems.contains(item)) MaterialTheme.colorScheme.primaryContainer
                                        else MaterialTheme.colorScheme.onSecondary
                                    ),
                                showDivider = state.shopItems.last() != item,

                                item = item,

                                onItemChanged = { viewModel.editShopItem(it) },

                                onDeleteClicked = { viewModel.deleteShopItem(it) },

                                isSelectionMode = state.uiMode == ListPageUiMode.SELECTING,
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

    if (showConfirmDeleteDialog) {
        ConfirmDialog(
            onDismissRequest = {
                showConfirmDeleteDialog = false
            },
            title = stringResource(R.string.delete_items, getItemsText(state.selectedItems.size)),
            description = stringResource(R.string.this_action_cannot_be_undone),
            confirmText = stringResource(R.string.delete),
            icon = Icons.Outlined.Delete,
            iconColor = Color(0xFFDC2626),
            iconBackgroundColor = Color(0xFFFEE2E2),
            onConfirmation = {
                showConfirmDeleteDialog = false
                viewModel.deleteShopItems(state.selectedItems.map { it.id!! })
                viewModel.clearItems()
            }
        )
    }

    if (showConfirmLogoutDialog) {
        ConfirmDialog(
            onDismissRequest = {
                showConfirmLogoutDialog = false
            },
            title = stringResource(R.string.are_you_sure_you_want_to_logout),
            description = stringResource(R.string.your_data_will_remain_unchanged),
            confirmText = stringResource(R.string.logout),
            icon = Icons.AutoMirrored.Outlined.Logout,
            iconColor = Color(0xFFDC2626),
            iconBackgroundColor = Color(0xFFFEE2E2),
            onConfirmation = {
                showConfirmLogoutDialog = false
                viewModel.logout()
            }
        )
    }
}


@Composable
private fun AnimatedShopItem(
    modifier: Modifier = Modifier,
    item: ShopItem,
    showDivider: Boolean = true,
    onItemChanged: (ShopItem) -> Unit,
    onDeleteClicked: (Long?) -> Unit,
    isSelectionMode: Boolean
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
                modifier = modifier,
                shopItem = item,
                isSelectionMode = isSelectionMode,
                onItemChanged = onItemChanged,
                onDeleteClicked = { id ->
                    isVisible = false
                    coroutineScope.launch {
                        delay(Duration.ofMillis(300))
                        onDeleteClicked(id)
                    }
                }
            )
            if (showDivider) {
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
    ListScreen(navController = rememberNavController())
}