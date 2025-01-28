package com.cromulent.cartio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cromulent.cartio.ui.screen.ListPage
import com.cromulent.cartio.ui.screen.LoginScreen
import com.cromulent.cartio.ui.screen.SignUpScreen
import com.cromulent.cartio.ui.theme.CartioTheme
import com.cromulent.cartio.viewmodel.ListPageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CartioTheme {
//              ListPage()
                LoginScreen()
//                SignUpScreen()
            }
        }
    }
}