package br.com.runes.matchsimulator.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.runes.matchsimulator.databinding.MatchItemBinding
import br.com.runes.matchsimulator.domain.Match
import kotlin.random.Random

class MatchesAdapter(private val clickListener: MatchClickListener) : ListAdapter<Match, MatchesAdapter.ViewHolder>(DiffCallback()) {

    fun simulateMatches() {
        val random = Random
        currentList.onEachIndexed { index, match ->
            match.homeTeam.score = random.nextInt(match.homeTeam.stars + 1)
            match.awayTeam.score = random.nextInt(match.awayTeam.stars + 1)
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MatchItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = currentList[position]
        holder.bind(match, clickListener)
    }

    class ViewHolder(private val binding: MatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(match: Match, clickListener: MatchClickListener) {
                binding.match = match
                binding.listener = clickListener
            }
    }

    class DiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean = oldItem == newItem

    }
}

