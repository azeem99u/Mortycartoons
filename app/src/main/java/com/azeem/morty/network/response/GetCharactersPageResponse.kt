package com.azeem.morty.network.response

data class GetCharactersPageResponse (
    val info: PageInfo = PageInfo(),
    val results: List<GetCharacterByIdResponse> = emptyList()
)