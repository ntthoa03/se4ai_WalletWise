package com.finance.android.walletwise.ui.viewmodel.user

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.walletwise.repository.FirebaseAuthRepository
import kotlinx.coroutines.launch

data class AuthenticationUiState(
    val username: String = "",
    val password: String = "",
    val usernameSignup: String = "",
    val passwordSignup: String = "",
    val confirmPasswordSignup: String = "",
    //
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val errorSignup: String? = null,
    val errorLogin: String? = null,
)

class AuthenticationViewModel(
    private val repository: FirebaseAuthRepository = FirebaseAuthRepository()
) : ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean
        get() = repository.hasUser()

    var authenticationUiState by mutableStateOf(AuthenticationUiState())
        private set //can only be accessed within this class (viewmodel)

    /**
     * Update items
     */
    //Username
    fun onUsernameChange(username: String) {
        authenticationUiState = authenticationUiState.copy(username = username)
    }
    //Password
    fun onPasswordChange(password: String) {
        authenticationUiState = authenticationUiState.copy(password = password)
    }
    //Signup
    fun onUsernameSignupChange(username: String) {
        authenticationUiState = authenticationUiState.copy(usernameSignup = username)
    }
    //Password
    fun onPasswordSignupChange(password: String) {
        authenticationUiState = authenticationUiState.copy(passwordSignup = password)
    }
    //Confirm Password
    fun onConfirmPasswordSignupChange(confirmPassword: String) {
        authenticationUiState = authenticationUiState.copy(confirmPasswordSignup = confirmPassword)
    }

    /**
     * Validate function
     */
    private fun validateLoginForm() =
        authenticationUiState.username.isNotBlank() &&
                authenticationUiState.password.isNotBlank()

    private fun validateSignupForm() =
        authenticationUiState.usernameSignup.isNotBlank() &&
                authenticationUiState.passwordSignup.isNotBlank() &&
                authenticationUiState.confirmPasswordSignup.isNotBlank()

    /**
     * Create user
     */
    fun createUser(context: Context) = viewModelScope.launch {
        try {
            //Check validated Form
            if (!validateSignupForm())
            {
                throw IllegalArgumentException("Email and password cannot be empty")
            }
            //Loading
            authenticationUiState = authenticationUiState.copy(isLoading = true)
            //Check password and confirm password
            if (authenticationUiState.passwordSignup != authenticationUiState.confirmPasswordSignup) {
                throw IllegalArgumentException("Password and confirm password do not match")
            }
            //No error
            authenticationUiState = authenticationUiState.copy(errorSignup = null)
            //Create user
            repository.createUser(
                authenticationUiState.usernameSignup,
                authenticationUiState.passwordSignup, )
            { isSuccessful ->
                if (isSuccessful)
                {
                    Toast.makeText(context, "Sign up success", Toast.LENGTH_SHORT).show()
                    authenticationUiState = authenticationUiState.copy(isSuccessLogin = true)
                }
                else
                {
                    Toast.makeText(context, "Sign up failed", Toast.LENGTH_SHORT).show()
                    authenticationUiState = authenticationUiState.copy(isSuccessLogin = false)
                }
            }
        }
        catch (e: Exception)
        {
            authenticationUiState = authenticationUiState.copy(errorSignup = e.localizedMessage)
            e.printStackTrace()
        }
        finally
        {
            authenticationUiState = authenticationUiState.copy(isLoading = false)
        }
    }

    /**
     * Login user
     */
    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            //Check validated Form
            if (!validateLoginForm())
            {
                throw IllegalArgumentException("Email and password cannot be empty")
            }
            //Loading
            authenticationUiState = authenticationUiState.copy(isLoading = true)
            //No error
            authenticationUiState = authenticationUiState.copy(errorLogin = null)
            //Login
            repository.loginUser(
                authenticationUiState.username,
                authenticationUiState.password, )
            { isSuccessful ->
                if (isSuccessful)
                {
                    Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
                    authenticationUiState = authenticationUiState.copy(isSuccessLogin = true)
                }
                else
                {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    authenticationUiState = authenticationUiState.copy(isSuccessLogin = false)
                }
            }
        }
        catch (e: Exception)
        {
            authenticationUiState = authenticationUiState.copy(errorLogin = e.localizedMessage)
            e.printStackTrace()
        }
        finally
        {
            authenticationUiState = authenticationUiState.copy(isLoading = false)
        }

    }
}
