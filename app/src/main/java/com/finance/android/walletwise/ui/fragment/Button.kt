package com.finance.android.walletwise.ui.fragment

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

import com.finance.android.walletwise.R

@Composable
fun NormalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = colorResource(id = R.color.md_theme_primary),
    contentColor: Color = colorResource(id = R.color.white) )
{
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor, contentColor = contentColor),
        shape = RoundedCornerShape(50.dp) )
    {
        Text(text = text)
    }
}