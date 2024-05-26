package com.finance.android.walletwise.repository

import com.finance.android.walletwise.model.User
import com.finance.android.walletwise.model.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun getUser(userId: Int): User? = userDao.getUser(userId)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
}