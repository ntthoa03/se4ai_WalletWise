package com.finance.android.walletwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finance.android.walletwise.ui.fragment.WalletWiseBottomBar
import com.finance.android.walletwise.ui.fragment.WalletWiseTopAppBar

//Import UI file
import com.finance.android.walletwise.ui.viewmodel.user.AuthenticationViewModel
import com.finance.android.walletwise.ui.viewmodel.user.PinViewModel
import com.finance.android.walletwise.ui.viewmodel.user.UserProfileViewModel

//Application start point
class WalletWiseActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            val authenticationViewModel = viewModel(modelClass = AuthenticationViewModel::class.java)
            val userProfileViewModel    = viewModel(modelClass = UserProfileViewModel::class.java)
            val pinViewModel            = PinViewModel(context = LocalContext.current)
            WalletWiseApp(
                authenticationViewModel = authenticationViewModel,
                userProfileViewModel    = userProfileViewModel,
                pinViewModel            = pinViewModel,
            )
        }
    }
}

@Composable
fun WalletWiseApp(
    authenticationViewModel: AuthenticationViewModel,
    userProfileViewModel: UserProfileViewModel,
    pinViewModel: PinViewModel, )
{
    WalletWiseTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination?.route

        /**
         * Elements Visibility
         */
        val isTopBarVisible: Boolean =
            tabNavigationScreens.contains(currentDestination)
        val isBottomBarVisible: Boolean =
            tabNavigationScreens.contains(currentDestination)
        val isFABVisible: Boolean =
            homepageScreens.contains(currentDestination)

        /**
         * Elements Types
         */
        val topBarType: Int = when {
            tabNavigationScreens.contains(currentDestination) -> 0 //main Top App Bar
            else -> -1
        }

        val bottomBarType: Int = when {
            tabNavigationScreens.contains(currentDestination) -> 0 //navigation Bottom App Bar
            else -> -1
        }

        var selectedTab by remember { mutableStateOf(0) }

        Scaffold(
            /**
             * TOP APP BAR
             */
            topBar = {
                if (isTopBarVisible)
                {
                    when (topBarType) {
                        0 -> WalletWiseTopAppBar(
                            title = "WalletWise",
                            useIconForTitle = true,
                            showNavigationButton = true,
                            showActionButton = true,
                            onNavigationClick = { /*TODO*/ },
                            onActionClick = { /*TODO*/ },
                        )
                    }
                }
            },
            /**
             * BOTTOM APP BAR
             */
            bottomBar = {
                if (isBottomBarVisible) {
                    when (bottomBarType) {
                        0 -> WalletWiseBottomBar(
                            selectedTab = selectedTab,
                            onTabSelected = { index ->
                                selectedTab = index
                                navController.navigate(
                                    when (index) {
                                        0 -> homeScreen.route
                                        1 -> transactionsListScreen.route
                                        2 -> categoriesListScreen.route
                                        3 -> settingScreen.route
                                        else -> homeScreen.route
                                    },
                                )
                                {
                                    popUpTo(homeScreen.route) { saveState = true }
                                    launchSingleTop = true
                                }
                            },
                            onHomeClick = { /*TODO*/ },
                            onExpenseListClick = { /*TODO*/ },
                            onCategoryListClick = { /*TODO*/ },
                            onSettingsClick = {/* TODO */ },
                        )
                    }
                }
            },
        )
        { innerPadding ->
            WalletWiseNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                authenticationViewModel = authenticationViewModel,
                userProfileViewModel = userProfileViewModel,
                pinViewModel = pinViewModel,
            )
        }
    }
}