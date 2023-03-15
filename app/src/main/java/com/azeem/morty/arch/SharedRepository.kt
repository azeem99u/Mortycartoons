package com.azeem.morty.arch

import com.azeem.morty.domain.mappers.CharacterMapper
import com.azeem.morty.domain.models.Character
import com.azeem.morty.network.MortyCash
import com.azeem.morty.network.NetworkLayer
import com.azeem.morty.network.response.GetCharacterByIdResponse
import com.azeem.morty.network.response.GetEpisodeByIdResponse

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): Character? {


        val cashCharacter = MortyCash.characterMap[characterId]
        if (cashCharacter != null){
            return cashCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed||!request.isSuccessful) {
            return null
        }

        val networkEpisodes = getEpisodesFromCharacterIdResponse(request.body)
        val character = CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )

        MortyCash.characterMap[characterId] = character
        return character
    }

    private suspend fun getEpisodesFromCharacterIdResponse(characterResponse: GetCharacterByIdResponse): List<GetEpisodeByIdResponse> {
        val episodeRange = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/")+1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed||!request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }

}