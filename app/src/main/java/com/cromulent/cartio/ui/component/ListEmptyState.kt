package com.cromulent.cartio.ui.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.CombinedClickableNode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListEmptyState(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    description: String,
    iconColor: Color = Color(0xFF16A34A),
    backgroundColor: Color = Color(0xFFDCFCE7)
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Icon(
            imageVector = icon,
            contentDescription = "List Icon",
            tint = iconColor,
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .size(64.dp)
                .padding(10.dp)
        )

        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 6.dp)
                .align(Alignment.CenterHorizontally)
        )


        Text(
            text = description,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

//        Button(
//            colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xFF16A34A)),
//            shape = RoundedCornerShape(8.dp),
//            onClick = { onClick() } ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "")
//                Text(text = "Add first Item")
//            }
//        }

    }

}

@Preview
@Composable
private fun EmptyStatePrev() {
    ListEmptyState(
        icon = Icons.AutoMirrored.Filled.List,
        title = "Start your shopping list",
        description = "Add items you need to buy and share the list with others",
    )
}