package com.finance.android.walletwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

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