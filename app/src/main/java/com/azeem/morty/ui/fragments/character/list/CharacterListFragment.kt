package com.azeem.morty.ui.fragments.character.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.azeem.morty.R
import com.azeem.morty.characters_pagination.CharactersViewModel
import com.azeem.morty.databinding.FragmentCharacterListBinding
import com.azeem.morty.databinding.FragmentCharacterSearchBinding
import com.azeem.morty.ui.fragments.BaseFragment
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CharacterListFragment : BaseFragment(R.layout.fragment_character_list) {


    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)


    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCharacterListBinding.bind(view)


        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        binding.epoxyRecycleView.setControllerAndBuildModels(epoxyController)

    }

    private fun onCharacterSelected(characterId: Int) {
        FirebaseCrashlytics.getInstance().recordException(
            RuntimeException("character selected = $characterId")
        )
        findNavController().navigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                characterId
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}