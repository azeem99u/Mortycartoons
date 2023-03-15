package com.azeem.morty.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azeem.morty.domain.models.Character
import com.azeem.morty.network.MortyCash

import kotlinx.coroutines.launch

class ShareViewModel:ViewModel() {

    private val repository = SharedRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData:LiveData<Character?> = _characterByIdLiveData

    fun refreshCharacter(characterId:Int){

        viewModelScope.launch {
            val character = repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(character)
        }
    }
}