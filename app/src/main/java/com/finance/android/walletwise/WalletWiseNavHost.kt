package com.finance.android.walletwise

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.finance.android.walletwise.model.user.UserPreferences
import com.finance.android.walletwise.ui.activity.*
import com.finance.android.walletwise.ui.activity.category.ScreenAddCategory
import com.finance.android.walletwise.ui.activity.transaction.EditScreenExpense
import com.finance.android.walletwise.ui.activity.transaction.ListExpenseScreen
import com.finance.android.walletwise.ui.activity.transaction.ScreeneAddExpense
import com.finance.android.walletwise.ui.activity.transaction.TransactionEditDestination
import com.finance.android.walletwise.ui.activity.user.EnterPinScreen
import com.finance.android.walletwise.ui.activity.user.LoginScreen
import com.finance.android.walletwise.ui.activity.user.ProfileSetupScreen
import com.finance.android.walletwise.ui.activity.user.SetupPinScreen
import com.finance.android.walletwise.ui.activity.user.SignupScreen
import com.finance.android.walletwise.ui.activity.user.WelcomeScreen
import com.finance.android.walletwise.ui.screen.CategoryListScreen
import com.finance.android.walletwise.ui.viewmodel.user.AuthenticationViewModel
import com.finance.android.walletwise.ui.viewmodel.user.PinViewModel
import com.finance.android.walletwise.ui.viewmodel.user.UserProfileViewModel

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
                    navController.navigateSingleTopTo(pinSetupScreen.route)
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
        composable(
            route = pinSetupScreen.route,
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
            SetupPinScreen(
                onNavigateHome = {
                    navController.navigateSingleTopTo(pinVerificationScreen.route)
                },
                pinViewModel = pinViewModel,
            )
        }
        /**
         * Enter PIN ===============================================================================
         */
        //EnterPinScreen
        composable(
            route = pinVerificationScreen.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                scaleOut(tween(500))
            }, )
        {
            EnterPinScreen(
                onNavigateHome = {
                    navController.navigate(homeScreen.route){
                        popUpTo(pinVerificationScreen.route) { inclusive = true }
                    }
                },
                pinViewModel = pinViewModel,
                userProfileViewModel = userProfileViewModel,
            )
        }
        /**
         * MAIN APP SCREENS ========================================================================
         */
        //HomeScreen ===============================================================================
        composable(
            route = homeScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            HomeScreen(
                onClickAddManual = {},
                onClickAddOCR = {},
                onClickAddText = {},
                quickAccessOnAnalysisClick = {},
                quickAccessOnAIChatClick = { navController.navigate(chatbotScreen.route) },
                quickAccessOnRemindClick = {}, )
        }

        //TransactionScreen ========================================================================
        //TransactionsListScreen
        composable(
            route = transactionsListScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            ListExpenseScreen(navController = navController)
        }
        //TransactionAddScreen
        composable(
            route = addTransactionScreen.route, )
        {
            ScreeneAddExpense(navigateBack = { navController.popBackStack() })
        }
        //TransactionEditScreen
        composable(
            route = TransactionEditDestination.routeWithArgs,
            arguments = listOf(navArgument(TransactionEditDestination.transactionIdArg){ type= NavType.IntType })
        )
        {
            EditScreenExpense(
                onBackClick = { navController.popBackStack() }
            )
        }

        //CategoryScreen ===========================================================================
        //CategoriesListScreen
        composable(
            route = categoriesListScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            CategoryListScreen(
                navController = navController)
        }
        //CategoryAddScreen
        composable(
            route = addCategoryScreen.route, )
        {
            ScreenAddCategory(navigateBack = { navController.popBackStack() })
        }
        //CategoryEditScreen
        composable(
            route = DetailCategoryDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailCategoryDestination.categoryIdArg){ type= NavType.IntType })
        )
        {
            DetailCategoryScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        //SettingScreen ============================================================================
        composable(
            route = settingScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            SettingScreen()
        }

        composable(
            route = chatbotScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            ChatScreen()
        }
    }
}

//Navigation FUNCTION
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