package com.azeem.morty.ui.fragments.character.detail

import android.annotation.SuppressLint
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.azeem.morty.R
import com.azeem.morty.databinding.*
import com.azeem.morty.domain.models.Character
import com.azeem.morty.domain.models.Episode
import com.azeem.morty.epoxy.LoadingEpoxyModel
import com.azeem.morty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso


class CharacterDetailsEpoxyController(
    private val onClick:(Int) -> Unit
) : EpoxyController() {

    private var isLoading = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }

        }

    override fun buildModels() {

        if (isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (character == null) {
            return
        }


        HeaderCharacterEpoxyModel(
            character!!.name,
            character!!.gender,
            character!!.status
        ).id("Header").addTo(this)

        ImageEpoxyModel(character!!.image).id("Image").addTo(this)

        TitleEpoxyModel("Episodes").id("title_episodes").addTo(this)

        if (character!!.episodeList.isNotEmpty()){
            val items = character!!.episodeList.map {
                EpisodeCarouselItem(it,onClick).id(it.id)
            }
            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.15f)
                .addTo(this)
        }

        DataPointEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("data_point_2").addTo(this)
    }

    data class HeaderCharacterEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            aliveTextView.text = status
            if (gender.equals("male", true)) {
                genderImageView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderImageView.setImageResource(R.drawable.ic_female_24)
            }

        }

    }

    data class ImageEpoxyModel(
        val imageUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(headerImageView)
        }

    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointsBinding>(R.layout.model_character_details_data_points) {
        override fun ModelCharacterDetailsDataPointsBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }

    data class EpisodeCarouselItem(val episode: Episode,val onClick: (Int) -> Unit) :
        ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(R.layout.model_episode_carousel_item){
        @SuppressLint("SetTextI18n")
        override fun ModelEpisodeCarouselItemBinding.bind() {
            episodeTextView.text = episode.getFormattedSeasonTruncated()
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
            root.setOnClickListener {
                onClick(episode.id)
            }
        }

    }

    data class TitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title) {

        override fun ModelTitleBinding.bind() {
            titleTextView.text = title
        }
    }
}