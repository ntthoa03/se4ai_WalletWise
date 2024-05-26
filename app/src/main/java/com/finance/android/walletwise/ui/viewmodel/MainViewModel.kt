package com.finance.android.walletwise.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.finance.android.walletwise.model.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val context: Context) : ViewModel()
{
    private val _navigateToScreen = MutableStateFlow(
        if (UserPreferences.isFirstTimeLaunch(context)) Screen.Welcome else Screen.Login
    )
    val navigateToScreen: StateFlow<Screen> = _navigateToScreen
}
enum class Screen {Welcome, Login, SignUp}