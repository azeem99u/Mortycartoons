package com.azeem.morty.network

import com.azeem.morty.network.response.GetCharacterByIdResponse
import com.azeem.morty.network.response.GetCharactersPageResponse
import com.azeem.morty.network.response.GetEpisodeByIdResponse
import com.azeem.morty.network.response.GetEpisodePageResponse
import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall {
            rickAndMortyService.getCharterById(characterId)
        }
    }

    suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharactersPageResponse>{
        return safeApiCall {
            rickAndMortyService.getChartersPage(pageIndex)
        }
    }

    suspend fun getMultipleCharacters(characterList: List<String>): SimpleResponse<List<GetCharacterByIdResponse>> {
        return safeApiCall { rickAndMortyService.getMultipleCharacters(characterList) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse> {
        return safeApiCall {
            rickAndMortyService.getEpisodeById(episodeId)
        }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall {
            rickAndMortyService.getEpisodeRange(episodeRange)
        }
    }

    suspend fun getEpisodePage(pageIndex:Int):SimpleResponse<GetEpisodePageResponse>{
        return safeApiCall {
            rickAndMortyService.getEpisodePage(pageIndex)
        }
    }


    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }

    suspend fun getCharactersPage( characterName: String, pageIndex: Int,): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getChartersPage( characterName, pageIndex ) }
    }
}