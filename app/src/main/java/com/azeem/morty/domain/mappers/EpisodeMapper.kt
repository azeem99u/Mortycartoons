package com.azeem.morty.domain.mappers

import com.azeem.morty.domain.models.Character
import com.azeem.morty.domain.models.Episode
import com.azeem.morty.network.response.GetCharacterByIdResponse
import com.azeem.morty.network.response.GetEpisodeByIdResponse

object EpisodeMapper{

    fun buildFrom(networkEpisode:GetEpisodeByIdResponse,
                  networkCharacters:List<GetCharacterByIdResponse> = emptyList()):Episode{
        return Episode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            seasonNumber = getSeasonFromEpisodeString(networkEpisode.episode),
            episodeNumber = getEpisodeFromEpisodeString(networkEpisode.episode),
            characters = networkCharacters.map {
                CharacterMapper.buildFrom(it)
            }
        )
    }

    private fun getSeasonFromEpisodeString(episode: String):Int{
        val endIndex = episode.indexOfFirst { it.equals('e',true) }
        if (endIndex ==-1){
            return 0
        }
        return episode.substring(1,endIndex).toIntOrNull()?:0
    }

    private fun getEpisodeFromEpisodeString(episode: String):Int{
        val firstIndex = episode.indexOfFirst { it.equals('e',true) }
        if (firstIndex ==-1){
            return 0
        }
        return episode.substring(firstIndex+1).toIntOrNull()?:0
    }


}