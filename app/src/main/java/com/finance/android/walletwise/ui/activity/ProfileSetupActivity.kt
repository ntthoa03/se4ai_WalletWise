package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.DropDownMenu
import com.finance.android.walletwise.ui.fragment.NormalButton
import com.finance.android.walletwise.ui.fragment.NormalTextField

@Preview(showBackground = true)
@Composable
fun PreviewProfileSetupScreen() {
    WalletWiseTheme {
        ProfileSetupScreen(
            onNextProfile = {}
        )
    }
}

@Composable
fun ProfileSetupScreen(
    onNextProfile: () -> Unit, )
{
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("") }
    val genders = listOf("Male", "Female", "Other")
    val currencies = listOf("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY")

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.md_theme_background)))
    {
        //Logo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start)
        {
            Image(
                painter = painterResource(R.drawable.application_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    //.size((screenWidth*0.4).dp)
                    .padding(bottom = 8.dp))
        }

        //Setup Profile text
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Set up your profile",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        //Setup Profile Form
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Your name
            NormalTextField(value = name, onValueChange = { name = it }, label = "Your name")

            //Gender and Age section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp))
            {
                //Gender
                Box(
                    modifier = Modifier.weight(1f))
                {
                    DropDownMenu(
                        options = genders,
                        selectedOption = gender,
                        onOptionSelected = { gender = it },
                        label = "Gender")
                }

                //Age
                Box(modifier = Modifier.weight(1f))
                {
                    NormalTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = "Age",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                }
            }

            //Phone number
            NormalTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Phone number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))

            //Email address
            NormalTextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = "Email address",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))

            //Currency
            DropDownMenu(
                options = currencies,
                selectedOption = "",
                onOptionSelected = {currency = it},
                label = "Currency")
        }

        //Button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            NormalButton(
                text = "Next",
                onClick = onNextProfile,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = name.isNotBlank() && gender.isNotBlank() && age.isNotBlank() && phoneNumber.isNotBlank() && emailAddress.isNotBlank())
        }
    }
}
