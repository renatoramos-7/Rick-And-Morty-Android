package com.renatoramos.rickandmorty.data.store.dto.response

import com.squareup.moshi.Json

data class RickAndMortyResponse<T>(
    @field:Json(name = "info")
    val info: Info,
    @field:Json(name = "results")
    val results: List<T>
)
