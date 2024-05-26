package com.finance.android.walletwise.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    val username: String,
    val password: String,
    val fullname: String,
    val email: String?,
    // ... other profile fields
)