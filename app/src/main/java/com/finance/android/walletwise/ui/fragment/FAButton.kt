package com.finance.android.walletwise.ui.fragment

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.WalletWiseTheme

@Preview(showBackground = true)
@Composable
fun PreviewFAButton() {
    WalletWiseTheme {
        FAButton(
            onClick = { /*TODO*/ },
            icon = Icons.Default.Add,
            contentDescription = "Add Transaction"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAButton(
    onClick: () -> Unit,
    icon: ImageVector? = null,
    iconResId: Int? = null,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = buttonColor,
        modifier = Modifier.padding(16.dp), )
    {
        if (icon != null)
        {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription, )
        }
        else if (iconResId != null)
        {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = contentDescription, )
        }
    }
}