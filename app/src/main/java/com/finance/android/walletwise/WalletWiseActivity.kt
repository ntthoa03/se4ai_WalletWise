package com.finance.android.walletwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finance.android.walletwise.ui.activity.*

//Import UI file
import com.finance.android.walletwise.ui.theme.*
import com.finance.android.walletwise.ui.viewmodel.AuthenticationViewModel
import com.finance.android.walletwise.ui.viewmodel.MainViewModel
import com.finance.android.walletwise.ui.viewmodel.PinViewModel
import com.finance.android.walletwise.ui.viewmodel.Screen
import com.finance.android.walletwise.ui.viewmodel.UserProfileViewModel

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
            WalletWiseApplication(
                authenticationViewModel = authenticationViewModel,
                userProfileViewModel    = userProfileViewModel,
                pinViewModel            = pinViewModel,
            )
        }
    }
}

@Composable
fun WalletWiseApplication(
    authenticationViewModel: AuthenticationViewModel,
    userProfileViewModel: UserProfileViewModel,
    pinViewModel: PinViewModel, )
{
    WalletWiseTheme {
        val navController = rememberNavController()
        //val currentBackStack by navController.currentBackStackEntryAsState()
        //val currentDestination = currentBackStack?.destination

        WalletWiseNavHost(
            navController = navController,
            modifier = Modifier,
            authenticationViewModel = authenticationViewModel,
            userProfileViewModel = userProfileViewModel,
            pinViewModel = pinViewModel, )
    }
}