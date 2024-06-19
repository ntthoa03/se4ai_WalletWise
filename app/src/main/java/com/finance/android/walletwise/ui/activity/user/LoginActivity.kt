package com.finance.android.walletwise.ui.activity.user

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.finance.android.walletwise.ui.fragment.NormalTextField
import com.finance.android.walletwise.ui.fragment.NormalButton
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.PasswordField
import com.finance.android.walletwise.ui.viewmodel.user.AuthenticationViewModel

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    WalletWiseTheme {
        LoginScreen(
            authenticationViewModel = null,
        )
    }
}

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    authenticationViewModel: AuthenticationViewModel? = null, )
{
    val authenticationUiState = authenticationViewModel?.authenticationUiState
    val isError = authenticationUiState?.errorLogin != null
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight   = configuration.screenHeightDp

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.md_theme_background)))
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
                contentDescription = "Application Logo",
                modifier = Modifier
                    .padding(bottom = 8.dp))
        }

        /**
         * LOGIN FORM
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Login text ---------------------------------------------------------------------------
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp))

            //Spacer
            Spacer(modifier = Modifier.height((screenHeight*0.05).dp))

            //Username -----------------------------------------------------------------------------
            NormalTextField(
                value = authenticationUiState?.username ?: "",
                onValueChange = { authenticationViewModel?.onUsernameChange(it) },
                label = "Username",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Username",)
                },
                modifier = Modifier.fillMaxWidth(),
                isError = isError, )

            Spacer(modifier = Modifier.height((screenHeight*0.005).dp))

            //Password -----------------------------------------------------------------------------
            PasswordField(
                value = authenticationUiState?.password ?: "",
                onValueChange = { authenticationViewModel?.onPasswordChange(it) },
                label = "Password",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password",)
                },
                isError = isError, )

            //Error lines --------------------------------------------------------------------------
            Box(modifier = Modifier)
            {
                if (isError)
                {
                    Text(
                        text = authenticationUiState?.errorLogin ?: "Unknown Error",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp), )
                }

                Spacer(modifier = Modifier.height((screenHeight * 0.2).dp))
            }
        }

        /**
         * BUTTON
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Button -------------------------------------------------------------------------------
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, )
            {
                if (authenticationUiState?.isLoading == true)
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
                        authenticationViewModel?.loginUser(context)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = authenticationUiState?.isLoading != true && authenticationUiState?.username.toString().isNotEmpty() && authenticationUiState?.password.toString().isNotEmpty()
                )
            }

            //Already have an account? -> Login text button ----------------------------------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(0.dp), )
                TextButton(
                    onClick = onSignUpClick,
                    modifier = Modifier.padding(0.dp), )
                {
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(0.dp), )
                }
            }
        }

        LaunchedEffect(key1 = authenticationViewModel?.hasUser)
        {
            if (authenticationViewModel?.hasUser == true)
            {
                navigateToHome()
            }
        }
    }
}