package br.com.runes.matchsimulator.data.api

import br.com.runes.matchsimulator.domain.Match
import retrofit2.Response
import retrofit2.http.GET

interface MatchApi {
    @GET("matches.json")
    suspend fun getAllMatches(): Response<List<Match>>
}