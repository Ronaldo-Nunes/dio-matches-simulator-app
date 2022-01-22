package br.com.runes.matchsimulator.implementation

import android.os.RemoteException
import br.com.runes.matchsimulator.data.MatchRepository
import br.com.runes.matchsimulator.data.api.MatchService
import br.com.runes.matchsimulator.domain.Match
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MatchRepositoryImplementation(private val matchService: MatchService) : MatchRepository {

    override fun getAllMatches(): Flow<List<Match>?>  = flow {
        try {
            val response = matchService.getAllMatches()
            if (response.isSuccessful) {
                emit(response.body())
            } else {
                val errorBody = response.errorBody()
                val errorMessage: String = errorBody?.toString() ?: "Ocorreu um erro ao buscar os dados."
                throw RemoteException(errorMessage)
            }
        } catch (ex: HttpException) {
            throw RemoteException(ex.message())
        }
    }
}