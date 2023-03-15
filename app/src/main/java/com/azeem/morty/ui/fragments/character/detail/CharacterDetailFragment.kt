package com.azeem.morty.ui.fragments.character.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.azeem.morty.NavGraphDirections
import com.azeem.morty.R
import com.azeem.morty.arch.ShareViewModel
import com.azeem.morty.databinding.FragmentCharacterDetailBinding
import com.azeem.morty.databinding.FragmentCharacterListBinding
import com.azeem.morty.ui.fragments.BaseFragment

class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShareViewModel by lazy {
        ViewModelProvider(this).get(ShareViewModel::class.java)
    }
    private val safeArgs: CharacterDetailFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailBinding.bind(view)
        viewModel.refreshCharacter(safeArgs.characterId)

        val epoxyController = CharacterDetailsEpoxyController {
            findNavController().navigate(
                NavGraphDirections.actionGlobalEpisodeDetailBottomSheetFragment(
                    it
                )
            )
        }

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->
            if (character == null) {
                Toast.makeText(
                    requireActivity(), "Un successful network call!",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigateUp()
                return@observe
            }

            epoxyController.character = character
        }

        binding.epoxyRecycleView.setControllerAndBuildModels(epoxyController)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}