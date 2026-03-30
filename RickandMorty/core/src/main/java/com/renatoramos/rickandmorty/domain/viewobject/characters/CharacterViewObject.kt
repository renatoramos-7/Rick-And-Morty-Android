package com.renatoramos.rickandmorty.domain.viewobject.characters

import com.renatoramos.rickandmorty.domain.viewobject.locations.LocationShortViewObject

data class CharacterViewObject (
    var created : String = "",
    var episodes: List<String> = listOf(),
    var gender: String = "",
    var id: Int = 0,
    var image: String = "",
    var location: LocationShortViewObject,
    var origin: LocationShortViewObject,
    var name: String = "",
    var species: String = "",
    var status: String = "",
    var type: String = "",
    var url: String = ""
)
