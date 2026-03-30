package com.renatoramos.rickandmorty.data.store.dto.episodes

import com.renatoramos.rickandmorty.domain.viewobject.episodes.EpisodeViewObject
import com.squareup.moshi.Json

data class EpisodeDTO(
    @param:Json(name = "air_date")
    val airDate: String,
    @param:Json(name = "characters")
    val characters: List<String>,
    @param:Json(name = "created")
    val created: String,
    @param:Json(name = "episode")
    val episode: String,
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "name")
    val name: String,
    @param:Json(name = "url")
    val url: String
)

fun EpisodeDTO.toEpisodeViewObject(): EpisodeViewObject = EpisodeViewObject(
    airDate = airDate,
    characters = characters,
    created = created,
    episode = episode,
    id = id,
    name = name,
    url = url
)
