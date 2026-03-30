package com.renatoramos.rickandmorty.characters.presentation.ui.feature.characterslist.adapter.holder

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatoramos.rickandmorty.characters.databinding.CharacterViewholderBinding
import com.renatoramos.rickandmorty.common.extensions.loadWithGlide
import com.renatoramos.rickandmorty.common.ui.components.DrawableRequestListener
import com.renatoramos.rickandmorty.domain.viewobject.characters.CharacterViewObject

class CharactersViewHolder(
    private val binding: CharacterViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(characterViewObject: CharacterViewObject?) {
        characterViewObject?.let {
            binding.titleTextView.text = it.name
            setupImageView(it.image)
        }
    }

    private fun setupImageView( url: String) {
        binding.placeGuideImageView.loadWithGlide(url, object : DrawableRequestListener() {
            override fun onResourceReady(bitmap: Bitmap){
                binding.placeGuideImageView.setRatio(bitmap.width.toFloat() / bitmap.height)
            }
        })
    }

    companion object {
        fun create(parent: ViewGroup): CharactersViewHolder {
            val binding = CharacterViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CharactersViewHolder(binding)
        }
    }
}
