package br.com.runes.matchsimulator.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchRestAPI {
    companion object {
        const val BASE_URL = "https://ronaldo-nunes.github.io/dio-matches-simulator-api/"
    }

    private val client: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun retrofitApi(): MatchesAPI = client.create(MatchesAPI::class.java)
}