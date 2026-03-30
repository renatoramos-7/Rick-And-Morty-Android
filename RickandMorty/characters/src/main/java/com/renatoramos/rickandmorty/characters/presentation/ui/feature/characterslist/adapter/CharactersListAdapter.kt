package com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter.holder.CharactersViewHolder
import com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter.listener.CharactersListListener
import com.renatoramos.rickandmorty.common.ui.reusable.viewholder.ListFooterViewHolder
import com.renatoramos.rickandmorty.common.util.State
import com.renatoramos.rickandmorty.domain.viewobject.characters.CharacterViewObject

class CharactersListAdapter(
    private val retry: () -> Unit,
    private val charactersListListener: CharactersListListener
) : PagedListAdapter<CharacterViewObject, RecyclerView.ViewHolder>(characterViewObjectDiffCallback) {

    private companion object {
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2
        val characterViewObjectDiffCallback = object : DiffUtil.ItemCallback<CharacterViewObject>() {
            override fun areItemsTheSame(oldItem: CharacterViewObject, newItem: CharacterViewObject): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterViewObject, newItem: CharacterViewObject): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            CharactersViewHolder.create(parent)
        } else {
            ListFooterViewHolder.create(retry, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            val item = getItem(position)
            (holder as CharactersViewHolder).bind(item)
            holder.itemView.setOnClickListener {
                item?.let { character ->
                    charactersListListener.onItemClick(character.species)
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
