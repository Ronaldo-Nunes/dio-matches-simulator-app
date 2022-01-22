package br.com.runes.matchsimulator.data.api

import android.util.Log
import br.com.runes.matchsimulator.domain.Match
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchService {
    companion object {
        const val BASE_URL = "https://ronaldo-nunes.github.io/dio-matches-simulator-api/"
        private const val OK_HTTP = "OkHttp"
    }

    private fun createClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor {
            Log.e(OK_HTTP, it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }


    private val api: MatchApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createClient())
        .build()
        .create(MatchApi::class.java)

    suspend fun getAllMatches(): Response<List<Match>> = api.getAllMatches()
}