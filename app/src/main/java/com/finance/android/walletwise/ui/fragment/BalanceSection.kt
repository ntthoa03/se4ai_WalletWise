package com.finance.android.walletwise.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.util.formatBalance

import java.text.NumberFormat
import java.util.Locale

/**
 * BALANCE SECTION
 */
@Preview(showBackground = true)
@Composable
fun PreviewBalanceSection() {
    WalletWiseTheme {
        BalanceSection(
            title = "Remaining Budget",
            balance = "1000000",
            currency = "VND",
            showTitle = true,
            showCurrencyBackground = true,
            currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun BalanceSection(
    title: String,
    balance: String,
    currency: String,
    showTitle: Boolean = true,
    showCurrencyBackground: Boolean = true,
    currencyBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), )
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .background(colorResource(id = R.color.md_theme_background)),
        contentAlignment = Alignment.Center, )
    {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally, )
        {
            if (showTitle)
            {
                Text(
                    text = title,
                    modifier = Modifier
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color.Black.copy(alpha = 0.2f),
                                start = androidx.compose.ui.geometry.Offset(0f, y),
                                end = androidx.compose.ui.geometry.Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(2.dp),
                    style = MaterialTheme.typography.titleSmall, )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically, )
            {
                if (showCurrencyBackground)
                {
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(end = 8.dp)
                            .background(
                                color = currencyBackgroundColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(2.dp)
                            .padding(start = 3.dp)
                            .padding(end = 3.dp), )
                    {
                        Text(
                            text = currency,
                            style = MaterialTheme.typography.titleSmall, )
                    }
                }
                else
                {
                    Text(
                        text = currency,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp) )
                }

                Text(
                    text = formatBalance(balance),
                    modifier = Modifier.padding(top = 0.dp),
                    style = MaterialTheme.typography.displayLarge, )
            }
        }
    }
}

/**
 * BALANCE BOX
 */
@Preview(showBackground = true)
@Composable
fun PreviewBalanceBox() {
    WalletWiseTheme {
        BalanceBox(
            amount = "1000000",
            label = "Income",
            icon = Icons.Default.KeyboardArrowDown,
            backgroundColor = Color.Green.copy(alpha = 0.2f),
            textColor = Color.DarkGray,
            onClick = {})
    }
}

@Composable
fun BalanceBox(
    amount: String,
    label: String,
    icon: ImageVector,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier, )
{
    Card(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp), )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(backgroundColor)
                .align(Alignment.CenterHorizontally),
        )
        {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center, )
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = textColor, )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = textColor, )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatBalance(amount),
                    style = MaterialTheme.typography.headlineSmall,
                    color = textColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally), )
            }
        }
    }
}

/**
 * DETAILED SECTION
 */
@Preview(showBackground = true)
@Composable
fun PreviewDetailedBalanceSection() {
    WalletWiseTheme {
        DetailedBalanceSection(
            incomeAmount = "10000000",
            outcomeAmount = "500000",
            onIncomeClick = {},
            onOutcomeClick = {}, )
    }
}

@Composable
fun DetailedBalanceSection(
    incomeAmount: String,
    outcomeAmount: String,
    onIncomeClick: () -> Unit = {},
    onOutcomeClick: () -> Unit = {}, )
{
    Row(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround, )
    {
        //Income Box
        BalanceBox(
            amount = incomeAmount,
            label = "Income",
            icon = Icons.Default.KeyboardArrowDown,
            backgroundColor = Color(0xFFC8E6C9),
            textColor = Color.DarkGray,
            modifier = Modifier.weight(1f),
            onClick = onIncomeClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        //Outcome Box
        BalanceBox(
            amount = outcomeAmount,
            label = "Expense",
            icon = Icons.Default.KeyboardArrowUp,
            backgroundColor = Color(0xFFF8BBD0),
            textColor = Color.DarkGray,
            modifier = Modifier.weight(1f),
            onClick = onOutcomeClick
        )
    }
}
