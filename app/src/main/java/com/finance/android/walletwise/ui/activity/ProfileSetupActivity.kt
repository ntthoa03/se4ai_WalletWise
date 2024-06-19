package com.finance.android.walletwise.ui.activity

import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.DropDownMenu
import com.finance.android.walletwise.ui.fragment.NormalButton
import com.finance.android.walletwise.ui.fragment.NormalTextField
import com.finance.android.walletwise.ui.viewmodel.user.UserProfileUiState
import com.finance.android.walletwise.ui.viewmodel.user.UserProfileViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewProfileSetupScreen() {
    WalletWiseTheme {
        ProfileSetupScreen()
    }
}

@Composable
fun ProfileSetupScreen(
    userProfileViewModel: UserProfileViewModel? = null,
    navigateToHome: () -> Unit = {}, )
{
    val userProfileUiState = userProfileViewModel?.userProfileUiState ?: UserProfileUiState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isFormNotBlank = userProfileUiState.fullName.isNotBlank() &&
                            userProfileUiState.gender.isNotBlank() &&
                            userProfileUiState.age != 0 &&
                            userProfileUiState.phoneNumber.isNotBlank() &&
                            userProfileUiState.currency.isNotBlank()

    val configuration = LocalConfiguration.current
    val screenHeight   = configuration.screenHeightDp

    val genders = listOf("Male", "Female", "Other")
    val currencies = listOf("VND", "USD", "EUR")

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background), )
    {
        //LOGO
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
                    .padding(bottom = 8.dp))
        }

        /**
         * User Profile Form
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Set up your profile text
            Text(
                text = "Set up your profile",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp), )

            Spacer(modifier = Modifier.height((screenHeight * 0.05).dp))

            //Your name ----------------------------------------------------------------------------
            NormalTextField(
                value = userProfileUiState.fullName,
                onValueChange = { userProfileViewModel?.onFullNameChanged(it) },
                label = "Your name")

            //Gender and Age section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp))
            {
                //Gender ---------------------------------------------------------------------------
                Box(
                    modifier = Modifier.weight(1f))
                {
                    DropDownMenu(
                        options = genders,
                        value = userProfileUiState.gender,
                        onOptionSelected = { userProfileViewModel?.onGenderChanged(it) },
                        label = "Gender")
                }

                //Age ------------------------------------------------------------------------------
                Box(modifier = Modifier.weight(1f))
                {
                    NormalTextField(
                        value = if (userProfileUiState.age != 0) (userProfileUiState.age).toString() else "",
                        onValueChange = { userProfileViewModel?.onAgeChanged(it.toIntOrNull() ?: 0) },
                        label = "Age",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
                }
            }

            //Phone number -------------------------------------------------------------------------
            NormalTextField(
                value = userProfileUiState.phoneNumber,
                onValueChange = { userProfileViewModel?.onPhoneNumberChanged(it) },
                label = "Phone number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))

            //Currency -----------------------------------------------------------------------------
            DropDownMenu(
                options = currencies,
                value = userProfileUiState.currency,
                onOptionSelected = { userProfileViewModel?.onCurrencyChanged(it) },
                label = "Currency")

            Spacer(modifier = Modifier.height((screenHeight * 0.1).dp))
        }

        //Button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally, )
        {
            if (userProfileUiState.isLoading)
            {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    strokeWidth = 4.dp,
                )
            }

            NormalButton(
                text = "Next",
                onClick = {
                    userProfileViewModel?.addUserProfile()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isFormNotBlank, )
        }

        LaunchedEffect(key1 = userProfileUiState.addUserProfileStatus)
        {
            if (userProfileUiState.addUserProfileStatus)
            {
                scope.launch {
                    Toast.makeText(context, "Profile set up successfully", Toast.LENGTH_SHORT).show()
                    userProfileViewModel?.resetUserProfileStatus()
                    navigateToHome()
                }
            }
        }

    }
}
