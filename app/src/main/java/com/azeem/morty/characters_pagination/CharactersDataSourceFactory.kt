package com.azeem.morty.characters_pagination

import androidx.paging.DataSource
import com.azeem.morty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope


class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
) : DataSource.Factory<Int, GetCharacterByIdResponse>() {
    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharactersDataSource(coroutineScope, repository)
    }
}