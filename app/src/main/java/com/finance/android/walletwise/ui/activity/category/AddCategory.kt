package com.finance.android.walletwise.ui.activity.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.fragment.NormalButton
import androidx.compose.ui.res.painterResource
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.fragment.NormalSwitch
import com.finance.android.walletwise.ui.viewmodel.category.CategoryViewModel
import com.finance.android.walletwise.util.categoriesList
import com.finance.android.walletwise.util.categoryIconsList
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
//@Composable
//fun AddCategoryScreenPreview() {
//    WalletWiseTheme {
//        AddCategoryScreen(
//            text = "Repeat this budget category",
//            isChecked = true,
//            onCheckedChange = {}
//        )
//    }
//}
@Composable
fun ScreenAddCategory(
    viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit)
{
    AddCategoryScreen(
        categoryUIState = viewModel.categoryUiState,
        navigateBack = navigateBack, )
}

@Composable
fun AddCategoryScreen(
    categoryUIState: CategoryUIState,
    viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit
) {
    var isChecked by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Category icon:", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))

        IconButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier
                .padding(top = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                ),
        ) {
            Image(
                painter = painterResource(
                    id = categoryIconsList[categoryUIState.icon] ?: R.drawable.ic_category
                ),
                contentDescription = "Category Icon: ${categoryUIState.icon}",
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        NameCategoryTextField(categoryUIState = viewModel.categoryUiState, onValueChange = viewModel::updateUiState)

        Spacer(modifier = Modifier.height(16.dp))
        BudgetTextField(categoryUIState = viewModel.categoryUiState, onValueChange = viewModel::updateUiState)

        Spacer(modifier = Modifier.height(16.dp))

        NormalSwitch(
            text = "Repeat this budget category",
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        NormalButton(
            text = "Save",
            onClick = {
                coroutineScope.launch {
                    viewModel.saveCategory()
                    navigateBack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        if (showBottomSheet) {
            IconsBottomSheet(
                categoryUIState = viewModel.categoryUiState,
                onIconSelected = { icon ->
                    viewModel.updateUiState(viewModel.categoryUiState.copy(icon = icon))
                    showBottomSheet = false
                },
                onDismissRequest = { showBottomSheet = false },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconsBottomSheet(
    categoryUIState: CategoryUIState,
    onIconSelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Choose Category Icon",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 64.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categoriesList.size) { index ->
                    IconButton(
                        onClick = { onIconSelected(categoriesList[index]) },
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = CircleShape
                            )
                    ) {
                        Image(
                            painter = painterResource(id = categoryIconsList[categoriesList[index]] ?: R.drawable.ic_category),
                            contentDescription = "Category Icon: ${categoriesList[index]}",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NameCategoryTextField(
    categoryUIState: CategoryUIState,
    onValueChange: (CategoryUIState) -> Unit = {}
) {
    OutlinedTextField(
        value = categoryUIState.name,
        onValueChange = { onValueChange(categoryUIState.copy(name = it)) },
        label = { Text("Name") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BudgetTextField(
    categoryUIState: CategoryUIState,
    onValueChange: (CategoryUIState) -> Unit = {}
) {
    OutlinedTextField(
        value = categoryUIState.amount,
        onValueChange = { onValueChange(categoryUIState.copy(amount = it)) },
        label = { Text("Budget") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}