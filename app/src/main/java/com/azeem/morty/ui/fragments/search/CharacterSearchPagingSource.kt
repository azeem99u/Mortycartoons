package com.azeem.morty.ui.fragments.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azeem.morty.domain.mappers.CharacterMapper
import com.azeem.morty.domain.models.Character
import com.azeem.morty.network.NetworkLayer

class CharacterSearchPagingSource(
    private val userSearch:String,
    private val localExceptionCallback:(LocalException) ->Unit
):PagingSource<Int,Character>() {

    sealed class LocalException(
        val title:String,
        val description:String = ""
    ):Exception(){
        object EmptySearch:LocalException(
            title = "Start type to search"
        )
        object NoResult:LocalException(
            title = "Whoops!",
            description = "Looks like your search return no result"
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        if (userSearch.isEmpty()){

            val exception = LocalException.EmptySearch
            localExceptionCallback(exception)
            return LoadResult.Error(exception)

        }
        val pageNumber = params.key ?: 1

        val previousKey = if (pageNumber == 1) {
            null
        } else {
            pageNumber - 1
        }

        val request = NetworkLayer.apiClient.getCharactersPage(userSearch,pageNumber)

        if(request.data?.code() == 404){
            val exception = LocalException.NoResult
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        request.exception?.let {
            return LoadResult.Error(it)
        }
        return LoadResult.Page(
            data = request.bodyNullable?.results?.map {
                CharacterMapper.buildFrom(it)
            }?: emptyList(),
            previousKey,
            getPageIndexFromNext(request.bodyNullable?.info?.next)
        )

    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }
    private fun getPageIndexFromNext(next: String?): Int? {
        if (next == null) {
            return null
        }

        val remainder = next.substringAfter("page=")
        val finalIndex = if (remainder.contains('&')) {
            remainder.indexOfFirst { it == '&' }
        } else {
            remainder.length
        }

        return remainder.substring(0, finalIndex).toIntOrNull()
    }
}