package com.finance.android.walletwise.ui.activity

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.*
import com.finance.android.walletwise.ui.viewmodel.PinViewModel
import com.finance.android.walletwise.ui.viewmodel.UserProfileViewModel

/**
 * ENTER PIN SCREEN
 */
@Preview(showBackground = true)
@Composable
fun PreviewEnterPinScreen()
{
    WalletWiseTheme {
        EnterPinScreen(
            onNavigateHome = {}
        )
    }
}

@Composable
fun EnterPinScreen(
    pinViewModel: PinViewModel? = null,
    userProfileViewModel: UserProfileViewModel? = null,
    onNavigateHome: () -> Unit,)
{
    val pinUiState = pinViewModel?.pinUiState
    var pin by remember { mutableStateOf("") }

    val userProfileUiState = userProfileViewModel?.userProfileUiState
    userProfileViewModel?.getUserProfile()
    val firstName = userProfileUiState?.fullName ?: ""

    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp

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
                contentDescription = "Application Logo",)
        }

        /**
         * GREETING
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(
                modifier = Modifier.height((screenHeight*0.08).dp), )

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = "Welcome back,",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )

                AnimatedGradientText(
                    text = firstName,
                    gradient = listOf(Color.Red, Color.Blue), //listOf(Color(0xFFF44336), Color(0xFFFF9800), Color(0xFFFFEB3B), Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFF9C27B0)),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                )
            }
        }

        /**
         * PIN
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Enter Pin field ----------------------------------------------------------------------
            Text(
                text = "Enter Your PIN",
                style = MaterialTheme.typography.titleMedium
                    .copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            //PinField -----------------------------------------------------------------------------
            PinField(
                value = pin,
                mask = true,
                onValueChange = { pin = it },
                onPinEntered = { pin ->
                    pinViewModel?.verifyPin(pin)},
                isError = false,
            )

            //Error message
            if (pinUiState?.error != null)
            {
                Text(
                    text = pinUiState.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height((screenHeight*0.2).dp))
        }

        LaunchedEffect(key1 = pinUiState?.isPinVerified)
        {
            if (pinUiState?.isPinVerified == true)
            {
                onNavigateHome()
            }
        }

    }
}

/**
 * ANIMATED TEXT
 */
@Preview(showBackground = true)
@Composable
fun PreviewAnimatedGradientText(){
    WalletWiseTheme {
        AnimatedGradientText(
            text = "Hello, World!",
            gradient = listOf(Color.Red, Color.Blue)
        )
    }
}

@Composable
fun AnimatedGradientText(
    text: String,
    gradient: List<Color>,
    style: TextStyle = MaterialTheme.typography.headlineMedium, )
{
    //Animation configuration
    var offset by remember { mutableStateOf(0f) }
    val transition = rememberInfiniteTransition()
    val animatedOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    //Update offset for animation
    LaunchedEffect(key1 = animatedOffset) {
        offset = animatedOffset
    }

    //Gradient brush
    val brush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                return LinearGradientShader(
                    colors = gradient,
                    from = Offset(size.width * offset, 0f),
                    to = Offset(size.width * offset + size.width, 0f),
                    tileMode = TileMode.Mirror
                )
            }
        }
    }

    //Text with gradient brush
    Text(
        text = text,
        style = style.copy(brush = brush),
    )
}