package com.azeem.morty.characters_pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.azeem.morty.Constants
import com.azeem.morty.characters_pagination.CharactersDataSourceFactory
import com.azeem.morty.characters_pagination.CharactersRepository
import com.azeem.morty.network.response.GetCharacterByIdResponse

class CharactersViewModel() : ViewModel() {


    private val repository = CharactersRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(Constants.PAGE_SIZE)
        .setPrefetchDistance(Constants.PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope, repository)
    val charactersPagedListLiveData: LiveData<PagedList<GetCharacterByIdResponse>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()


}