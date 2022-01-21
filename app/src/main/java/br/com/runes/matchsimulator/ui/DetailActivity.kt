package br.com.runes.matchsimulator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.runes.matchsimulator.databinding.ActivityDetailBinding
import br.com.runes.matchsimulator.domain.Match

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRAS_MATCH = "match_extras"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra() {
        intent.extras?.getParcelable<Match>(EXTRAS_MATCH)?.let { matchExtra ->
            binding.match = matchExtra
        }
    }
}