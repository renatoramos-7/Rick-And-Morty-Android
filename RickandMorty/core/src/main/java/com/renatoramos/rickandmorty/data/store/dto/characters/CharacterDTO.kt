package com.renatoramos.rickandmorty.data.store.dto.characters

import com.renatoramos.rickandmorty.data.store.dto.locations.LocationShortDTO
import com.renatoramos.rickandmorty.data.store.dto.locations.toLocationShortViewObject
import com.renatoramos.rickandmorty.domain.viewobject.characters.CharacterViewObject
import com.squareup.moshi.Json

data class CharacterDTO(
    @param:Json(name = "created")
    val created: String,
    @param:Json(name = "episode")
    val episodes: List<String>,
    @param:Json(name = "gender")
    val gender: String,
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "image")
    val image: String,
    @param:Json(name = "location")
    val location: LocationShortDTO,
    @param:Json(name = "name")
    val name: String,
    @param:Json(name = "origin")
    val origin: LocationShortDTO,
    @param:Json(name = "species")
    val species: String,
    @param:Json(name = "status")
    val status: String,
    @param:Json(name = "type")
    val type: String,
    @param:Json(name = "url")
    val url: String
): Any()

fun CharacterDTO.toCharacterViewObject(): CharacterViewObject = CharacterViewObject(
        created = this.created,
        episodes = this.episodes,
        gender = this.gender,
        id = this.id,
        image = this.image,
        location = this.location.toLocationShortViewObject(),
        origin = this.origin.toLocationShortViewObject(),
        name = this.name,
        species = this.species,
        status = this.status,
        type = this.type,
        url = this.url
)
