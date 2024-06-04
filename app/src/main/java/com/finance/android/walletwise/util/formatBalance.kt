package com.finance.android.walletwise.util

import java.text.NumberFormat
import java.util.Locale

fun formatBalance(balance: String): String
{
    val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
    return numberFormat.format(balance.toLongOrNull() ?: 0)
}