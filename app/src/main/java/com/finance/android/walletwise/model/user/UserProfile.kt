package com.finance.android.walletwise.model.user

data class UserProfile(
    val fullName: String = "",
    val gender: String = "",
    val age: Int = 0,
    val phoneNumber: String = "",
    val currency: String = "",
)
