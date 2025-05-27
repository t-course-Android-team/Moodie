package com.example.feature_search.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.feature_search.R
import com.example.feature_search.databinding.SearchFragmentBinding
import com.example.feature_response.presentation.ResponseFragment.Companion.DATA
import com.example.feature_search.domain.RemoteOpenRouterRepository
import com.example.feature_search.domain.RepositoryFactory
import com.example.feature_search.domain.SearchMovieUseCase
import com.example.feature_search.domain.WatchedMoviesRepository
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var data: String

    // data которая передаётся в фрагмент Response (пока что просто string)

    private val featureSearchViewModel: FeatureSearchViewModel by activityViewModels {
        FeatureSearchViewModelFactory(searchMovieUseCase = SearchMovieUseCase(RepositoryFactory.createOpenRouterRepository()))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initTestButton()
    }

    private fun initTestButton() {
        with(binding) {
            searchButton.setOnClickListener {
                val prompt = RequestUIModel(
                    genre = genreText.text.toString(),
                    released = releaseText.text.toString(),
                    reference = referenceText.text.toString(),
                    mood = moodText.text.toString(),
                    country = countryText.text.toString()
                )

                lifecycleScope.launch {

                    featureSearchViewModel.searchMovies(prompt)


                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObserve() {
        featureSearchViewModel.watchedMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let { data ->
                val bundle = Bundle().apply {
                    putString(DATA, data)
                }
                findNavController().navigate(
                    R.id.action_searchFragment_to_nav_graph_response, bundle
                )


            }
        }
    }

}
