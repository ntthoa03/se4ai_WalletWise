package com.finance.android.walletwise

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.finance.android.walletwise.ui.viewmodel.AuthenticationViewModel
import com.finance.android.walletwise.ui.viewmodel.PinViewModel
import com.finance.android.walletwise.ui.viewmodel.UserProfileViewModel

//Control application navigation with NavHost
@Composable
fun WalletWiseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authenticationViewModel: AuthenticationViewModel? = null,
    userProfileViewModel: UserProfileViewModel? = null,
    pinViewModel: PinViewModel? = null,)
{
    val context = LocalContext.current
    var startDestination by rememberSaveable { mutableStateOf(
        if (UserPreferences.isFirstTimeLaunch(context)) welcomeScreen.route else pinVerificationScreen.route) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier, )
    {
        /**
         * WELCOME =================================================================================
         */
        //WelcomeScreen ----------------------------------------------------------------------------
        composable(
            route = welcomeScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))  //Open Animation
            },
            exitTransition = {
                return@composable fadeOut(tween(300)) //Close Animation
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Re-open Animation
            }, )
        {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigateSingleTopTo(loginScreen.route)
                },
                onSignupClick = {
                    navController.navigateSingleTopTo(signupScreen.route) }
            )
        }
        //LoginScreen ------------------------------------------------------------------------------
        composable(
            route = loginScreen.route,
            enterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300), )     //Open Animation
            },
            exitTransition = {
                return@composable fadeOut(tween(300)) //Close Animation
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Re-open Animation
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Back Animation
            }, )
        {
            LoginScreen(
                onSignUpClick = {
                    navController.navigateSingleTopTo(signupScreen.route)
                },
                navigateToHome = {
                    navController.navigateSingleTopTo(homeScreen.route)
                },
                authenticationViewModel = authenticationViewModel,
            )
        }
        //SignupScreen -----------------------------------------------------------------------------
        composable(
            route = signupScreen.route,
            enterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300), )     //Open Animation
            },
            exitTransition = {
                return@composable fadeOut(tween(300)) //Close Animation
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Re-open Animation
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Back Animation
            }, )
        {
            SignupScreen(
                navigateToProfile = {
                    navController.navigateSingleTopTo(profileSetupScreen.route)
                },
                onLoginClick = {
                    navController.navigateSingleTopTo(loginScreen.route)
                },
                authenticationViewModel = authenticationViewModel,
            )
        }

        /**
         * SETUP PROFILE ===========================================================================
         */
        //SetupProfileScreen
        composable(
            route = profileSetupScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))
            },
            exitTransition = {
                return@composable fadeOut(tween(300))
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }, )
        {
            ProfileSetupScreen(
                navigateToHome = {
                    navController.navigateSingleTopTo(pinSetupScreen.route)
                },
                userProfileViewModel = userProfileViewModel,
            )
        }
        //SetupPinScreen
        composable(route = pinSetupScreen.route)
        {
            SetupPinScreen(
                onNavigateHome = {
                    navController.navigateSingleTopTo(pinVerificationScreen.route)
                },
                pinViewModel = pinViewModel,
            )
        }
        /**
         * HOME ====================================================================================
         */
        //EnterPinScreen
        composable(route = pinVerificationScreen.route)
        {
            EnterPinScreen(
                onNavigateHome = {
                    navController.navigateSingleTopTo(homeScreen.route)
                },
                pinViewModel = pinViewModel,
            )
        }
        //HomeScreen
        composable(route = homeScreen.route)
        {
            HomeScreen()
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