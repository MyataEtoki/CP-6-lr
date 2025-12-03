package com.example.a6lr

import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object JsonHelper {
    private const val JSON_URL = "https://my-json-server.typicode.com/MyataEtoki/Parrot-JSON/db" // Подставь реальный URL

    fun fetchAndParseJson(): List<ParrotJsonResponse>? {
        return try {
            val url = URL(JSON_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val json = reader.readText()
            reader.close()

            val gson = Gson()
            val apiResponse = gson.fromJson(json, ParrotApiResponse::class.java)
            apiResponse.parrots
        } catch (e: Exception) {
            Log.e("JsonHelper", "Ошибка при загрузке JSON", e)
            null
        }
    }
}