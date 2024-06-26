package com.finance.android.walletwise.ui.screen

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
import com.finance.android.walletwise.addCategoryScreen
import com.finance.android.walletwise.addTransactionScreen
import com.finance.android.walletwise.model.Category.Category

import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.activity.DetailCategoryDestination

import com.finance.android.walletwise.ui.fragment.BalanceSection
import com.finance.android.walletwise.ui.fragment.FAButton

import com.finance.android.walletwise.ui.viewmodel.category.CategoryViewModel
import com.finance.android.walletwise.util.categoryIconsList
import kotlinx.coroutines.launch
@Composable
fun CategoryListScreen(
    viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory), // Inject your repository here
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
    }
    val totalBudget by viewModel.totalBudget.collectAsState()
    val categoryListState by viewModel.expenseCategories.collectAsState()

    Scaffold(
        floatingActionButton = {
            FAButton(
                onClick = { navController.navigate(addCategoryScreen.route) },
                buttonColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier,
                icon = Icons.Default.Add,
                contentDescription = "Add Category"
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                BalanceSection(
                    title = "TOTAL BUDGET",
                    balance = totalBudget.toString(),
                    currency = "VND"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CategoryList(categoryListState, navController = navController)
            }
        }
    }
}
@Composable
fun CategoryList(categoryList: List<Category>, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        items(items = categoryList, key = { it.id }) { item ->
            BudgetProgressCard(category = item,navController = navController)
        }
    }
}

@Composable
fun BudgetProgressCard(
    category: Category,
    navController: NavController, )
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("${DetailCategoryDestination.route}/${category.id}") }
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(),)
    {
        Box(modifier = Modifier
            .padding(16.dp)) {
            BudgetProgress(category)
        }
    }
}

@Composable
fun BudgetProgress(category: Category )
{
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
                Text(text = category.amount.toString(), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
