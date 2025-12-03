package com.example.a6lr

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

object JsonHelper {
    private const val JSON_URL = "https://example.com/parrots.json" // Подставь реальный URL

    fun fetchAndParseJson(): List<ParrotJsonResponse>? {
        return try {
            val url = URL(JSON_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val json = reader.readText()
            reader.close()

            val gson = Gson()
            val listType: Type = object : TypeToken<List<ParrotJsonResponse>>() {}.type
            gson.fromJson(json, listType)
        } catch (e: Exception) {
            Log.e("JsonHelper", "Ошибка при загрузке JSON", e)
            null
        }
    }
}