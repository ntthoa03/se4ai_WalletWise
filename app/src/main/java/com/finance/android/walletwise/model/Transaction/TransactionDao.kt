package com.finance.android.walletwise.model.Transaction

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.finance.android.walletwise.model.Transaction.Transaction
//import com.finance.android.walletwise.model.Transaction.ExpenseWithRelation

@Dao
interface TransactionDao {
    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getFlowOfExpensesWithRelation(): Flow<List<ExpenseWithRelation>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_table WHERE id = :id")
    fun getExpenseWithRelationById(id: Int): Flow<ExpenseWithRelation>

    @Query("SELECT * FROM transaction_table WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getTransactionsInRange(startDate: Long, endDate: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table")
    fun getTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE id=:id")
    fun getTransaction(id: Int): Flow<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}