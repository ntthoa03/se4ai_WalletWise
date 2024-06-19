package com.finance.android.walletwise.ui.viewmodel.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.finance.android.walletwise.model.user.UserProfile
import com.finance.android.walletwise.repository.UserProfileRepository
import com.google.firebase.auth.FirebaseUser

data class UserProfileUiState(
    val fullName: String = "",
    val gender: String = "",
    val age: Int = 0,
    val phoneNumber: String = "",
    val currency: String = "",

    val isLoading: Boolean = false,

    val addUserProfileStatus: Boolean = false,
    val updateUserProfileStatus: Boolean = false,
    val currentUserProfile: UserProfile? = null,
)

class UserProfileViewModel(
    private val repository: UserProfileRepository = UserProfileRepository()
) : ViewModel() {

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user()

    private val userId: String
        get() = repository.getUserId()

    var userProfileUiState by mutableStateOf(UserProfileUiState())
        private set

    /**
     * Update functions
     */
    //Username
    fun onFullNameChanged(fullName: String) {
        userProfileUiState = userProfileUiState.copy(fullName = fullName)
    }
    //Gender
    fun onGenderChanged(gender: String) {
        userProfileUiState = userProfileUiState.copy(gender = gender)
    }
    //Age
    fun onAgeChanged(age: Int) {
        userProfileUiState = userProfileUiState.copy(age = age)
    }
    //Phone Number
    fun onPhoneNumberChanged(phoneNumber: String) {
        userProfileUiState = userProfileUiState.copy(phoneNumber = phoneNumber)
    }
    //Currency
    fun onCurrencyChanged(currency: String) {
        userProfileUiState = userProfileUiState.copy(currency = currency)
    }

    /**
     * Add User Profile function
     */
    fun addUserProfile()
    {
        userProfileUiState = userProfileUiState.copy(isLoading = true)
        if (hasUser)
        {
            repository.addUserProfile(
                userId = userId,
                fullName = userProfileUiState.fullName,
                gender = userProfileUiState.gender,
                age = userProfileUiState.age,
                phoneNumber = userProfileUiState.phoneNumber,
                currency = userProfileUiState.currency, )
            {
                userProfileUiState = userProfileUiState.copy(addUserProfileStatus = it)
            }
        }
        userProfileUiState = userProfileUiState.copy(isLoading = false)
    }

    /**
     * Set edit fields user profile
     */
    fun setEditFieldsUserProfile(userProfile: UserProfile)
    {
        userProfileUiState = userProfileUiState.copy(
            fullName = userProfile.fullName,
            gender = userProfile.gender,
            age = userProfile.age,
            phoneNumber = userProfile.phoneNumber,
            currency = userProfile.currency, )
    }

    /**
     * Get user profile
     */
    fun getUserProfile()
    {
        repository.getUserProfile(
            userId = userId,
            onError = {}, )
        {
            userProfileUiState = userProfileUiState.copy(currentUserProfile = it)
            userProfileUiState.currentUserProfile?.let { it1 -> setEditFieldsUserProfile(it1) }
        }
    }

    /**
     * Update user profile
     */
    fun updateUserProfile(
        userId: String, )
    {
        repository.updateUserProfile(
            userId = userId,
            fullName = userProfileUiState.fullName,
            gender = userProfileUiState.gender,
            age = userProfileUiState.age,
            phoneNumber = userProfileUiState.phoneNumber,
            currency = userProfileUiState.currency, )
        {
            userProfileUiState = userProfileUiState.copy(updateUserProfileStatus = it)
        }
    }

    /**
     * Reset status
     */
    fun resetUserProfileStatus()
    {
        userProfileUiState = userProfileUiState.copy(
            addUserProfileStatus = false,
            updateUserProfileStatus = false,)
    }

    fun resetStateUi()
    {
        userProfileUiState = UserProfileUiState()
    }

}