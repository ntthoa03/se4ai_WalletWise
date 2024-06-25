package com.finance.android.walletwise.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryDao
import com.finance.android.walletwise.model.Category.CategoryRepository
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.model.Category.OfflineCategoryRepository
import com.finance.android.walletwise.model.Category.isValid
import com.finance.android.walletwise.model.Category.toCategory
import com.finance.android.walletwise.model.Transaction.AppDatabase
import com.finance.android.walletwise.model.Transaction.Transaction
import com.finance.android.walletwise.model.Transaction.isValid
import com.finance.android.walletwise.model.Transaction.toTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    private val _categoryUiState = mutableStateOf(CategoryUIState())
    val categoryUiState get() = _categoryUiState.value

    private val _allCategories = MutableStateFlow<List<Category>>(emptyList())
    val allCategories: StateFlow<List<Category>> = _allCategories.asStateFlow()

    fun updateUiState(newCategoryUIState: CategoryUIState) {
        _categoryUiState.value = newCategoryUIState.copy(actionEnabled = newCategoryUIState.isValid())
    }

    fun getAllCategories() {
        viewModelScope.launch {
            val categories = categoryRepository.getCategoriesStream().firstOrNull() ?: emptyList()
            _allCategories.value = categories
        }
    }

    suspend fun saveCategory() {
        if (categoryUiState.isValid()) {
            categoryRepository.insertCategory(categoryUiState.toCategory())
            getAllCategories() // refresh the list after saving
        }
    }

    fun updateNameCategory(category: String) {
        _categoryUiState.value = categoryUiState.copy(name = category)
    }

    fun updateAmount(amount: Int) {
        _categoryUiState.value = categoryUiState.copy(amount = amount.toString())
    }

    suspend fun findCategoryIdByName(name: String): Int {
        return categoryRepository.findCategoryIdByName(name)
    }

    // For getting categories of a specific type (similar to your transaction implementation)
    val expenseCategories: StateFlow<List<Category>> =
        allCategories
            //.map { categories -> categories.filter { it. == "Expense" } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}