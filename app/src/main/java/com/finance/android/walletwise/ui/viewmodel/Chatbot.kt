package com.finance.android.walletwise.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.android.walletwise.ui.activity.Message
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>(listOf())
    val messages: LiveData<List<Message>> = _messages

    private val model: GenerativeModel = GenerativeModel(
        "gemini-1.5-flash",
        "AIzaSyCq40KK_pI3FM_-3wkCOw-86H6RERMbZ70",
        generationConfig = generationConfig {
            temperature = 1f
            topK = 64
            topP = 0.95f
            maxOutputTokens = 8192
            responseMimeType = "text/plain"
        },
        systemInstruction = content { text("You are a virtual assistant designed to provide financial information. Your job is to offer resources on budgeting, setting savings goals, and general financial planning. Ensure that your tone is friendly and encouraging. Start by asking if the user is seeking financial information, and suggest a few topics to guide the conversation, including budgeting tips, saving goals, or connecting with a financial advisor.") },
    )

    fun sendMessage(userMessage: String)
    {
        val newMessages = _messages.value.orEmpty() + Message(userMessage, true)
        _messages.value = newMessages

        viewModelScope.launch(Dispatchers.IO)
        {
            val chatHistory = listOf(
                content("user")
                {
                    text(userMessage)
                }
            )
            val chat = model.startChat(chatHistory)
            val response = chat.sendMessage(userMessage)
            val modelResponse = response.text?: ""

            withContext(Dispatchers.Main) {
                val updatedMessages = _messages.value.orEmpty() + Message(modelResponse, false)
                _messages.value = updatedMessages
            }
        }
    }
}