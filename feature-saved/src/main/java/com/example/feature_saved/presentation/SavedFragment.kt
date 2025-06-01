package com.example.feature_saved.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_saved.R
import com.example.feature_saved.databinding.SavedFragmentBinding
import com.example.feature_saved.presentation.recycler.SavedFragmentAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedFragment : Fragment(R.layout.saved_fragment) {
    private lateinit var binding: SavedFragmentBinding

    private lateinit var adapter: SavedFragmentAdapter
    private val viewModel: SavedFragmentViewModel by activityViewModels {
        ViewModelFactory(requireActivity().application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SavedFragmentBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        lifecycleScope.launch {
            viewModel.getSavedCount()
            viewModel.savedCount.collect { number ->
                if (number != viewModel.films.value?.size){
                    viewModel.loadInitialData()
                    viewModel.savedCount.value = viewModel.films.value?.size ?: 0
                }
            }
        }
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SavedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SavedFragmentBinding.bind(view)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView(){
        adapter = SavedFragmentAdapter(
            deleteMovie = { name ->
                viewModel.deleteMovie(name, {adapter.notifyDataSetChanged()})
            },
            watchMovie = { name ->
                viewModel.watchMovie(name, {adapter.notifyDataSetChanged()})
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SavedFragment.adapter
            addOnScrollListener(createScrollListener())
        }
    }

    private fun createScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            val lastVisible = layoutManager.findLastVisibleItemPosition()

            when (val state = viewModel.screenState.value) {
                is State.Content -> {
                    if (firstVisible < (currentLimit / 3) && state.canLoadPrevious) {
                        viewModel.loadPreviousItems()
                    }
                    if (lastVisible > (currentLimit / 3 * 2) && state.canLoadMore) {
                        viewModel.loadMoreItems()
                    }
                }
                else -> {}
            }
        }
    }

    private fun observeViewModel() {

        viewModel.films.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Content -> showContentState(state)
                is State.Error -> showErrorState(state)
                State.Loading -> showLoadingState()
            }
        }
    }

    private fun showContentState(state: State.Content) {
        with (binding) {
            shimmerViewContainer.isVisible = false
            recyclerView.isVisible = true

            loadMoreProgress.isVisible = state.loadingMore
            loadPreviousProgress.isVisible = state.loadingPrevious
        }
    }

    private fun showErrorState(state: State.Error) {
        with (binding) {
            shimmerViewContainer.isVisible = false
            recyclerView.isVisible = false
        }
    }

    private fun showLoadingState() {
        with (binding) {
            shimmerViewContainer.isVisible = true
            recyclerView.isVisible = false
        }
    }

    companion object {
        private const val currentLimit = 30
    }
}