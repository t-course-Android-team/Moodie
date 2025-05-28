package com.example.feature_search.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.data.WatchedMoviesDao
import com.example.data.WatchedMoviesDataBase
import com.example.data.WatchedMoviesRepoImpl
import com.example.feature_search.R
import com.example.feature_search.databinding.SearchFragmentBinding
import com.example.feature_response.presentation.ResponseFragment.Companion.DATA
import com.example.feature_search.domain.RemoteOpenRouterRepository
import com.example.feature_search.domain.RepositoryFactory
import com.example.feature_search.domain.SearchMovieUseCase
import com.example.utils.InternetChecker.isInternetAvailable
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var data: String

    // data которая передаётся в фрагмент Response (пока что просто string)

    private val featureSearchViewModel: FeatureSearchViewModel by activityViewModels {
        FeatureSearchViewModelFactory(
            searchMovieUseCase = SearchMovieUseCase(
                RepositoryFactory.createOpenRouterRepository(), repository = WatchedMoviesRepoImpl(
                    WatchedMoviesDataBase.WatchedMoviesDataBase.getWatchedDataBase(requireContext())
                        .watchedMoviesDao()
                )
            )
        )
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
                searchButton.isEnabled = false

                val prompt = RequestUIModel(
                    genre = genreText.text.toString(),
                    released = releaseText.text.toString(),
                    reference = referenceText.text.toString(),
                    mood = moodText.text.toString(),
                    country = countryText.text.toString()
                )

                lifecycleScope.launch {
                    try {
                        if (isInternetAvailable(requireContext())) {
                            featureSearchViewModel.searchMovies(prompt)
                        } else {
                            showInternetError()
                        }
                    } catch (e: Exception) {
                        Log.e("SEARCH", "Error", e)
                        showInternetError()
                    } finally {
                        searchButton.isEnabled = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObserve() {
        featureSearchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            with(binding) {
                when (isLoading) {

                    true -> {
                        loadingContainer.visibility = View.VISIBLE
                        loadingAnimation.playAnimation()
                        questions.visibility = View.GONE
                        searchButton.visibility = View.GONE


                    }

                    false -> {
                        loadingContainer.visibility = View.GONE
                        loadingAnimation.cancelAnimation()
                        questions.visibility = View.VISIBLE
                        searchButton.visibility = View.VISIBLE
                    }
                }
            }
        }

        featureSearchViewModel.watchedMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let { data ->
                if (isInternetAvailable(requireContext())) {

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

    private fun showInternetError() {
        Snackbar.make(
            binding.root,
            "Нет интернет-соединения",
            Snackbar.LENGTH_LONG
        ).setAction("Повторить") {
            initTestButton()
        }.show()
    }
}
