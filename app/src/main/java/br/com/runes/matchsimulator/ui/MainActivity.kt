package br.com.runes.matchsimulator.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.runes.matchsimulator.R
import br.com.runes.matchsimulator.SimulatorApp
import br.com.runes.matchsimulator.databinding.ActivityMainBinding
import br.com.runes.matchsimulator.domain.Match
import br.com.runes.matchsimulator.domain.State
import br.com.runes.matchsimulator.framework.MainViewModel
import br.com.runes.matchsimulator.framework.MatchClickListener
import br.com.runes.matchsimulator.framework.MatchesAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MatchClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        val repository = (application as SimulatorApp).matchRepository
        MainViewModel.MainViewModelFactory(repository)
    }
    private val matchesAdapter: MatchesAdapter by lazy {
        MatchesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMatchesList()
        setupMatchesSwipe()
        setupFloatingActionButton()
    }

    private fun setupMatchesList() {
        binding.rvMatches.apply {
            setHasFixedSize(true)
            adapter = matchesAdapter
        }
        loadMatches()
    }

    private fun setupFloatingActionButton() {
        binding.fabSimulate.setOnClickListener { view ->
            view.animate().rotationBy(360f).setDuration(500).setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
//                    val random = Random
//                    matchesAdapter.currentList.forEachIndexed { index, match ->
//                        match.homeTeam.score = random.nextInt(match.homeTeam.stars + 1)
//                        match.awayTeam.score = random.nextInt(match.awayTeam.stars + 1)
//                        matchesAdapter.notifyItemChanged(index)
//                    }
                    matchesAdapter.simulateMatches()
                }
            })
        }
    }

    private fun setupMatchesSwipe() {
        binding.swipeLayout.setOnRefreshListener(this::loadMatches)
    }

    private fun loadMatches() {
        viewModel.matches.observe(this@MainActivity) { state ->
            when(state) {
                is State.Loading -> {
                    binding.swipeLayout.isRefreshing = true
                }
                is State.Error -> {
                    binding.swipeLayout.isRefreshing = false
                    val errorMessage = state.error.message ?: getString(R.string.error_api)
                    showErrorMessage(errorMessage)
                }
                is State.Success -> {
                    matchesAdapter.submitList(state.list)
                    binding.swipeLayout.isRefreshing = false
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onMatchClick(match: Match) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRAS_MATCH, match)
        startActivity(intent)
    }
}