package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.BalanceSection
import com.finance.android.walletwise.ui.fragment.DetailedBalanceSection
import com.finance.android.walletwise.ui.fragment.FAButton
import com.finance.android.walletwise.ui.fragment.NormalIconLabelButton
import com.finance.android.walletwise.ui.fragment.WalletWiseBottomBar
import com.finance.android.walletwise.ui.fragment.WalletWiseTopAppBar
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.fragment.NormalIconButton

/**
 * Home Screen
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview()
{
    WalletWiseTheme {
        HomeScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen()
{
    //val scaffoldState = rememberScaffoldState()
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        //scaffoldState = scaffoldState,
        //TOP APP BAR
        topBar = {
            WalletWiseTopAppBar(
                title = "WalletWise",
                useIconForTitle = true,
                showNavigationButton = true,
                showActionButton = true,
                onNavigationClick = { /*TODO*/ },
                onActionClick = { /*TODO*/ },
            )
        },
        //BOTTOM BAR
        bottomBar = {
            WalletWiseBottomBar(
                selectedTab = 0,
                onTabSelected = { /*TODO*/ },
                onHomeClick = { /*TODO*/ },
                onExpenseListClick = { /*TODO*/ },
                onCategoryListClick = { /*TODO*/ },
                onSettingsClick = {/* TODO */},
            )
        },
        //FAB
        floatingActionButton = {
            FAButton(
                onClick = { /*TODO*/ },
                icon = Icons.Default.Add,
                contentDescription = "Add Transaction"
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        //isFloatingActionButtonDocked = true
    )
    { innerPadding ->
        HomeScreenContent(
            balance = "1000000",
            currency = "VND",
            incomeAmount = "100000",
            outcomeAmount = "50000",
            innerPadding = innerPadding
        )
        /*when (selectedTab) {
            0 -> HomeScreen(innerPadding)
            1 -> ExpenseListScreen(innerPadding)
            2 -> CategoryListScreen(innerPadding)
            3 -> SettingsScreen(innerPadding)
        }*/
    }
}

/**
 * Home screen content
 */
@Composable
fun HomeScreenContent(
    balance: String,
    currency: String,
    incomeAmount: String,
    outcomeAmount: String,
    innerPadding: PaddingValues)
{
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), )
    {
        //BALANCE SECTION
        item {
            BalanceSection(
                title = "Balance",
                balance = balance,
                currency = currency,
                showTitle = false,
                showCurrencyBackground = true,
                currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            )
        }
        //DETAILED BALANCE SECTION BOXES
        item {
            DetailedBalanceSection(
                incomeAmount = incomeAmount,
                outcomeAmount = outcomeAmount,
                onIncomeClick = {},
                onOutcomeClick = {},
                )
        }
        //QUICK ACCESS BAR
        item {
            QuickAccessBar()
        }
        //Divider
        item {
            Divider(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .padding(top = 16.dp, bottom = 16.dp),
                color = Color.Gray.copy(alpha = 0.5f),
                thickness = 1.dp)
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

/**
 * Quick Access Bar
 */
@Preview(showBackground = true)
@Composable
fun PreviewQuickAccessBar() {
    WalletWiseTheme {
        QuickAccessBar(
            onAnalysisClick = { /*TODO*/ },
            onAIChatClick = { /*TODO*/ },
            onRemindClick = { /*TODO*/ },
        )
    }
}

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

