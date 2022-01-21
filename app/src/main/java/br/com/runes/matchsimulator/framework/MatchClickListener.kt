package br.com.runes.matchsimulator.framework

import br.com.runes.matchsimulator.domain.Match

interface MatchClickListener {
    fun onMatchClick(match: Match)
}
