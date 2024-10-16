package com.example.login.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CodeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "ACTION_SEND_CODE") {
            val code = intent.getStringExtra("code") ?: return

            // Логика обработки кода, например, показать Toast с кодом
           // Toast.makeText(context, "Ваш код: $code", Toast.LENGTH_LONG).show()
        }
    }
}