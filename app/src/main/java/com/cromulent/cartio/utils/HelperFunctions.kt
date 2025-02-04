package com.cromulent.cartio.utils

import android.text.TextUtils
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import com.cromulent.cartio.R


@Composable
fun getItemsText(count: Int): String {
    return if (count == 1) stringResource(R.string.item, count)
    else stringResource(R.string.items, count)
}

@Composable
fun getDeviceLayoutDirection(): LayoutDirection {
    val context = LocalContext.current
    return if (TextUtils.getLayoutDirectionFromLocale(context.resources.configuration.locales[0])
        == View.LAYOUT_DIRECTION_RTL) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
}