package com.azeem.morty.ui.fragments.episde.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azeem.morty.domain.mappers.EpisodeMapper
import com.azeem.morty.domain.models.Episode
import com.azeem.morty.network.NetworkLayer

class EpisodePagingSource(
    private val episodeRepository: EpisodeRepository

) : PagingSource<Int, EpisodesUiModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesUiModel> {
        val pageNumber = params.key ?: 1

        val previousKey = if (pageNumber == 1) {
            null
        } else {
            pageNumber - 1
        }
        val pageRequest = NetworkLayer.apiClient.getEpisodePage(pageNumber)
        if (!pageRequest.isSuccessful) {
            return LoadResult.Error(pageRequest.exception!!)
        }
        if (!pageRequest.isSuccessful) {
            return LoadResult.Error(pageRequest.exception!!)
        }

        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = pageRequest.body.results.map {
                EpisodesUiModel.Item(EpisodeMapper.buildFrom(it)) },
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(pageRequest.body.info.next)
        )
    }


    override fun getRefreshKey(state: PagingState<Int, EpisodesUiModel>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}