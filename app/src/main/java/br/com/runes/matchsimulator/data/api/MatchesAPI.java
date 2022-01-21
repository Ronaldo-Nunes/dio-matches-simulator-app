package br.com.runes.matchsimulator.data.api;

import java.util.List;

import br.com.runes.matchsimulator.domain.Match;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesAPI {

    @GET("matches.json")
    Call<List<Match>> getMatches();
}
