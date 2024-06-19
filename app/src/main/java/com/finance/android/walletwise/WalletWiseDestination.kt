package com.finance.android.walletwise

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

interface WalletWiseDestination {
    val icon : ImageVector
    val route: String
}

/**
 * AUTHENTICATION ==================================================================================
 */
//WELCOME SCREEN -----------------------------------------------------------------------------------
object welcomeScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "welcomeScreen"
}
//SIGNUP SCREEN ------------------------------------------------------------------------------------
object signupScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "signupScreen"
}
//LOGIN SCREEN -------------------------------------------------------------------------------------
object loginScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "loginScreen"
}
//PROFILE SETUP SCREEN -----------------------------------------------------------------------------
object profileSetupScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "profileSetupScreen"
}
//PIN SETUP SCREEN ---------------------------------------------------------------------------------
object pinSetupScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.CheckCircle
    override val route = "pinSetupScreen"
}
//PIN VERIFICATION SCREEN --------------------------------------------------------------------------
object pinVerificationScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.CheckCircle
    override val route = "pinVerificationScreen"
}

/**
 * MAIN TABS NAVIGATION ============================================================================
 */
//HOME SCREEN --------------------------------------------------------------------------------------
object homeScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "homeScreen"
}
//EXPENSES LIST SCREEN -----------------------------------------------------------------------------
object expenseListScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "expenseListScreen"
}
//CATEGORIES LIST SCREEN ---------------------------------------------------------------------------
object categoryListScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "categoryListScreen"
}
//SETTINGS SCREEN ----------------------------------------------------------------------------------
object settingScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "settingScreen"
}




/**
 * APPLICATION BARS SETTINGS =======================================================================
 */
val authenticationScreens = mutableListOf(
    welcomeScreen.route,
    signupScreen.route,
    loginScreen.route,
    profileSetupScreen.route,
    pinSetupScreen.route,
    pinVerificationScreen.route
) //No Top or Bottom bars or FAB

val tabNavigationScreens = mutableListOf(
    homeScreen.route,
    expenseListScreen.route,
    categoryListScreen.route,
    settingScreen.route,
) //Top (main) and Bottom (navigation) bars and FAB

val homepageScreens = mutableListOf(
    homeScreen.route,
) //Show FAB on Homepage only, or not :>