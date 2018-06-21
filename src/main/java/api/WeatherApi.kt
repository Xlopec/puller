package api

import com.google.gson.Gson
import io.reactivex.Single
import model.Weather
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


class WeatherProvider(private val apiKey: String) {

    private interface WeatherApi {
        // see doc ref https://openweathermap.org/current
        @GET("/data/2.5/weather")
        fun fetchWeather(@Query("lat") lat: Double,
                         @Query("lon") lon: Double,
                         @Query("APPID") apiKey: String): Single<Weather>

    }

    init {
        require(apiKey.isNotBlank())
    }

    private val api: WeatherApi by lazy {
        val client = OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .build()

        Retrofit.Builder()
                .baseUrl(HttpUrl.parse("http://api.openweathermap.org/data/2.5/")!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(client)
                .build()
                .create(WeatherApi::class.java)
    }

    fun fetchWeather(lat: Double, lon: Double) = api.fetchWeather(lat, lon, apiKey)

}