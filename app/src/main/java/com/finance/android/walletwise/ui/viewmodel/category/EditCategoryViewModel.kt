package com.finance.android.walletwise.ui.viewmodel.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.android.walletwise.model.Category.CategoryRepository
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.model.Category.isValid
import com.finance.android.walletwise.model.Category.toCategory
import com.finance.android.walletwise.model.Category.toCategoryUIState
import com.finance.android.walletwise.ui.activity.DetailCategoryDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditCategoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    var categoryUIState by mutableStateOf(CategoryUIState())
        private set

    private val categoryId: Int = checkNotNull(savedStateHandle[DetailCategoryDestination.categoryIdArg])

    init {
        viewModelScope.launch {
            categoryUIState = categoryRepository.getCategoryStream(categoryId)
                .filterNotNull()
                .first()
                .toCategoryUIState(actionEnabled = true)
        }
    }

    fun updateUiState(newItemUiState: CategoryUIState){
        categoryUIState=newItemUiState.copy(actionEnabled = newItemUiState.isValid())
    }
    suspend fun updateCategory(){
        if(categoryUIState.isValid()){
            categoryRepository.updateCategory(categoryUIState.toCategory())
        }
    }
    suspend fun deleteCategory() {
        categoryRepository.deleteCategory(categoryUIState.toCategory())
    }
}

