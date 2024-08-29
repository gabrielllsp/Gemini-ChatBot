package com.gabriel.chatbotgemini

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabriel.chatbotgemini.model.MessageModel
import com.gabriel.chatbotgemini.ui.theme.Blue_Light
import com.gabriel.chatbotgemini.ui.theme.ChatBotGeminiTheme
import com.gabriel.chatbotgemini.ui.theme.Custom_Black
import com.gabriel.chatbotgemini.ui.theme.Dark_Blue
import com.gabriel.chatbotgemini.ui.theme.Gray_01
import com.gabriel.chatbotgemini.ui.theme.Gray_03
import com.gabriel.chatbotgemini.ui.theme.Light_Blue
import com.gabriel.chatbotgemini.ui.theme.Purple
import com.gabriel.chatbotgemini.ui.theme.Salmon_Light

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier = Modifier, viewModel: ChatViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gemini Chatbot",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(8.dp),
                        color = Light_Blue
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Dark_Blue
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            Column(modifier = modifier) {

                MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
                MessageInput(
                    onMessageSent = {
                        viewModel.sendMessage(it)
                    }
                )
            }
        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GradientText(
                text = "Olá, Desenvolvedor"
            )
            Text(
                text = "Posso ajudar?",
                fontSize = 32.sp,
                color = Gray_03,
                fontWeight = FontWeight.W900,
                fontFamily = FontFamily.SansSerif
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun GradientText(text: String, modifier: Modifier = Modifier) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Blue_Light, Purple, Salmon_Light),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Text(
        text = text,
        style = TextStyle(
            fontSize = 32.sp,
            brush = gradientBrush,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W900,
            fontFamily = FontFamily.SansSerif
        ),
        modifier = modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
    )
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd

                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) Blue_Light else Salmon_Light)
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(onMessageSent: (String) -> Unit) {
    var message by remember {
        mutableStateOf("")
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        query = message,
        onQueryChange = { message = it },
        onSearch = {},
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(
                text = "Digite uma pergunta ou comando",
                color = Gray_01,
                fontWeight = FontWeight.W300,
                fontFamily = FontFamily.SansSerif
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_send_plane_fill),
                contentDescription = "icon send",
                tint = Custom_Black,
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 4.dp)
                    .clickable {
                        if (message.isNotEmpty()) {
                            onMessageSent(message)
                            message = ""
                        }
                    }
            )
        }
    ) { }
}


@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Full Preview", showSystemUi = true)
@Preview
@Composable
private fun ChatScreenPreview() {
    ChatBotGeminiTheme {
        ChatScreen(
            modifier = Modifier,
            viewModel = ChatViewModel()
        )
    }
}