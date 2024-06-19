package com.finance.android.walletwise.repository

import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthRepository {
    //Get current user
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    //Check if user is logged in
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    //Get user id
    fun getUserId() = Firebase.auth.currentUser?.uid.orEmpty()

    /**
     * CREATE USER
     */
    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit,
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }
            .await() //Create suspendable function to not block the main thread.
    }

    /**
     * LOGIN USER
     */
    suspend fun loginUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit,
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }
            .await() //Create suspendable function to not block the main thread.
    }
}