package com.example.core.data.source

import com.google.firebase.vertexai.FirebaseVertexAI
import com.google.firebase.vertexai.type.Part
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PromptDataSource @Inject constructor(private val firebaseVertexAI: FirebaseVertexAI) {
    private val textPromptModel = firebaseVertexAI.generativeModel("gemini-1.5-pro")
    private val docPromptModel = firebaseVertexAI.generativeModel("gemini-1.0-pro-vision")
    private val transactionPromptString: String = "You are a text message entity extraction specialist for financial transaction related text / SMS. Given one or more text message containing transactions done, your task is to extract the text value of the following entities:\n" +
            "{\n" +
            "\t\"amount\": \"\",\n" +
            "\t\"currency\": \"\",\n" +
            "\t\"account_ending_digits\": \"\",\n" +
            "\t\"bank_name\": \"\"\n" +
            "\t\"transaction_date\": \"\",\n" +
            "\t\"transaction_type\": \"\",\n" +
            "\t\"account_type\": \"\",\n" +
            "\t\"remarks\": \"\"\n" +
            "}\n" +
            "\n" +
            "- The JSON schema must be followed during the extraction.\n" +
            "- The values must only include text found in the document\n" +
            "-There may be more than one messages in the given text, so separately process them and return a list of json objects.\n" +
            "- Do not normalize any entity value.\n" +
            "-If message has extra information than required, ignore it.\n" +
            "- If an entity is not found in the document, set the entity value to null.\n" +
            "-The field \"transaction_type\" can only be either \"Debit\" or \"Credit\" if the amount has been debited or credited respectively.\n" +
            "-The field \"remarks\" will contain information about what the transaction is done for.\n" +
            "-The field \"account_type\" can only be either of three values: \"Credit Card\" or \"Debit Card\" or \"UPI\". If transaction is not done via credit card or upi, then automatically mark it as debit card.\n" +
            "\n" +
            "The transaction messages are given below:"

    val documentExtractionPrompt = "You are a credit card statement document entity extraction specialist. Given a document of a credit card statement of a month, your task is to extract the text value of the following entities:\n" +
            "{\n" +
            "\"bank_name\": ,\n" +
            "\"payment_summary\": {\n" +
            "\"payment_due_date\": ,\n" +
            "\"minimum_payment_due\": ,\n" +
            "\"statement_period\": ,\n" +
            "\"total_payment_due\": \n" +
            "},\n" +
            "\"transactions\": [\n" +
            "{\n" +
            "\"date\": ,\n" +
            "\"transaction_details\": ,\n" +
            "\"amount\": ,\n" +
            "}\n" +
            "],\n" +
            "\"account_summary\": {\n" +
            "\"opening_balance\": ,\n" +
            "\"purchases_and_other_charges\": ,\n" +
            "\"payments_and_other_credits\": ,\n" +
            "\"net_outstanding_balance\": ,\n" +
            "},\n" +
            "\"reward_points_summary\": {\n" +
            "\"opening_balance\": ,\n" +
            "\"earned\": ,\n" +
            "\"redeemed\": ,\n" +
            "\"closing_balance\": \n" +
            "}\n" +
            "}\n" +
            "\n" +
            "- The JSON schema must be followed during the extraction.\n" +
            "- The values must only include text strings found in the document.\n" +
            "- Generate null for missing entities.\n"

    suspend fun getResponse(prompt: String): String? {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val response = textPromptModel.generateContent(transactionPromptString + prompt)
                continuation.resume(response.text)
            }
        }
    }

//    suspend fun getDocumentExtractionResponse(input: String): String? {
//        return suspendCoroutine { continuation ->
//            CoroutineScope(Dispatchers.IO).launch {
//                val contentData = firebaseVertexAI.
//                val response = docPromptModel.generateContent()
//            }
//        }
//    }
}