package com.finance.android.walletwise.ui.activity

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.ui.fragment.BalanceSection
import com.finance.android.walletwise.ui.fragment.DetailedBalanceSection
import com.finance.android.walletwise.ui.fragment.NormalIconLabelButton
import com.finance.android.walletwise.R
import com.finance.android.walletwise.addTransactionScreen
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.fragment.FAButton
import com.finance.android.walletwise.ui.fragment.FAButtonCircle
import com.finance.android.walletwise.ui.fragment.NormalIconButton
import com.finance.android.walletwise.ui.viewmodel.transaction.TransactionsScreenViewModel
import kotlin.math.cos
import kotlin.math.sin

/**
 * Home Screen -------------------------------------------------------------------------------------
 */
@Composable
fun HomeScreen(
    currency: String = "VND",
    onNavigateToAddTransaction: () -> Unit = {},
    quickAccessOnAnalysisClick:() -> Unit = {},
    quickAccessOnAIChatClick: () -> Unit = {},
    quickAccessOnRemindClick: () -> Unit = {},
    viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory), )
{
    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()

    Scaffold(
        floatingActionButton = {
            FAButton(
                onClick = { onNavigateToAddTransaction() },
                buttonColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier,
                icon = Icons.Default.Add,
                contentDescription = "Add Transaction"
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), )
        {
            //BALANCE SECTION
            item {
                BalanceCard(
                    currency = currency,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                )
            }
            //QUICK ACCESS BAR
            item {
                QuickAccessBar(
                    onAnalysisClick = { quickAccessOnAnalysisClick() },
                    onAIChatClick   = { quickAccessOnAIChatClick()   },
                    onRemindClick   = { quickAccessOnRemindClick()   },
                )
            }
            //Divider
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .padding(top = 16.dp, bottom = 16.dp),
                    thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f)
                )
            }
            //QUICK VIEW OF TRANSACTIONS LIST
            //item {
            //    ExpenseList(expenses = listOf("Coffee - $5", "Groceries - $30", "Dinner - $20")) // Replace with actual data
            //}

            //QUICK VIEW OF CATEGORIES LIST
            //item {
            //    CategoryList(categories = listOf("Food", "Entertainment", "Transport")) // Replace with actual data
            //}
        }
    }
}

@Composable
fun BalanceCard(
    modifier: Modifier = Modifier,
    currency: String = "",
    totalIncome: Double,
    totalExpense: Double, )
{
    Log.d("BalanceCard", "totalIncome: $totalIncome, totalExpense: $totalExpense")
    Column(
        modifier = modifier,
    ) {
        BalanceSection(
            title = "Balance",
            balance = (totalIncome-totalExpense).toInt().toString(),
            currency = currency,
            showTitle = false,
            showCurrencyBackground = true,
            currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), )

        DetailedBalanceSection(
            incomeAmount = totalIncome.toInt().toString(),
            outcomeAmount = totalExpense.toInt().toString(),
            onIncomeClick = {},
            onOutcomeClick = {},
        )
    }
}


/**
 * Quick Access Bar --------------------------------------------------------------------------------
 */
@Composable
fun QuickAccessBar(
    onAnalysisClick: () -> Unit = {},
    onAIChatClick: () -> Unit = {},
    onRemindClick: () -> Unit = {})
{
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically, )
    {
        //Analysis Button
        NormalIconLabelButton(
            icon = painterResource(R.drawable.ic_analytics_filled),
            text = "Analysis",
            onClick = onAnalysisClick
        )
        //AI Chat Button (Icon Only)
        NormalIconButton(
            contentDescription = "AI Chat",
            onClick = onAIChatClick,
            icon = painterResource(R.drawable.ic_chat_finance), )

        //Remind Button
        NormalIconLabelButton(
            icon = painterResource(R.drawable.ic_remind_event),
            text = "Remind",
            onClick = onRemindClick
        )
    }
}