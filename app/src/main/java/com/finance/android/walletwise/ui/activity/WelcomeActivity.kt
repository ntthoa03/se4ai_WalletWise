package com.finance.android.walletwise.ui.activity

//Import UI file
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WalletWiseTheme {
        WelcomeScreen(
            onLoginClick  = { /* Do nothing in preview */ },
            onSignupClick = { /* Do nothing in preview */ }, )
    }
}

@Composable
fun WelcomeScreen(
    onLoginClick:  () -> Unit,
    onSignupClick: () -> Unit )
{
    val configuration = LocalConfiguration.current
    val screenWidth   = configuration.screenWidthDp
    //val screenHeight  = configuration.screenHeightDp

    WalletWiseTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background))
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
                        .size((screenWidth * 0.4).dp)
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
                    //Start Journey button (special button)
                    Button(
                        onClick = { onSignupClick() },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(0.7f)
                            .height(60.dp),
                        shape = RoundedCornerShape(10.dp))
                    {
                        Text(
                            text = "Start your journey",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.onPrimary)
                        //Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = colorResource(id = R.color.white), modifier = Modifier.size(16.dp).padding(start = 8.dp))
                    }

                    //Space between 2 buttons
                    Spacer(modifier = Modifier.width(10.dp)) // Spacing between buttons

                    //Login button (special button)
                    OutlinedButton(
                        onClick = { onLoginClick() },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(0.3f)
                            .height(60.dp),
                        shape = RoundedCornerShape(10.dp))
                    {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.primary,)
                    }
                }

                //User Agreement text
                Text(
                    text = stringResource(id = R.string.user_agreement),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}