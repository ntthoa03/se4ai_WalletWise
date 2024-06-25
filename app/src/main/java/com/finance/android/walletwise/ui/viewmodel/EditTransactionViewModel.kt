package com.finance.android.walletwise.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.walletwise.model.Transaction.TransactionRepository
import com.finance.android.walletwise.model.Transaction.TransactionUiState
import com.finance.android.walletwise.model.Transaction.isValid
import com.finance.android.walletwise.model.Transaction.toTransaction
import com.finance.android.walletwise.model.Transaction.toTransactionUiState
import com.finance.android.walletwise.ui.activity.TransactionEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditTransactionViewModel(
    savedStateHandle: SavedStateHandle,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUiState())
        private set

    private val transactionId: Int = checkNotNull(savedStateHandle[TransactionEditDestination.transactionIdArg])

    init {
        viewModelScope.launch {
            transactionUiState = transactionRepository.getTransactionStream(transactionId)
                .filterNotNull()
                .first()
                .toTransactionUiState(actionEnabled = true)
        }
    }

    fun updateUiState(newItemUiState: TransactionUiState){
        transactionUiState=newItemUiState.copy(actionEnabled = newItemUiState.isValid())
    }
    suspend fun updateTransaction(){
        if(transactionUiState.isValid()){
            transactionRepository.updateTransaction(transactionUiState.toTransaction())
        }
    }
    suspend fun deleteTransaction() {
        transactionRepository.deleteTransaction(transactionUiState.toTransaction())
    }
}

