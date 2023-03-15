package com.azeem.morty.ui.fragments.episde.detailbottomsheet

import com.airbnb.epoxy.EpoxyController
import com.azeem.morty.R
import com.azeem.morty.databinding.ModelCharacterListItemSquareBinding
import com.azeem.morty.databinding.ModelEpisodeListItemBinding
import com.azeem.morty.domain.models.Character
import com.azeem.morty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class EpisodeDetailEpoxyModel(
    private val characterList:List<Character>
):EpoxyController(){

    override fun buildModels() {
        characterList.forEach {
            character ->
            CharacterEpoxyModel(
                character.image,
                character.name
            ).id(character.id).addTo(this)

        }
    }

    data class CharacterEpoxyModel(
        val imageUrl:String,
        val name:String
    ):ViewBindingKotlinModel<ModelCharacterListItemSquareBinding>(R.layout.model_character_list_item_square){
        override fun ModelCharacterListItemSquareBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }

    }
}