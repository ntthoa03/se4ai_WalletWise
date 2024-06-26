package com.finance.android.walletwise.ui.viewmodel.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.walletwise.model.Transaction.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.finance.android.walletwise.model.Transaction.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class TransactionsScreenViewModel(transactionRepository: TransactionRepository): ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val transactionsScreenUiState: StateFlow<TransactionsScreenUiState> =
        transactionRepository.getTransactionsStream().map { TransactionsScreenUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionsScreenUiState()
            )


    data class TransactionsScreenUiState(val transactionList: List<Transaction> = listOf())

    val now = System.currentTimeMillis()
    val oneDayInMillis = 24 * 60 * 60 * 1000 // Number of milliseconds in a day
    val oneWeekInMillis = 24 * 60 * 60 * 1000*7 // Number of milliseconds in a week


    val currentDate = LocalDate.now()
    val startOfCurrentMonth = currentDate.withDayOfMonth(1)


    val startDate = now - oneDayInMillis
    val endDate = now
    val endInstant = Instant.ofEpochMilli(endDate)

    val endLocalDate = endInstant.atZone(ZoneId.systemDefault()).toLocalDate()

    val startDateWeek = now - oneWeekInMillis
    val startInstantWeek = Instant.ofEpochMilli(startDateWeek)
    val startLocalDateWeek = startInstantWeek.atZone(ZoneId.systemDefault()).toLocalDate()



    val startLocalDateYear = LocalDate.now().minusYears(1)

    val transactionsScreenUiStateToday: StateFlow<TransactionsScreenUiStateToday> =
        //transactionRepository.getTransactionsInRangeStream(startDate = startDate,endDate=endDate).map { TransactionsScreenUiStateToday(it) }
        transactionRepository.getTransactionsStream()
            .map { TransactionsScreenUiStateToday(it.filter { transaction ->
                transaction.date == currentDate
            }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionsScreenUiStateToday()
            )
    data class TransactionsScreenUiStateToday(val transactionList: List<Transaction> = listOf())

    val transactionsScreenUiStateWeek: StateFlow<TransactionsScreenUiStateWeek> =
        transactionRepository.getTransactionsStream()
            .map { TransactionsScreenUiStateWeek(it.filter { transaction ->
                transaction.date >= startLocalDateWeek && transaction.date <= endLocalDate
            }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionsScreenUiStateWeek()
            )
    data class TransactionsScreenUiStateWeek(val transactionList: List<Transaction> = listOf())

    val transactionsScreenUiStateMonth: StateFlow<TransactionsScreenUiStateMonth> =
        transactionRepository.getTransactionsStream()
            .map { TransactionsScreenUiStateMonth(it.filter { transaction ->
                transaction.date >= startOfCurrentMonth && transaction.date <= startOfCurrentMonth.plusMonths(1)
            }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionsScreenUiStateMonth()
            )
    data class TransactionsScreenUiStateMonth(val transactionListt: List<Transaction> = listOf())

    val transactionsScreenUiStateYear: StateFlow<TransactionsScreenUiStateYear> =
        transactionRepository.getTransactionsStream()
            .map { TransactionsScreenUiStateYear(it.filter { transaction ->
                transaction.date >= startLocalDateYear && transaction.date <= endLocalDate
            }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionsScreenUiStateYear()
            )
    data class TransactionsScreenUiStateYear(val transactionList: List<Transaction> = listOf())

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome

    private val _totalExpense = MutableStateFlow(0.0)
    val totalExpense: StateFlow<Double> = _totalExpense

    init {
        viewModelScope.launch {
            transactionsScreenUiState.collect { uiState ->
                val income = uiState.transactionList.filter { it.type == "Income" }.sumOf { it.amount }
                val expense = uiState.transactionList.filter { it.type == "Expense" }.sumOf { it.amount }
                Log.d("Home", "totalIncome: $income")
                Log.d("Home", "totalOutcome: $expense")
                _totalIncome.value = income
                _totalExpense.value = expense
            }
        }
    }
}