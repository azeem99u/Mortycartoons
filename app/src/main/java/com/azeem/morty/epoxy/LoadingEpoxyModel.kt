package com.azeem.morty.epoxy

import com.azeem.morty.R
import com.azeem.morty.databinding.ModelLoadingBinding

class LoadingEpoxyModel: ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading){
    override fun ModelLoadingBinding.bind() {}
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}


