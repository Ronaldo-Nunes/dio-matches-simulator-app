package br.com.runes.matchsimulator.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.runes.matchsimulator.R
import br.com.runes.matchsimulator.data.api.MatchesAPI
import br.com.runes.matchsimulator.databinding.ActivityMainBinding
import br.com.runes.matchsimulator.domain.Match
import br.com.runes.matchsimulator.framework.MatchClickListener
import br.com.runes.matchsimulator.framework.MatchesAdapter
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity(), MatchClickListener {
    private lateinit var matchesAPI: MatchesAPI
    private lateinit var binding: ActivityMainBinding
    private val matchesAdapter: MatchesAdapter by lazy {
        MatchesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHttpClient()
        setupMatchesList()
        setupMatchesSwipe()
        setupFloatingActionButton()
    }

    private fun setupHttpClient() {
        val client = Retrofit.Builder()
            .baseUrl("https://ronaldo-nunes.github.io/dio-matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesAPI = client.create(MatchesAPI::class.java)
    }

    private fun setupMatchesList() {
        binding.rvMatches.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = matchesAdapter
        }
        findMatchesFromApi()
    }

    private fun setupFloatingActionButton() {
        binding.fabSimulate.setOnClickListener { view ->
            view.animate().rotationBy(360f).setDuration(500).setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    val random = Random
                    matchesAdapter.currentList.forEachIndexed { index, match ->
                        match.homeTeam.score = random.nextInt(match.homeTeam.stars + 1)
                        match.awayTeam.score = random.nextInt(match.awayTeam.stars + 1)
                        matchesAdapter.notifyItemChanged(index)
                    }
                }
            })
        }
    }

    private fun setupMatchesSwipe() {
        binding.swipeLayout.setOnRefreshListener(this::findMatchesFromApi)
    }

    private fun findMatchesFromApi() {
        binding.swipeLayout.isRefreshing = true
        matchesAPI.matches.enqueue(object : Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if (response.isSuccessful) {
                    val matches: List<Match>? = response.body()
                    matchesAdapter.submitList(matches)
                } else {
                    showErrorMessage()
                }
                binding.swipeLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                binding.swipeLayout.isRefreshing = false
                showErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.root, getString(R.string.error_api), Snackbar.LENGTH_LONG).show()
    }

    override fun onMatchClick(match: Match) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRAS_MATCH, match)
        startActivity(intent)
    }
}