package com.finance.android.walletwise.ui.fragment

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme

/**
 * Normal button
 */
@Preview(showBackground = true)
@Composable
fun PreviewNormalButton()
{
    WalletWiseTheme {
        NormalButton(
            text = "Button",
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun NormalButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean   = true,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color    = MaterialTheme.colorScheme.onPrimary )
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

/**
 * Icon button with label
 */
@Preview(showBackground = true)
@Composable
fun PreviewNormalIconLabelButton()
{
    WalletWiseTheme {
        NormalIconLabelButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_category),
            text = "Category",
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun NormalIconLabelButton(
    icon: Any,
    text: String,
    onClick: () -> Unit,)
{
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White), )
    {
        when (icon)
        {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = text, )
            }
            is Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = text,)
            }
            else -> throw IllegalArgumentException("Unsupported icon type")
        }
        Spacer(Modifier.width(8.dp))
        Text(text = text)
    }
}

/**
 * Icon button
 */
@Preview(showBackground = true)
@Composable
fun PreviewNormalIconButton() {
    WalletWiseTheme {
        NormalIconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_category),
            contentDescription = "Category",
            onClick = { /*TODO*/ })
    }
}

@Composable
fun NormalIconButton(
    icon: Any,
    contentDescription: String = "",
    onClick: () -> Unit,)
{
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White), )
    {
        when (icon)
        {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription, )
            }
            is Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = contentDescription,)
            }
            else -> throw IllegalArgumentException("Unsupported icon type")
        }
    }
}