package br.com.runes.matchsimulator

import android.app.Application
import br.com.runes.matchsimulator.data.api.MatchService
import br.com.runes.matchsimulator.implementation.MatchRepositoryImplementation

class SimulatorApp : Application() {
    val matchRepository by lazy {
        val service = MatchService()
        MatchRepositoryImplementation(service)
    }
}