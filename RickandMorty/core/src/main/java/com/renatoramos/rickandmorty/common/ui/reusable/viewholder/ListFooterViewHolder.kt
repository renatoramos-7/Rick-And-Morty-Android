package com.renatoramos.rickandmorty.common.ui.reusable.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatoramos.rickandmorty.common.databinding.ItemListFooterBinding
import com.renatoramos.rickandmorty.common.util.State

class ListFooterViewHolder(
    private val binding: ItemListFooterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(status: State?) {
        binding.progressBar.visibility = if (status == State.LOADING) android.view.View.VISIBLE else android.view.View.INVISIBLE
        binding.txtError.visibility = if (status == State.ERROR) android.view.View.VISIBLE else android.view.View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val binding = ItemListFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            binding.txtError.setOnClickListener { retry() }
            return ListFooterViewHolder(binding)
        }
    }
}
