package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import com.finance.android.walletwise.WalletWiseDestination
import com.finance.android.walletwise.model.Transaction.TransactionUiState
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.viewmodel.EditTransactionViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.fragment.NormalButton
import com.finance.android.walletwise.ui.fragment.WalletWiseTopAppBar
import com.finance.android.walletwise.ui.viewmodel.CategoryViewModel
import com.finance.android.walletwise.ui.viewmodel.ExpenseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object TransactionEditDestination : WalletWiseDestination {
    override val route = "transaction_edit"

    override val icon: ImageVector = Icons.Default.Home
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}
object IncomeTransactionEditDestination : WalletWiseDestination {
    override val route = "transaction_editincome"
    override val icon: ImageVector = Icons.Default.Home
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}

@Composable
fun EditScreenExpense(
    modifier: Modifier = Modifier,
    viewModel: EditTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    onBackClick: () -> Unit, )
{
    Scaffold(
        topBar = {
            WalletWiseTopAppBar(
                title = "Edit Transaction",
                useIconForTitle = false,
                showNavigationButton = true,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = { onBackClick() },
                showActionButton = false,
            )
        },
    ) {innerPadding ->
        EditExpense(
            modifier = Modifier.padding(innerPadding),
            navigateBack = onBackClick, transactionUiState = viewModel.transactionUiState)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpense(
    modifier: Modifier = Modifier,
    viewModel: EditTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    transactionUiState: TransactionUiState, )
{
    var selectedChipIndex by remember { mutableStateOf(0) }
    val chipTitles = listOf("EXPENSE", "INCOME")
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier
        .fillMaxSize()
        .padding(30.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            EditableAmountField(
                value = transactionUiState.amount,
                transactionUiState = transactionUiState,
                onValueChange=viewModel::updateUiState

            )
            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(), )
            {
                chipTitles.forEachIndexed { index, title ->
                    InputChip(
                        selected =  selectedChipIndex == index,
                        onClick = { selectedChipIndex = index },
                        colors = InputChipDefaults.inputChipColors(
                            selectedContainerColor = if (selectedChipIndex == index) colorResource(R.color.md_theme_primaryFixed) else colorResource(
                                R.color.md_theme_background),
                            labelColor = colorResource(R.color.md_theme_onBackground)
                        ),
                        label = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(title,
                                    color = colorResource(R.color.md_theme_onBackground)
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(0.dp)
                            .background(
                                shape = RectangleShape,
                                color = if (selectedChipIndex == index) colorResource(R.color.md_theme_primaryFixed) else colorResource(
                                    R.color.md_theme_background
                                )
                            )
                            .size(180.dp, 40.dp),  // Tùy chỉnh kích thước
                    )
                }
            }

            when (selectedChipIndex) {
                0 -> EditChipExpense(transactionUiState = viewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
//                1 -> ExditChipIncome()
                1 -> EditChipExpense(transactionUiState = viewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
            }

        }
    }

}


@Composable
fun EditChipExpense(transactionUiState: TransactionUiState,
                    expenseViewModel: EditTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                    navigateBack: () -> Unit,
                    coroutineScope: CoroutineScope
) {
    //var expanded by remember { mutableStateOf(false) } // Khởi tạo là false để menu không mở ban đầu


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            CategoryDropdown(transactionUiState = expenseViewModel.transactionUiState, categoryUiState = categoryViewModel.categoryUiState, onValueChange = expenseViewModel::updateUiState)



            datetimepicker(onValueChange=expenseViewModel::updateUiState, transactionUiState = transactionUiState)
            //Note
            DescriptionTextField(transactionUiState=transactionUiState,onValueChange=expenseViewModel::updateUiState)

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            NormalButton(
                text = "Save",
                onClick = { coroutineScope.launch {
                    expenseViewModel.updateTransaction()
                    navigateBack()
                }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}

