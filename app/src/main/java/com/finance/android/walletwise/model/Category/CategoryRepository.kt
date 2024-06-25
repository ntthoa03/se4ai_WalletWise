package com.finance.android.walletwise.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoriesStream(): Flow<List<Category>>

    fun getCategoryStream(id: Int): Flow<Category?>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun findCategoryIdByName(name: String): Int

}

class OfflineCategoryRepository(private val categoryDao: CategoryDao) : CategoryRepository {
    override fun getCategoriesStream(): Flow<List<Category>> = categoryDao.getCategories()

    override fun getCategoryStream(id: Int): Flow<Category?> = categoryDao.getCategoryById(id)

    override suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)

    override suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    override suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    override suspend fun findCategoryIdByName(name: String): Int {
        return categoryDao.findCategoryIdByName(name)
    }
}
