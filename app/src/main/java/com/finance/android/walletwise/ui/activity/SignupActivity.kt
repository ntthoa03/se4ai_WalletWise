package com.finance.android.walletwise.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.finance.android.walletwise.ui.fragment.NormalTextField
import com.finance.android.walletwise.ui.fragment.NormalButton
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.PasswordField
import com.finance.android.walletwise.ui.viewmodel.AuthenticationViewModel

@Preview(showBackground = true)
@Composable
fun PreviewSignupScreen() {
    WalletWiseTheme {
        SignupScreen(
            authenticationViewModel = null,
        )
    }
}

@Composable
fun SignupScreen(
    authenticationViewModel: AuthenticationViewModel? = null,
    navigateToProfile: () -> Unit = {},
    onLoginClick: () -> Unit = {}, )
{
    val authenticationUiState = authenticationViewModel?.authenticationUiState
    val isError = authenticationUiState?.errorSignup != null
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight   = configuration.screenHeightDp

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp), )
    {
        //LOGO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start )
        {
            Image(
                painter = painterResource(R.drawable.application_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(bottom = 8.dp), )
        }

        /**
         * SIGN UP FORM
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge, )

            Spacer(modifier = Modifier.height((screenHeight*0.05).dp))

            //Username
            NormalTextField(
                value = authenticationUiState?.usernameSignup ?: "",
                onValueChange = { authenticationViewModel?.onUsernameSignupChange(it) },
                label = "Username",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Username",)
                },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
            )

            Spacer(modifier = Modifier.height((screenHeight*0.005).dp))

            //Password Field
            PasswordField(
                value = authenticationUiState?.passwordSignup ?: "",
                onValueChange = { authenticationViewModel?.onPasswordSignupChange(it) },
                label = "Password",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password",)
                },
                isError = isError,
            )

            Spacer(modifier = Modifier.height((screenHeight*0.005).dp))

            //Confirm Password Field
            PasswordField(
                value = authenticationUiState?.confirmPasswordSignup ?: "",
                onValueChange = { authenticationViewModel?.onConfirmPasswordSignupChange(it) },
                label = "Confirm Password",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password",)
                },
                isError = isError,
            )

            //Error Lines
            Box(modifier = Modifier)
            {
                if (isError)
                {
                    Text(
                        text = authenticationUiState?.errorSignup ?: "Unknown Error",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp), )
                }

                Spacer(modifier = Modifier.height((screenHeight * 0.2).dp))
            }
        }

        /**
         * BUTTONS
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Button
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
                //Loading
                if (authenticationUiState?.isLoading == true)
                {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        strokeWidth = 4.dp,
                    )
                }

                //Button
                NormalButton(
                    text = "Next",
                    onClick = {
                        authenticationViewModel?.createUser(context)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = authenticationUiState?.isLoading != true && (authenticationUiState?.usernameSignup != null && authenticationUiState?.passwordSignup != null && authenticationUiState?.confirmPasswordSignup != null)
                )
            }

            //Already have an account? -> Login text button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(0.dp), )

                TextButton(
                    onClick = onLoginClick,
                    modifier = Modifier.padding(0.dp), )
                {
                    Text(
                        text = "Log in",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(0.dp), )
                }
            }

            LaunchedEffect(key1 = authenticationViewModel?.hasUser)
            {
                if (authenticationViewModel?.hasUser == true)
                {
                    navigateToProfile()
                }
            }

        }
    }
}
