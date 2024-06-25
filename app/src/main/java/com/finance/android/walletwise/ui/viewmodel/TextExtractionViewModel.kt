package com.finance.android.walletwise.ui.viewmodel

import android.util.Log
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
import java.time.LocalTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class TextExtractionViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>(listOf())
    val messages: LiveData<List<Message>> = _messages
    var amountExtracted: String = ""
    var categoryExtracted: String = ""
    var dateExtracted: LocalDate = LocalDate.now()
    var noteExtracted: String = ""

    private val model: GenerativeModel = GenerativeModel(
        "gemini-1.5-flash",
        "AIzaSyC-4rIhUxhmsOkEOsSP-rz15T0wvMGIKtc",
        generationConfig = generationConfig {
            temperature = 1f
            topK = 64
            topP = 0.95f
            maxOutputTokens = 8192
            responseMimeType = "text/plain"
        },
        // safetySettings = Adjust safety settings
        // See https://ai.google.dev/gemini-api/docs/safety-settings
        systemInstruction = content {
            val currentDate = LocalDate.now()
            val currentDateFormatted = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            text("Today is ${currentDateFormatted}. " +
                    "From the following message, extract the amount, category, date, and note from {user_message}\n" +
                    "Categories include Food & Drink, Education, Transportation, Sport & Entertainment, Shopping, House & Utilities.\n" +
                    "Output format:{extracted_amount},{extracted_category},{extracted_date},{extracted_note}\n" +
                    "extracted_amount: The numerical value indicating the amount of money spent\n" +
                    "extracted_date: The date of the transaction formatted as yyyy-MM-dd. If {user_message} contains a date (e.g., today, yesterday, Jun 14th), ensure it is extracted as well.\n" +
                    "extracted_category: Ensure that the extracted category matches exactly one of the predefined categories listed above.") },
    )

    private fun extractVariables(response: String)  {

        val values = response.split(",")

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        try {
            if (values.size >= 3) {
                amountExtracted = values[0]
                categoryExtracted = values[1]
                dateExtracted = LocalDate.parse(values[2], dateFormatter)
                noteExtracted = values[3]

            } else {
                Log.e("TextExtractionViewModel", "Not enough elements in 'values' array: ${values.size}")
            }
        } catch (e: IndexOutOfBoundsException) {
            Log.e("TextExtractionViewModel", "IndexOutOfBoundsException: ${e.message}")
            // Xử lý khi index vượt quá giới hạn của mảng 'values'
        } catch (e: DateTimeParseException) {
            Log.e("TextExtractionViewModel", "DateTimeParseException: ${e.message}")
            // Xử lý khi không thể parse ngày tháng từ 'values[2]'
        }

    }
    fun extractText(userMessage: String){
        val newMessages = _messages.value.orEmpty() + Message(userMessage, true)
        _messages.value = newMessages
        Log.d("Test","Test extract text")
        viewModelScope.launch(Dispatchers.IO) {
            val chatHistory = listOf(
                content("user") {
                    text(userMessage)
                }
            )
            val chat = model.startChat(chatHistory)
            val response = chat.sendMessage(userMessage)
            val modelResponse = response.text ?: ""
            withContext(Dispatchers.Main) {
                extractVariables(modelResponse)
//                if (amountExtracted.isNotEmpty() && categoryExtracted.isNotEmpty() && dateExtracted != null && noteExtracted.isNotEmpty()) {
//                    Log.d("TransactionInfo", "Amount extracted: $amountExtracted")
//                    Log.d("TransactionInfo", "Category extracted: $categoryExtracted")
//                    Log.d("TransactionInfo", "Date extracted: $dateExtracted")
//                    Log.d("TransactionInfo", "Note extracted: $noteExtracted")
//                } else {
//                    Log.e("TransactionInfo", "Failed to extract all required values")
//                }
            }
        }
    }
}