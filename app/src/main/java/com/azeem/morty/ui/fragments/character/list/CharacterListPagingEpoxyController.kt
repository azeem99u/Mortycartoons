package com.azeem.morty.ui.fragments.character.list

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.azeem.morty.R
import com.azeem.morty.databinding.ModelCharacterListItemBinding
import com.azeem.morty.databinding.ModelCharacterListTitleBinding
import com.azeem.morty.epoxy.LoadingEpoxyModel
import com.azeem.morty.epoxy.ViewBindingKotlinModel
import com.azeem.morty.network.response.GetCharacterByIdResponse

import com.squareup.picasso.Picasso
import java.util.*

class CharacterListPagingEpoxyController(
   private val onCharacterSelected:(Int)-> Unit
):PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*>  {
        return CharacterGridItemEpoxyModel(item!!.id,item!!.image,item.name,onCharacterSelected).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if(models.isEmpty()){
            LoadingEpoxyModel().id("loading_").addTo(this)
            return
        }
        CharacterGridTitleEpoxyModel("Main Family").id("main_family_header").addTo(this)

        super.addModels(models.subList(0,5))
        (models.subList(5,models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].uppercase()
        }.forEach{
            mapEntry ->
            val character = mapEntry.key.toString().uppercase(locale = Locale.US)
            CharacterGridTitleEpoxyModel(character).id(character).addTo(this)
            super.addModels(mapEntry.value)
        }

    }

    data class CharacterGridItemEpoxyModel(
        val id:Int,
        val imageUrl:String,
        val name:String,
        val onCharacterSelected: (Int) -> Unit

    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item){
        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
            root.setOnClickListener{
                onCharacterSelected(id)
            }
        }
    }

    data class CharacterGridTitleEpoxyModel(
        val title:String
    ): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title){
        override fun ModelCharacterListTitleBinding.bind() {
            textView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount

        }
    }
}