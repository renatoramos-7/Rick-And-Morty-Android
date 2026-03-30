package com.renatoramos.rickandmorty.domain.mapping

import com.renatoramos.rickandmorty.data.store.dto.characters.CharacterDTO
import com.renatoramos.rickandmorty.data.store.dto.characters.toCharacterViewObject
import com.renatoramos.rickandmorty.data.store.dto.episodes.EpisodeDTO
import com.renatoramos.rickandmorty.data.store.dto.episodes.toEpisodeViewObject
import com.renatoramos.rickandmorty.data.store.dto.locations.LocationShortDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class DomainMappingTest {

    @Test
    fun `character dto maps to character view object`() {
        val characterDto = CharacterDTO(
            created = "2017-11-04T18:48:46.250Z",
            episodes = listOf("episode/1", "episode/2"),
            gender = "Male",
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = LocationShortDTO(name = "Citadel of Ricks", url = "location/3"),
            name = "Rick Sanchez",
            origin = LocationShortDTO(name = "Earth", url = "location/1"),
            species = "Human",
            status = "Alive",
            type = "",
            url = "character/1"
        )

        val result = characterDto.toCharacterViewObject()

        assertEquals("Rick Sanchez", result.name)
        assertEquals("Citadel of Ricks", result.location.name)
        assertEquals("Earth", result.origin.name)
        assertEquals("Human", result.species)
    }

    @Test
    fun `episode dto maps to episode view object`() {
        val episodeDto = EpisodeDTO(
            airDate = "December 2, 2013",
            characters = listOf("character/1", "character/2"),
            created = "2017-11-10T12:56:33.798Z",
            episode = "S01E01",
            id = 1,
            name = "Pilot",
            url = "episode/1"
        )

        val result = episodeDto.toEpisodeViewObject()

        assertEquals("Pilot", result.name)
        assertEquals("S01E01", result.episode)
        assertEquals("December 2, 2013", result.airDate)
        assertEquals(2, result.characters.size)
    }
}
