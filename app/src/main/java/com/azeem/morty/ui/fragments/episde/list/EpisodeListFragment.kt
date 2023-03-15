package com.azeem.morty.ui.fragments.episde.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.airbnb.epoxy.EpoxyController
import com.azeem.morty.NavGraphDirections
import com.azeem.morty.R
import com.azeem.morty.databinding.FragmentEpisodeListBinding
import com.azeem.morty.domain.models.Episode
import com.azeem.morty.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_episode_list.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class EpisodeListFragment : BaseFragment(R.layout.fragment_episode_list) {

    private var _binding:FragmentEpisodeListBinding?= null
    private val binding:FragmentEpisodeListBinding by lazy {
        _binding!!
    }
    private val viewModel:EpisodesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        val controller = EpisodeListEpoxyController{

            findNavController().navigate(NavGraphDirections.actionGlobalEpisodeDetailBottomSheetFragment(it))
        }

        lifecycleScope.launch {

            viewModel.flow.collectLatest { pagingData: PagingData<EpisodesUiModel> ->
                controller.submitData(pagingData)
            }
        }
        binding.epoxyRecyclerView.setController(controller)

    }
    override fun onDestroyView(

    ) {
        super.onDestroyView()
        _binding = null
    }

}