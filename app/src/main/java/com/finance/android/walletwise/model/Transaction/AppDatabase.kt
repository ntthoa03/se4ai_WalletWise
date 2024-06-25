package com.finance.android.walletwise.model.Transaction

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val defaultExpenseCategoryDtoTypeList = listOf(
    Category(id = 1, name = "INCOME", amount = 0),
    Category(id = 2, name = "Food & Drink", amount = 0),
    Category(id = 3, name = "Education", amount = 0),
    Category(id = 4, name = "Transportation", amount = 0),
    Category(id = 5, name = "Sport & Entertainment", amount = 0),
    Category(id = 6, name = "Shopping", amount = 0),
    Category(id = 7, name = "House & Utilities", amount = 0),
)

@Database(entities = [Transaction::class, Category::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Log.d("Test getDatabase","run getDatabase")
                Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    "app_database_v6"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("AppDatabase", "Database created")
                            CoroutineScope(Dispatchers.IO).launch {
                                populateDatabase(getDatabase(context))
                            }
                        }
                    })
                    .build()
                    .also { instance = it }
            }
        }

        private suspend fun populateDatabase(database: AppDatabase) {
            val categoryDao = database.categoryDao()
            val categories = categoryDao.getAllCategories() // Giả sử bạn có hàm này trong CategoryDao để kiểm tra dữ liệu hiện tại
            if (categories.isEmpty()) {
                Log.d("AppDatabase", "Inserting default categories")
                categoryDao.insertAll(defaultExpenseCategoryDtoTypeList)
            } else {
                Log.d("AppDatabase", "Default categories already exist")
            }
        }

    }
}