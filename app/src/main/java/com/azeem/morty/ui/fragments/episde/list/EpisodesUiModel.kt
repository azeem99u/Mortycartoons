package com.azeem.morty.ui.fragments.episde.list

import com.azeem.morty.domain.models.Episode

sealed class EpisodesUiModel{
    class Item(val episode: Episode):EpisodesUiModel()
    class Header(val text:String):EpisodesUiModel()
}
