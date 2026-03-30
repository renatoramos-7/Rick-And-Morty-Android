package com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renatoramos.rickandmorty.common.base.BaseFragment
import com.renatoramos.rickandmorty.common.extensions.makeTextToast
import com.renatoramos.rickandmorty.common.util.State
import com.renatoramos.rickandmorty.episodes.databinding.FragmentEpisodesListBinding
import com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter.EpisodesListAdapter
import com.renatoramos.rickandmorty.episodes.presentation.ui.episodeslist.adapter.listener.EpisodesListListener
import javax.inject.Inject

class EpisodesListFragment : BaseFragment(), EpisodesListListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var episodesListViewModel: EpisodesListViewModel
    private var _binding: FragmentEpisodesListBinding? = null
    private val binding: FragmentEpisodesListBinding
        get() = checkNotNull(_binding) { "Binding is only valid between onCreateView and onDestroyView." }
    private lateinit var episodesListAdapter: EpisodesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onItemClick(anyString: String) {
        context?.makeTextToast(anyString, Toast.LENGTH_LONG)?.show()
    }

    private fun initView() {
        bindViewModel()
        setupRecyclerView()
        initialize()
        getAllEpisodes()
        setObservables()
    }

    private fun bindViewModel() {
        episodesListViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[EpisodesListViewModel::class.java]
    }

    private fun setObservables() {
        episodesListViewModel.getState().observe(viewLifecycleOwner) { state ->
            binding.progressBar.visibility =
                if (episodesListViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility =
                if (episodesListViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!episodesListViewModel.listIsEmpty()) {
                episodesListAdapter.setState(state ?: State.DONE)
            }
        }

        episodesListViewModel.reposListLiveData.observe(viewLifecycleOwner) {
            episodesListAdapter.submitList(it)
        }
    }

    private fun initialize() {
        binding.txtError.setOnClickListener { episodesListViewModel.retry() }
    }

    private fun setupRecyclerView() {
        binding.episodesRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.episodesRecyclerView.setHasFixedSize(true)

        episodesListAdapter = EpisodesListAdapter(
            { episodesListViewModel.retry() },
            this
        )

        binding.episodesRecyclerView.adapter = episodesListAdapter
    }

    private fun getAllEpisodes() {
        episodesListViewModel.getAllEpisodes()
    }

    override fun onDestroyView() {
        binding.episodesRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

}
