package br.com.runes.matchsimulator.domain

sealed class State {
    object Loading : State()
    data class Success(val list: List<Match>?) : State()
    data class Error(val error: Throwable) : State()
}