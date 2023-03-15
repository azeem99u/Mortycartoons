package com.azeem.morty.ui.fragments.episde.list

import com.azeem.morty.domain.mappers.EpisodeMapper
import com.azeem.morty.domain.models.Episode
import com.azeem.morty.network.NetworkLayer
import com.azeem.morty.network.response.GetCharacterByIdResponse
import com.azeem.morty.network.response.GetEpisodeByIdResponse
import com.azeem.morty.network.response.GetEpisodePageResponse

class EpisodeRepository {

    suspend fun fetchEpisodePage(pageIndex:Int):GetEpisodePageResponse?{
        val request = NetworkLayer.apiClient.getEpisodePage(pageIndex)
        if (!request.isSuccessful){
            return null
        }

        return request.body

    }
    suspend fun getEpisodeById(episodeId:Int):Episode?{
        val request = NetworkLayer.apiClient.getEpisodeById(episodeId)
        if (!request.isSuccessful){
            return null
        }
        val characterList = getCharacterFromEpisodeResponse(request.body)

        return EpisodeMapper.buildFrom(networkEpisode = request.body, networkCharacters = characterList)

    }

    private suspend fun getCharacterFromEpisodeResponse(episodeByIdResponse: GetEpisodeByIdResponse): List<GetCharacterByIdResponse> {
        val characterList  =episodeByIdResponse.characters.map {
            it.substring(startIndex =it.lastIndexOf("/")+1)

        }

        val request =NetworkLayer.apiClient.getMultipleCharacters(characterList)

        return request.body
    }


}