package com.finance.android.walletwise.ui.fragment

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme


/**
 * WalletWiseTopAppBar =============================================================================
 */
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


/**
 * WalletWiseBottomBar =============================================================================
 */
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

/**
 * TransactionAppBar ===============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    topBarState: Boolean
)  {
    AnimatedVisibility(visible = topBarState) {
        TopAppBar(
            //backgroundColor =Color(colorAppBar),
            title = { Text(title, fontSize = 18.sp)},
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(painter = painterResource(id = R.drawable.ic_chat_finance), contentDescription = "Back")
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletWiseTopAppBar(
    title: String,
    useIconForTitle: Boolean = true,
    showNavigationButton: Boolean = true,
    navigationIcon: ImageVector = Icons.Default.Menu,
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,)
                {
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                    )
                }
            }
        },
        navigationIcon = {
            if (showNavigationButton)
            {
                IconButton(onClick = onNavigationClick)
                {
                    navigationButton?.invoke() ?: Icon(navigationIcon, contentDescription = navigationIcon.name)
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