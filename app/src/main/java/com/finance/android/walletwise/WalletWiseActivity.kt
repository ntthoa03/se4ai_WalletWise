package com.finance.android.walletwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finance.android.walletwise.ui.activity.*

//Import UI file
import com.finance.android.walletwise.ui.theme.*
import com.finance.android.walletwise.ui.viewmodel.MainViewModel
import com.finance.android.walletwise.ui.viewmodel.Screen

//Application start point
class WalletWiseActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            WalletWiseApplication()
        }
    }
}

@Composable
fun WalletWiseApplication()
{
    WalletWiseTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination

        WalletWiseNavHost(
            navController = navController,
            modifier = Modifier)
    }
}


//class WalletWiseActivity : ComponentActivity()
//{
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        setContent {
//            WalletWiseTheme {
//                //Create MainViewModel instance
//                val viewModel = MainViewModel(this)
//                val navController = rememberNavController()
//
//                //Observe the navigateToScreen state flow
//                val currentScreen by viewModel.navigateToScreen.collectAsState()
//
//                //Use NavHost to manage navigation
//                NavHost(navController = navController, startDestination = currentScreen.name) {
//
//                    composable(Screen.Welcome.name)
//                    {
//                        WelcomeScreen(
//                            navController = navController,
//                            onSignUpClick = { navController.navigate("signup") },
//                            onLoginClick = { navController.navigate("login") }
//                        )
//                    }
//                    composable(Screen.SignUp.name) { SignupScreen(navController) }
//                    composable(Screen.Login.name) { LoginScreen(navController) }
//                }
//            }
//        }
//    }
//}