package br.com.runes.matchsimulator.framework

import android.util.Log
import androidx.lifecycle.*
import br.com.runes.matchsimulator.data.MatchRepository
import br.com.runes.matchsimulator.domain.Match
import br.com.runes.matchsimulator.domain.State
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(private val matchRepository: MatchRepository) : ViewModel() {
    private val _matches = MutableLiveData<State>()
    private var _simulatedList: List<Match>? = emptyList()
    val matches: LiveData<State> = _matches

    init {
        getMatchList()
    }

    private fun getMatchList() {
        viewModelScope.launch {
            matchRepository.getAllMatches()
                .onStart {
                    _matches.postValue(State.Loading)
                }
                .catch {
                    _matches.postValue(State.Error(it))
                }
                .collect { list ->
                    _simulatedList = list
                    _matches.postValue(State.Success(list))
                }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class MainViewModelFactory(private val matchRepository: MatchRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(matchRepository) as T
            }
            throw IllegalArgumentException("ViewModel class not found!!")
        }
    }
}