package com.finance.android.walletwise.ui.viewmodel.user

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.finance.android.walletwise.repository.PinRepository

data class PinUiState(
    val pin: String = "",
    val confirmPin: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPinCreated: Boolean = false,
    val isPinVerified: Boolean = false
)

class PinViewModel(
    context: Context,
    private val pinRepository: PinRepository = PinRepository(context)
) : ViewModel() {
    var pinUiState by mutableStateOf(PinUiState())
        private set

    /**
     * Upgrade Functions
     */
    fun onPinChanged(pin: String) {
        pinUiState = pinUiState.copy(pin = pin)
    }

    fun onConfirmPinChanged(confirmPin: String) {
        pinUiState = pinUiState.copy(confirmPin = confirmPin)
    }

    /**
     * Create Pin Functions
     */
    fun createPin()
    {
        Log.d("PinViewModel", "Creating PIN")

        pinUiState = pinUiState.copy(isLoading = true)

        if (pinUiState.pin.length < 4 || pinUiState.pin != pinUiState.confirmPin)
        {
            pinUiState = pinUiState.copy(
                error = "Not a valid PIN",
                isLoading = false, )
            return
        }
        pinRepository.savePin(pinUiState.pin)
        pinUiState = pinUiState.copy(
            isPinCreated = true,
            isLoading = false, )
    }

    /**
     * Verify Pin Functions
     */
    fun verifyPin(inputPin: String)
    {
        Log.d("PinViewModel", "Verifying PIN")

        pinUiState = pinUiState.copy(isLoading = true)

        val storedPinHash = pinRepository.getPin()
        if (storedPinHash == null || pinRepository.hashPin(inputPin) != storedPinHash) {
            pinUiState = pinUiState.copy(
                error = "Incorrect PIN",
                isLoading = false, )
        }
        else
        {
            pinUiState = pinUiState.copy(
                isPinVerified = true,
                isLoading = false, )
        }
    }

    /**
     * Reset Pin Functions
     */
    fun resetPinState() {
        pinUiState = PinUiState()
    }
}
