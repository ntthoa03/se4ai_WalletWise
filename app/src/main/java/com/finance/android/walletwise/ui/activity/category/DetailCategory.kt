package com.finance.android.walletwise.ui.activity.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.BalanceSection
import com.finance.android.walletwise.ui.fragment.DetailedBalanceSection_2
import com.finance.android.walletwise.ui.fragment.NormalSwitch


@Preview(showBackground = true)
@Composable
fun BudgetDetailScreenPreview() {
    WalletWiseTheme {
        BudgetDetailScreen(
            balance = "210000",
            currency = "VND",
            incomeAmount = "300000",
            outcomeAmount = "90000",
            text = "Repeat this budget category",
            isChecked = true,
            onCheckedChange = {}
        )
    }
}

@Composable
fun BudgetDetailScreen(balance: String,
                       currency: String,
                       incomeAmount: String,
                       outcomeAmount: String,
                       text: String,
                       isChecked: Boolean,
                       onCheckedChange: (Boolean) -> Unit,
                       modifier: Modifier = Modifier
) {
    var isChecked by remember { mutableStateOf(true)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Detail",
                style = MaterialTheme.typography.headlineSmall
            )

            IconButton(onClick = { /* Close action */ }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.category_icon_food___drink), // replace with your drawable resource
                contentDescription = "Food & Drink",
                modifier = Modifier.size(39.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Food & Drink",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        }
        //Divider
        Divider(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .padding(top = 16.dp, bottom = 16.dp),
            color = Color.Gray.copy(alpha = 0.5f),
            thickness = 1.dp)

        BalanceSection(
            title = "BUDGET",
            balance = balance,
            currency = currency,
            showTitle = true,
            showCurrencyBackground = true,
            currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        )


        Spacer(modifier = Modifier.height(16.dp))

        DetailedBalanceSection_2(
            incomeAmount = incomeAmount,
            outcomeAmount = outcomeAmount,
            onIncomeClick = {},
            onOutcomeClick = {},
        )

        Spacer(modifier = Modifier.height(16.dp))

        NormalSwitch(text = "Repeat this budget category",
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(
            onClick = { /* Edit action */ },
            modifier = Modifier
                .align(Alignment.End)
                .offset(x = (130).dp)
                .offset(y = (180).dp)
                .padding(150.dp)
                .background(Color(0xFFE3F2FD), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }
    }
}
