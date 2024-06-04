package com.finance.android.walletwise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.finance.android.walletwise.model.UserPreferences
import com.finance.android.walletwise.ui.activity.*

//Control Screen navigation with NavHost
@Composable
fun WalletWiseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier )
{
    val context = LocalContext.current
    var startDestination by rememberSaveable { mutableStateOf(
        if (UserPreferences.isFirstTimeLaunch(context)) welcomeScreen.route else loginScreen.route) }

    NavHost(
        navController = navController,
        startDestination = startDestination, //welcomeScreen.route,
        modifier = modifier )
    {
        /* ============================== Welcome ============================== */

        //WelcomeScreen
        composable(route = welcomeScreen.route)
        {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigateSingleTopTo(loginScreen.route)
                },
                onSignupClick = {
                    navController.navigateSingleTopTo(signupScreen.route) }
            )
        }

        //LoginScreen
        composable(route = loginScreen.route)
        {
            LoginScreen(
                onNextLogin = { /* TODO */ }
            )
        }

        //SignupScreen
        composable(route = signupScreen.route)
        {
            SignupScreen()
        }
    }
}

//Navigation
fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route)
{
    //Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as users select items
    popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
    {
        saveState = true
    }
    //Avoid multiple copies of the same destination when re-selecting the same item
    launchSingleTop = true
    //Restore state when re-selecting a previously selected item
    restoreState = true
}