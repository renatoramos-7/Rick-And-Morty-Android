package com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.renatoramos.rickandmorty.common.ui.reusable.viewholder.ListFooterViewHolder
import com.renatoramos.rickandmorty.common.util.State
import com.renatoramos.rickandmorty.domain.viewobject.episodes.EpisodeViewObject
import com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter.holder.EpisodeViewHolder
import com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter.listener.EpisodesListListener

class EpisodesListAdapter (
    private val retry: () -> Unit,
    private val episodesListListener: EpisodesListListener
) : PagedListAdapter<EpisodeViewObject, RecyclerView.ViewHolder>(EpisodeViewObjectDiffCallback) {

    private companion object {
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2
        val EpisodeViewObjectDiffCallback = object : DiffUtil.ItemCallback<EpisodeViewObject>() {
            override fun areItemsTheSame(oldItem: EpisodeViewObject, newItem: EpisodeViewObject): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EpisodeViewObject, newItem: EpisodeViewObject): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            EpisodeViewHolder.create(parent)
        } else {
            ListFooterViewHolder.create(retry, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            val item = getItem(position)
            (holder as EpisodeViewHolder).bind(item)
            holder.itemView.setOnClickListener {
                item?.let { episode ->
                    episodesListListener.onItemClick(episode.episode)
                }
            }
        } else {
            (holder as ListFooterViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        val hadFooter = hasFooter()
        val previousState = this.state
        this.state = state
        val hasFooter = hasFooter()

        when {
            hadFooter != hasFooter -> {
                if (hadFooter) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            }
            hasFooter && previousState != state -> notifyItemChanged(itemCount - 1)
        }
    }

}
