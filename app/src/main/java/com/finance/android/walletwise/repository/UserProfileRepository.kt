package com.finance.android.walletwise.repository

import com.finance.android.walletwise.model.user.UserProfile
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore

const val USERPROFILE_COLLECTION_REF = "UserProfiles"

/**
 * Repository for user profile
 */
class UserProfileRepository
{
    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val userProfileRef: CollectionReference =
        Firebase.firestore.collection(USERPROFILE_COLLECTION_REF)

    /**
     * Get user profile
     */
    fun getUserProfile(
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (UserProfile?) -> Unit, )
    {
        userProfileRef
            .document(userId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(UserProfile::class.java))
            }
            .addOnFailureListener {
                onError.invoke(it.cause)
            }
    }

    /**
     * Add user profile
     */
    fun addUserProfile(
        userId: String,
        fullName: String,
        gender: String,
        age: Int,
        phoneNumber: String,
        currency: String,
        onComplete: (Boolean) -> Unit, )
    {
        userProfileRef.document(userId)

        val userProfile = UserProfile(
            fullName = fullName,
            gender = gender,
            age = age,
            phoneNumber = phoneNumber,
            currency = currency, )

        userProfileRef
            .document(userId)
            .set(userProfile)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    /**
     * Delete user profile
     */
    fun deleteUserProfile(
        userId: String,
        onComplete: (Boolean) -> Unit, )
    {
        userProfileRef
            .document(userId)
            .delete()
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    /**
     * Update user profile
     */
    fun updateUserProfile(
        userId: String,
        //Values to change:
        fullName: String,
        gender: String,
        age: Int,
        phoneNumber: String,
        currency: String,
        onResult: (Boolean) -> Unit, )
    {
        val updateData = hashMapOf<String, Any> (
            "fullName" to fullName,
            "gender" to gender,
            "age" to age,
            "phoneNumber" to phoneNumber,
            "currency" to currency, )
        //instead of manually .update("userName", userName) ...

        userProfileRef
            .document(userId)
            .update(updateData)
            .addOnCompleteListener { result ->
                onResult.invoke(result.isSuccessful)
            }
    }

    fun signOut() = Firebase.auth.signOut()
}


/**
 * Resource Class to manage the state of a resource
 */
sealed class Resource<T>(
    val data:T? = null,
    val throwable: Throwable? = null, )
{
    class Loading<T>: Resource<T>()
    class Success<T>(data: T?): Resource<T>(data = data)
    class Error<T>(throwable: Throwable?): Resource<T>(throwable = throwable)
}