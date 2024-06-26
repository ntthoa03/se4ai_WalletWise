package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.viewmodel.ChatViewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finance.android.walletwise.ui.fragment.WalletWiseTopAppBar
import org.commonmark.node.*
import org.commonmark.parser.Parser

fun parseMarkdownToAnnotatedString(markdown: String): AnnotatedString {
    val parser = Parser.builder().build()
    val document = parser.parse(markdown)
    val builder = AnnotatedString.Builder()

    document.accept(object : AbstractVisitor() {
        override fun visit(text: Text) {
            builder.append(text.literal)
        }

        override fun visit(strongEmphasis: StrongEmphasis) {
            val start = builder.length
            visitChildren(strongEmphasis)
            val end = builder.length
            builder.addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
        }

        override fun visit(emphasis: Emphasis) {
            val start = builder.length
            visitChildren(emphasis)
            val end = builder.length
            builder.addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
        }

        override fun visit(code: Code) {
            val start = builder.length
            builder.append(code.literal)
            val end = builder.length
            builder.addStyle(SpanStyle(fontFamily = FontFamily.Monospace), start, end)
        }
    })

    return builder.toAnnotatedString()
}

@Preview
@Composable
fun ChatScreen(
  onBackClick: () -> Unit = {}, )
{
    val viewModel: ChatViewModel = viewModel()
    val messages by viewModel.messages.observeAsState(listOf())
    var input by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            WalletWiseTopAppBar(
                title = "Financial Chatbot",
                useIconForTitle = false,
                showNavigationButton = true,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick,
                showActionButton = false,
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)  // Sử dụng paddingValues để đảm bảo nội dung không bị che khuất
                    .imePadding()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    reverseLayout = true,
                ) {
                    items(messages.reversed()) { message ->
                        MessageItem(message)
                    }
                }

                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("Message Chatbot") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 0.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = {
                            if (input.text.isNotEmpty()) {
                                viewModel.sendMessage(input.text)
                                input = TextFieldValue("")
                            }
                        })
                    )
                    IconButton(onClick = {
                        if (input.text.isNotEmpty()) {
                            viewModel.sendMessage(input.text)
                            input = TextFieldValue("")
                        }
                    }) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }

                // Remind
                Text(
                    text = stringResource(id = R.string.chatbot_warning),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
            }
        }
    )
}

@Composable
fun MessageItem(message: Message) {
    val backgroundColor = if (message.isUser) Color(0xFFE0F7FA) else Color(0xFFBBDEFB)
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        contentAlignment = alignment,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(30.dp))
                .padding(10.dp)
        ) {
            if (!message.isUser) {
                Image(
                    painter = painterResource(id = R.drawable.ic_chat_finance),
                    contentDescription = "Assistant",
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                )
            }
//            Text(message.text.trim(), fontSize = 14.sp)
            Text(
                text = parseMarkdownToAnnotatedString(message.text),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotTopAppBar(
    title: String,
    useIconForTitle: Boolean = true,
    showNavigationButton: Boolean = true,
    navigationButton: @Composable (() -> Unit)? = null,
    onNavigationClick: () -> Unit = {},
    showActionButton: Boolean = true,
    actionButton: @Composable (() -> Unit)? = null,
    onActionClick: () -> Unit = {}, ) {
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp
    TopAppBar(
        modifier = Modifier
            .height((screenHeight * 0.06).dp)
            .fillMaxWidth(),
        title = {
            if (useIconForTitle) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.application_logo),
                        contentDescription = "WalletWise application text logo",
                    )
                }
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        },
        navigationIcon = {
            if (showNavigationButton) {
                IconButton(onClick = onNavigationClick)
                {
                    navigationButton?.invoke() ?: Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }
        },
        actions = {
            if (showActionButton) {
                IconButton(onClick = onActionClick)
                {
                    actionButton?.invoke() ?: Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications"
                    )
                }
            }
        }
    )
}

data class Message(val text: String, val isUser: Boolean)