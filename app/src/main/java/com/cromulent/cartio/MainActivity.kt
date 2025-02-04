package com.cromulent.cartio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cromulent.cartio.ui.screen.shop.list.ListScreen
import com.cromulent.cartio.ui.screen.auth.login.LoginScreen
import com.cromulent.cartio.ui.screen.auth.signup.SignUpScreen
import com.cromulent.cartio.ui.theme.CartioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CartioTheme {
                val navController: NavHostController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = CartioRoutes.SIGNUP.name,
                    modifier = Modifier,
                ) {

                    composable(CartioRoutes.SIGNUP.name) {
                        SignUpScreen( navController = navController )
                    }
                    composable(CartioRoutes.LOGIN.name) {
                        LoginScreen( navController = navController )
                    }
                    composable(CartioRoutes.LIST.name) {
                        ListScreen( navController = navController )
                    }

                }
            }
        }
    }
}

enum class CartioRoutes {
    SIGNUP,
    LOGIN,
    LIST
}