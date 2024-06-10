package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.*
import com.finance.android.walletwise.ui.viewmodel.PinViewModel

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
    onNavigateHome: () -> Unit,)
{
    val pinUiState = pinViewModel?.pinUiState
    var pin by remember { mutableStateOf("") }

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

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center, )
            {
                Text(
                    text = "Hi, ",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.tertiary,
                )

                Text(
                    text = "Buddy",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.tertiary,
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
            //Enter Pin field
            Text(
                text = "Enter PIN",
                style = MaterialTheme.typography.labelLarge,
            )
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

@Preview(showBackground = true)
@Composable
fun PreviewNumberBoard()
{
    WalletWiseTheme {
        NumberBoardRow(
            num = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", " ", "0", " "),
            onNumberClick = {},
        )
    }
}

@Composable
fun NumberBoardRow(
    num: List<String>,
    onNumberClick: (num: String) -> Unit)
{
    val list = (1..9).map { it.toString() }.toMutableList()
    list.addAll(mutableListOf(" ", "0", " "))

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            itemsIndexed(items = list) { index, item ->
                NumberButton(
                    modifier = Modifier,
                    number = item,
                    onClick = { onNumberClick(it) })
            }
        }
    )
}

@Composable
private fun NumberButton(
    modifier: Modifier = Modifier,
    number: String = "1",
    onClick: (number: String) -> Unit = {},
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            onClick(number)
        },
        modifier = modifier
            .padding(8.dp),)
    {
        Text(
            text = number,
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}