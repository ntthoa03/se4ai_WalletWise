package com.finance.android.walletwise.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.walletwise.R
import com.finance.android.walletwise.addTransactionScreen
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Transaction.Transaction
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.activity.TransactionCard
import com.finance.android.walletwise.ui.fragment.BalanceSection
import com.finance.android.walletwise.ui.fragment.FAButton
import com.finance.android.walletwise.ui.fragment.WalletWiseBottomBar
import com.finance.android.walletwise.ui.fragment.WalletWiseTopAppBar
import com.finance.android.walletwise.ui.viewmodel.CategoryViewModel
import com.finance.android.walletwise.util.categoryIconsList
import kotlinx.coroutines.launch

@Composable
fun CategoryListScreen(
    viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    onNavigateToAddCategory: () -> Unit,)
{
    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
    }


    val categoryListState by viewModel.expenseCategories.collectAsState()

    Scaffold(
        floatingActionButton = {
            FAButton(
                onClick = { onNavigateToAddCategory() },
                buttonColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier,
                icon = Icons.Default.Add,
                contentDescription = "Add Transaction"
            )
        },
        floatingActionButtonPosition = FabPosition.End,)
    {innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5)), )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                BalanceSection(
                    title = "REMAINING BUDGET",
                    balance = "1500000",
                    currency = "VND"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CategoryList(categoryListState)
            }
        }
    }
}
@Composable
fun CategoryList(categoryList: List<Category>) {
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        items(items = categoryList, key = { it.id }) { item ->
            BudgetProgressCard(category = item)
        }
    }
}


@Composable
fun LinearProgress() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(
            progress = 0.5f,
            color = Color(0xFF70AA72),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.Gray.copy(alpha = 0.2f))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "15/30 days", fontSize = 12.sp)
            Text(text = "1,500,000", fontSize = 12.sp)
            Text(text = "3,000,000", fontSize = 12.sp)
        }
    }
}


@Composable
fun BudgetProgressCard(category: Category ){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(), // Use CardDefaults.cardElevation() instead of specific value
    ) {
        // Add padding inside the Card
        Box(modifier = Modifier.padding(16.dp)) {
            BudgetProgress(category)
        }
    }
}

@Composable
fun BudgetProgress(category: Category ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = categoryIconsList[category.icon] ?: R.drawable.ic_category),
            contentDescription = "Category Icon: $category.icon",
            modifier = Modifier
                .size(45.dp)
                .padding(end = 8.dp)
        )
        // Row contains icon and text
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = category.name, modifier = Modifier.weight(20f), fontSize = 14.sp)
                Text(text = "30%", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = 30/ 100f,
                color = Color(0xFF7C99EE),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
        }
    }
}
