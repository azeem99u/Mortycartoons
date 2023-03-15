package com.azeem.morty.characters_pagination

import com.azeem.morty.network.response.GetCharactersPageResponse
import com.azeem.morty.network.NetworkLayer

class CharactersRepository {

    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}