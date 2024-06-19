package com.finance.android.walletwise.model.user

import android.content.Context

object UserPreferences {
    private const val KEY_FIRST_TIME = "first_time_launch"

    fun isFirstTimeLaunch(context: Context): Boolean
    {
        val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstTime = sharedPrefs.getBoolean(KEY_FIRST_TIME, true)

        // If it's the first time, set the flag to False and return to True
        if (isFirstTime)
        {
            sharedPrefs.edit().putBoolean(KEY_FIRST_TIME, false).apply()
        }
        return isFirstTime
    }
}