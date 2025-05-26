package com.example.feature_saved.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_saved.R
import com.example.feature_saved.databinding.SavedFragmentBinding
import com.example.feature_saved.domain.LocalFilmRepository
import com.example.feature_saved.presentation.recycler.SavedFragmentAdapter

class SavedFragment : Fragment(R.layout.saved_fragment) {
    private lateinit var binding: SavedFragmentBinding

    private lateinit var adapter: SavedFragmentAdapter
    private val viewModel: SavedFragmentViewModel by activityViewModels {
        ViewModelFactory(LocalFilmRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SavedFragmentBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SavedFragmentBinding.bind(view)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView(){
        adapter = SavedFragmentAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this.adapter
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
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Content -> TODO()
                is State.Error -> TODO()
                State.Loading -> TODO()
            }
        }

        viewModel.films.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    companion object {
        private const val currentLimit = 30
    }
}