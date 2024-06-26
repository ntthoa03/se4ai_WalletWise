package com.finance.android.walletwise.ui.activity.transaction


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.walletwise.R
import com.finance.android.walletwise.addTransactionScreen
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.viewmodel.transaction.TransactionsScreenViewModel
import com.finance.android.walletwise.model.Transaction.Transaction
import com.finance.android.walletwise.ui.fragment.FAButton
import com.finance.android.walletwise.ui.viewmodel.category.CategoryViewModel
import com.finance.android.walletwise.util.categoryIconsList
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListExpenseScreen(
    viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    navController: NavController, )
{
    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
    val transactionsScreenUiStateToday by viewModel.transactionsScreenUiStateToday.collectAsState()
    val transactionsScreenUiStateWeek by viewModel.transactionsScreenUiStateWeek.collectAsState()
    val transactionsScreenUiStateMonth by viewModel.transactionsScreenUiStateMonth.collectAsState()
    val transactionsScreenUiStateYear by viewModel.transactionsScreenUiStateYear.collectAsState()

    Scaffold(
        floatingActionButton = {
            FAButton(
                onClick = { navController.navigate(addTransactionScreen.route) },
                buttonColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier,
                icon = Icons.Default.Add,
                contentDescription = "Add Transaction"
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
        {
            Column {
                Spacer(modifier = Modifier.height(60.dp))
                val x = selecttime(items = listOf("Today", "Week", "Month", "Year"))
                Spacer(modifier = Modifier.height(25.dp))

                if (transactionScreenUiState.transactionList.isEmpty()) {
                    Text(
                        text = "No Items",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(15.dp)
                    )
                } else {
                    ExpenseList(
                        transactionList =
                        when (x) {
                            0 -> transactionsScreenUiStateToday.transactionList
                            1 -> transactionsScreenUiStateWeek.transactionList
                            2 -> transactionsScreenUiStateMonth.transactionListt
                            else -> transactionsScreenUiStateYear.transactionList
                        },
                        navController = navController
                    )
                }
            }
        }
    }

}

@Composable
fun ExpenseList(transactionList: List<Transaction>,navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        items(items = transactionList, key = { it.id }) { item ->
            TransactionCard(
                transaction = item,
                navController = navController)
        }
    }
}


@Composable
fun TransactionCard(
    transaction: Transaction,
    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController, )
{
    LaunchedEffect(Unit) {
        categoryViewModel.getAllCategories()
    }

    val categoryListState by categoryViewModel.expenseCategories.collectAsState()
    //Debug log to check category list state
    Log.d("TransactionCard", "Category List: ${categoryListState.joinToString { it.name }}")

    val selectedCategory = categoryListState.find { it.id == transaction.idCategory }
    //Debug log to check selected category
    Log.d("TransactionCard", "Selected Category: $selectedCategory")
    val selectedCategoryName = selectedCategory?.name ?: "Name Category"
    val selectedCategoryIcon = selectedCategory?.icon ?: "Icon Category"

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm").format(transaction.time)
        }
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                if (transaction.type.equals("Expense"))
                {
                    navController.navigate("${TransactionEditDestination.route}/${transaction.id}")
                }
                else
                {
                    navController.navigate("${IncomeTransactionEditDestination.route}/${transaction.id}")
                }
            },
    )
    {
        if (transaction.type == "Expense")
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,)
            {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .padding(5.dp))
                    {
                        Image(
                            painter = painterResource(id = categoryIconsList[selectedCategoryIcon] ?: R.drawable.ic_category),
                            contentDescription = "Category Icon: $selectedCategoryIcon",
                            modifier = Modifier.size(40.dp), )
                    }
                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        modifier = Modifier.padding(10.dp), )
                    {
                        Text(
                            text = selectedCategoryName,
                            fontSize = 18.sp,
                            color = Color(0xFF292B2D), )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = transaction.description,
                            fontSize = 15.sp,
                            color = Color(0xFF91919F),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier=Modifier.fillMaxWidth(0.75f), )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = transaction.amount.toString(), fontSize = 18.sp,
                        color = Color(0xFFFD3C4A)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(text = formattedTime, fontSize = 13.sp, color = Color(0xFF91919F))
                    }
                }
            }
        }
        else
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,)
            {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically, )
                {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .padding(5.dp), )
                    {
                        Image(
                            painter = painterResource(id = categoryIconsList[selectedCategoryIcon] ?: R.drawable.ic_category),
                            contentDescription = "Category Icon: $selectedCategoryIcon",
                            modifier = Modifier.size(40.dp), )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        modifier = Modifier.padding(10.dp), )
                    {
                        Text(
                            text = selectedCategoryName,
                            fontSize = 18.sp,
                            color = Color(0xFF292B2D), )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = transaction.description,
                            fontSize = 15.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = Color(0xFF91919F),
                            modifier = Modifier.fillMaxWidth(0.75f)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = transaction.amount.toString(), fontSize = 18.sp,
                        color = Color(0xFF00A86B)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(
                            text = formattedTime,
                            fontSize = 13.sp,
                            color = Color(0xFF91919F), )
                    }
                }

            }

        }
    }
}

@Composable
fun selecttime(items: List<String>, modifier: Modifier= Modifier,
               activeHighlightColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
               activeTextColor: Color = MaterialTheme.colorScheme.primary,
               inactiveTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
               initialSelectedItemIndex: Int=0): Int
{
    var selecteditemindex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        items.forEachIndexed{index,item->
            rangeselectoritem(item= item,
                isSelected = index==selecteditemindex,
                activeHighlightColor=activeHighlightColor,
                activeTextColor=activeTextColor,
                inactiveTextColor=inactiveTextColor){
                selecteditemindex=index
            }
        }

    }
    return selecteditemindex
}

@Composable
fun rangeselectoritem(
    item: String,
    isSelected: Boolean = false,
    activeHighlightColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    activeTextColor: Color = MaterialTheme.colorScheme.primary,
    inactiveTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
    onItemClick: () ->Unit, )
{
    Row(horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onItemClick()
        }, )
    {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (isSelected) activeHighlightColor
                    else Color.Transparent
                )
                .padding(10.dp), )
        {
            Text(
                text = item,
                color= if(isSelected) activeTextColor else inactiveTextColor,
                fontSize = 14.sp
            )
        }
    }
}