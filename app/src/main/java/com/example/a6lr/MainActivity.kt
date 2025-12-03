package com.example.a6lr

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSIONS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Проверяем разрешения
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), REQUEST_CODE_PERMISSIONS)
        }

        // Запускаем API-запрос
        fetchWeatherData()

        // Загружаем JSON в отдельном потоке и добавляем в БД
        loadJsonToDatabase()

        // Устанавливаем навигацию меню
        setupNavigation()
    }

    // Работа с API - вызов данных о температуре в городе N
    private fun fetchWeatherData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/") // Убраны лишние пробелы
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherApiService::class.java)

        // запрос
        val call = service.getCurrentWeather("Saint Petersburg", "0afc736cf6ba687d3e6d20ed62574b95", "metric")

        // отправляем запрос
        call.enqueue(object : Callback<WeatherResponse> {
            // сервер ответил, парсим ответ
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    findViewById<TextView>(R.id.textView).text = "${weather?.name}: ${weather?.main?.temp}°C"
                } else {
                    Toast.makeText(this@MainActivity, "Ошибка: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            // сервер не ответил
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(this@MainActivity, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // Загрузка JSON в отдельном потоке и добавление в БД
    private fun loadJsonToDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val jsonList = JsonHelper.fetchAndParseJson()
            jsonList?.forEach { jsonParrot ->
                val database = ParrotDatabase.getDatabase(applicationContext)
                val existingParrot = database.parrotDao().getParrotByName(jsonParrot.name)
                if (existingParrot == null) {
                    database.parrotDao().insertParrot(
                        Parrot(name = jsonParrot.name, species = jsonParrot.species, age = jsonParrot.age)
                    )
                }
            }
        }
    }

    // Проверка на наличие интернета
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected == true
        }
    }

    // Навигация нижнего меню - замена фрагментов
    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_parrots -> {
                    replaceFragment(ParrotsListFragment())
                    true
                }
                R.id.nav_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        // Загрузка начального фрагмента
        replaceFragment(HomeFragment())
    }

    // Замена фрагмента через их менеджер
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}