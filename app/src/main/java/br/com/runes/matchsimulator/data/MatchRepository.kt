package br.com.runes.matchsimulator.data

import br.com.runes.matchsimulator.domain.Match
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    fun getAllMatches(): Flow<List<Match>?>
}