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

@Composable
fun DetailedBalanceSection_2(
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
            label = "Remaining",
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

