package com.finance.android.walletwise.ui.fragment

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R

/**
 * TEXT FIELD
 */
@Preview(showBackground = true)
@Composable
fun PreviewNormalTextField()
{
    NormalTextField(
        value = "Value",
        onValueChange = { /* TODO */},
        label = "Label",
        modifier = Modifier)
}

@Composable
fun NormalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null)
{
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        trailingIcon = {
            if (value.isNotEmpty())
            {
                IconButton(onClick = { onValueChange("") })
                {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Text")
                }
            }
        }
    )
}

/**
 * PASSWORD FIELD
 */
@Preview(showBackground = true)
@Composable
fun PreviewPasswordField()
{
    PasswordField(
        value = "Value",
        onValueChange = { /* TODO */},
        label = "Label",
        modifier = Modifier)
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default)
{
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = keyboardOptions,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = if (passwordVisible) { painterResource(R.drawable.ic_password_show) } else painterResource(R.drawable.ic_password_hide),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        }
    )
}
