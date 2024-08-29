package com.gabriel.chatbotgemini

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel.chatbotgemini.ui.theme.ChatBotGeminiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatBotGeminiTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatScreen(modifier = Modifier.padding(), viewModel = ChatViewModel())
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Full Preview", showSystemUi = true)
@Preview
@Composable
private fun ChatScreenPreview() {
    ChatBotGeminiTheme {
        ChatScreen(
            modifier = Modifier.padding(16.dp),
            viewModel = ChatViewModel()
        )
    }
}