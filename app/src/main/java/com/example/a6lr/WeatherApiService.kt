import com.example.a6lr.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// для отправки запроса api
interface WeatherApiService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<WeatherResponse>
}