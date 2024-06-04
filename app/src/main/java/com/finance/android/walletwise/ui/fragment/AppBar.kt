package com.finance.android.walletwise.ui.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme

@Preview(showBackground = true)
@Composable
fun PreviewWalletWiseTopAppBar() {
    WalletWiseTheme {
        WalletWiseTopAppBar(
            title = "WalletWise",
            useIconForTitle = true,
            showNavigationButton = true,
            showActionButton = true,
            onNavigationClick = { /*TODO*/ },
            onActionClick = { /*TODO*/ },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWalletWiseNavigationBar() {
    WalletWiseTheme {
        WalletWiseBottomBar(
            selectedTab = 1,
            onTabSelected = { /*TODO*/ },
            onHomeClick = { /*TODO*/ },
            onExpenseListClick = { /*TODO*/ },
            onCategoryListClick = { /*TODO*/ },
            onSettingsClick = {/* TODO */},
        )
    }
}

//TopAppBar composable =============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletWiseTopAppBar(
    title: String,
    useIconForTitle: Boolean = true,
    showNavigationButton: Boolean = true,
    navigationButton: @Composable (() -> Unit)? = null,
    onNavigationClick: () -> Unit = {},
    showActionButton: Boolean = true,
    actionButton: @Composable (() -> Unit)? = null,
    onActionClick: () -> Unit = {}, )
{
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp

    TopAppBar(
        modifier = Modifier
            .height((screenHeight*0.06).dp)
            .fillMaxWidth(),
        title = {
            if (useIconForTitle)
            {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center, )
                {
                    Image(
                        painter = painterResource(id = R.drawable.application_logo),
                        contentDescription = "WalletWise application text logo", )
                }
            }
            else
            {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall, )
            }
        },
        navigationIcon = {
            if (showNavigationButton)
            {
                IconButton(onClick = onNavigationClick)
                {
                    navigationButton?.invoke() ?: Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            if (showActionButton)
            {
                IconButton(onClick = onActionClick)
                {
                    actionButton?.invoke() ?: Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }
        }
    )
}


//NavigationAppBar composable ======================================================================
@Composable
fun WalletWiseBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onExpenseListClick: () -> Unit,
    onCategoryListClick: () -> Unit,
    onSettingsClick: () -> Unit, )
{
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp

    NavigationBar(
        modifier = Modifier
            .height((screenHeight*0.06).dp)
            .fillMaxWidth(), )
    {
        //HOME
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home")
            },
            selected = selectedTab == 0,
            onClick = {
                onTabSelected(0)
                onHomeClick()
            }
        )

        //EXPENSE LIST
        NavigationBarItem(
            icon = {
                Icon(Icons.Default.List,
                    contentDescription = "Expense List")
            },
            selected = selectedTab == 1,
            onClick = {
                onTabSelected(1)
                onExpenseListClick()
            }
        )

        //CATEGORY LIST
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_category),
                    contentDescription = "Category List")
            },
            selected = selectedTab == 2,
            onClick = {
                onTabSelected(2)
                onCategoryListClick()
            }
        )

        //SETTING
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings")
            },
            selected = selectedTab == 3,
            onClick = {
                onTabSelected(3)
                onSettingsClick()
            }
        )

    }
}

//Test with Top App Bar and Navigation App Bar
//@Preview(showBackground = true)
//@Composable
//fun PreviewWalletWiseBottomBar()
//{
//    WalletWiseTheme {
//        Scaffold(
//            topBar = {
//                WalletWiseTopAppBar(
//                    title = "WalletWise",
//                    useIconForTitle = true,
//                    showNavigationButton = true,
//                    showActionButton = true,
//                    onNavigationClick = { /*TODO*/ },
//                    onActionClick = { /*TODO*/ }, ) },
//            bottomBar = {
//                WalletWiseBottomBar(
//                    selectedTab = 1,
//                    onTabSelected = { /*TODO*/ },
//                    onHomeClick = { /*TODO*/ },
//                    onExpenseListClick = { /*TODO*/ },
//                    onCategoryListClick = { /*TODO*/ },
//                    onSettingsClick = {/* TODO */}, ) },
//            content = { /* TODO */ }
//        )
//    }
//}