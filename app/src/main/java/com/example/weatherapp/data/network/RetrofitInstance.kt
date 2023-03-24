package com.example.skiescue.data.network
import com.example.weatherapp.data.network.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {

    private val baseUrl = "https://api.openweathermap.org/data/2.5/"

    private val retrofit:Retrofit by lazy {
        //Interceptor logger
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttp = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

        // Retrofit Builder
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
    }

    fun apiCall():ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}