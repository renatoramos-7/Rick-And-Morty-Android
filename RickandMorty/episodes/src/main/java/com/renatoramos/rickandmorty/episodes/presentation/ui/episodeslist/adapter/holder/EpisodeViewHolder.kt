package com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatoramos.rickandmorty.domain.viewobject.episodes.EpisodeViewObject
import com.renatoramos.rickandmorty.episodes.databinding.EpisodesViewholderBinding

class EpisodeViewHolder(
    private val binding: EpisodesViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(episodeViewObject: EpisodeViewObject?) {
        episodeViewObject?.let {
            binding.repoNameTextView.text = episodeViewObject.name
            binding.description.text = episodeViewObject.episode
            binding.loginOwneTextView.text = episodeViewObject.airDate
        }
    }

    companion object {
        fun create(parent: ViewGroup): EpisodeViewHolder {
            val binding = EpisodesViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return EpisodeViewHolder(binding)
        }
    }
}
