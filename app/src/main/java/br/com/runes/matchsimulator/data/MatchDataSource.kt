package br.com.runes.matchsimulator.data

import br.com.runes.matchsimulator.domain.Match

interface MatchDataSource {
    fun getAllMatches(): List<Match>
}