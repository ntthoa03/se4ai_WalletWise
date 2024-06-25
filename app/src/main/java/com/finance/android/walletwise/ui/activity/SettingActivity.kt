package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.finance.android.walletwise.WalletWiseTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.finance.android.walletwise.ui.fragment.WalletWiseBottomBar
import com.finance.android.walletwise.ui.fragment.WalletWiseTopAppBar

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview()
{
    WalletWiseTheme {
        SettingScreen()
    }
}

@Composable
fun SettingScreen() {

    Scaffold()
    {innerPadding ->
        SettingScreenContent(
            theme = "Light",
            language = "English",
            currency = "VND",
            dateFormat = "dd/mm/yyyy",
            email = "nguyenvana@gmail.com",
            innerPadding = innerPadding
        )
    }
}


@Composable
fun SettingScreenContent( theme: String,
                          language: String,
                          currency: String,
                          dateFormat: String,
                          email: String,
//                          spendingAlert: Boolean,
//                          notificationTone: Boolean,
//                          enablePin: Boolean,
                          innerPadding: PaddingValues) {
//    var theme by remember { mutableStateOf("") }
//    var language by remember { mutableStateOf("English") }
//    var currency by remember { mutableStateOf("VND") }
//    var dateFormat by remember { mutableStateOf("dd/mm/yyyy") }
    var spendingAlert by remember { mutableStateOf(true) }
    var notificationTone by remember { mutableStateOf(true) }
    var enablePin by remember { mutableStateOf(true) }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        item {
            Section(title = "Display") {
                SettingItem(title = "Theme", value = theme, onClick = { /*TODO*/ })
                SettingItem(title = "Select language", value = language, onClick = { /*TODO*/ })
                SettingItem(title = "Currency", value = currency, onClick = { /*TODO*/ })
                SettingItem(title = "Select date format", value = dateFormat, onClick = { /*TODO*/ })
            }
        }
        item{ Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Section(title = "Notification") {
                ToggleSettingItem(
                    title = "Receive alert based on spending",
                    checked = spendingAlert,
                    onCheckedChange = { spendingAlert = it }
                )
                ToggleSettingItem(
                    title = "Notification tone",
                    checked = notificationTone,
                    onCheckedChange = { notificationTone = it }
                )
            }
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item {
            Section(title = "System") {
                SettingItem(title = "Setting profile", onClick = { /*TODO*/ })
                ToggleSettingItem(
                    title = "Enable PIN",
                    checked = enablePin,
                    onCheckedChange = { enablePin = it }
                )
                SettingItem(title = "Change PIN", onClick = { /*TODO*/ })
                SettingItem(title = "Change password", onClick = { /*TODO*/ })
                SettingItem(title = "Report bugs to developer", onClick = { /*TODO*/ })
                SettingItem(title = "Sign out", value = email, onClick = { /*TODO*/ })
            }
        }
//
    }
}


@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = Color.Blue,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp)
        )
        content()
    }
}

@Composable
fun SettingItem(title: String, value: String = "", onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp, horizontal = 15.dp)
        .clickable(onClick = onClick)) {
        Text(text = title, fontSize = 16.sp)
        if (value.isNotEmpty()) {
            Text(text = value, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ToggleSettingItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 15.dp)
            .height(23.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 16.sp)
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = androidx.compose.material3.CheckboxDefaults.colors(
                checkedColor = Color.Blue, // Màu khi checkbox được chọn
                uncheckedColor = Color.Gray, // Màu khi checkbox không được chọn
                checkmarkColor = Color.White // Màu của dấu tích
            )
        )
    }
}
