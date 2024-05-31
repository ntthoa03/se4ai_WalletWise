package com.finance.android.walletwise.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
//Import UI file
import com.finance.android.walletwise.ui.theme.*
import com.finance.android.walletwise.R

//Show Screen
//class WelcomeActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            WalletWiseTheme {
//                WelcomeScreen(
//                    onSignUpClick = { startActivity(Intent(this, SignupActivity::class.java)) },
//                    onLoginClick  = { startActivity(Intent(this, LoginActivity::class.java)) }
//                )
//            }
//        }
//    }
//}

//@Preview(showBackground = true)
@Composable
fun WelcomeScreen(
    onLoginClick:  () -> Unit,
    onSignupClick: () -> Unit )
{
    val configuration = LocalConfiguration.current
    val screenWidth   = configuration.screenWidthDp
    val screenHeight  = configuration.screenHeightDp

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.md_theme_background)))
    {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Application Logo
            Image(
                painter = painterResource(R.drawable.application_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size((screenWidth*0.4).dp)
                    .padding(bottom = 8.dp))
            //"Welcome on board" text
            Image(
                painter = painterResource(R.drawable.start_welcometext),
                contentDescription = "Welcome Text",
                modifier = Modifier
                    .size((screenWidth*0.6).dp))
        }
        //Button section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Button section
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround)
            {
                //Start Journey button
                Button(
                    onClick = { onSignupClick() },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.md_theme_primary)),
                    modifier = Modifier
                        .weight(0.7f)
                        .height(60.dp),
                    shape = RoundedCornerShape(50.dp))
                {
                    Text(
                        text = "Start your journey",
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.white))
                    //Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = colorResource(id = R.color.white), modifier = Modifier.size(16.dp).padding(start = 8.dp))
                }
                Spacer(modifier = Modifier.width(16.dp)) // Spacing between buttons
                //Login button
                OutlinedButton(
                    onClick = { onLoginClick() },
                    border = BorderStroke(1.dp, colorResource(id = R.color.md_theme_primary)),
                    modifier = Modifier
                        .weight(0.3f)
                        .height(60.dp),
                    shape = RoundedCornerShape(50.dp))
                {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.md_theme_primary))
                }
            }

            //User Agreement text
            Text(
                text = stringResource(id = R.string.user_agreement),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}