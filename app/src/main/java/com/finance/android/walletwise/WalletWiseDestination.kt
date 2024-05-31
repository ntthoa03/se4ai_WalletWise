package com.finance.android.walletwise

//Related to Navigation
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
//Icon vector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

interface WalletWiseDestination {
    val icon : ImageVector
    val route: String
}

/* WELCOME ====================================================================================== */
object welcomeScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "welcomeScreen"
}

object signupScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "signupScreen"
}

object loginScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "loginScreen"
}