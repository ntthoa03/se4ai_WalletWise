package com.finance.android.walletwise.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime


import com.finance.android.walletwise.model.Transaction.TransactionRepository
import com.finance.android.walletwise.model.Transaction.TransactionUiState
import com.finance.android.walletwise.model.Transaction.isValid
import com.finance.android.walletwise.model.Transaction.toTransaction

class ExpenseViewModel(private val transactionRepository: TransactionRepository): ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUiState())

        private set

    fun updateUiState(newTransactionUiState: TransactionUiState){
        transactionUiState= newTransactionUiState.copy(actionEnabled = newTransactionUiState.isValid())
    }
    suspend fun saveTransactionExpense() {
        if (transactionUiState.isValid()) {
            transactionUiState = transactionUiState.copy(type = "Expense")
            val transaction = transactionUiState.toTransaction()
            Log.d("Transaction", "Saving transaction: $transaction")
            try {
                transactionRepository.addTransaction(transaction)
                Log.d("Transaction", "Transaction saved successfully.")
            } catch (e: Exception) {
                Log.e("Transaction", "Error saving transaction: ${e.message}")
                // Handle the error as needed
            }
        }
    }
    suspend fun saveTransactionIncome(){
        if(transactionUiState.isValid()){
            transactionUiState=transactionUiState.copy(type = "Income")
            transactionRepository.addTransaction(transactionUiState.toTransaction())
        }
    }
    fun updateDate(date: LocalDate){
        /*val date = DateTimeFormatter
            .ofPattern("MMM dd yyyy")
            .format(date)*/
        transactionUiState=transactionUiState.copy(date=date)
    }

    fun updateTime(time: LocalTime){
        /*val time = DateTimeFormatter
            .ofPattern("hh:mm")
            .format(time)*/
        transactionUiState = transactionUiState.copy(time = time)
    }
    fun updateCategory(category: Int) {
        transactionUiState = transactionUiState.copy(idCategory = category)
    }
    fun updateAmount(amount: Double) {
        transactionUiState = transactionUiState.copy(amount = amount.toString())
    }


}