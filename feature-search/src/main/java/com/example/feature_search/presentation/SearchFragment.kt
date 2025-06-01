package com.example.feature_search.presentation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.feature_search.R
import com.example.feature_search.databinding.SearchFragmentBinding
import com.example.feature_response.presentation.ResponseFragment.Companion.DATA
import com.example.utils.InternetChecker.isInternetAvailable
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val featureSearchViewModel: FeatureSearchViewModel by viewModels()
    private var currentPrompt: RequestUIModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initTestButton()
        FieldsInitializer.initTextFields(binding)
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
                        questions.visibility = View.GONE
                        searchButton.visibility = View.GONE
                    }

                    false -> {
                        loadingContainer.visibility = View.GONE
                        questions.visibility = View.VISIBLE
                        searchButton.visibility = View.VISIBLE
                    }
                }
            }
        }

        featureSearchViewModel.resultMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let { data ->
                if (isInternetAvailable(requireContext()) && featureSearchViewModel.isLoading.value!!) {

                    val bundle = Bundle().apply {
                        putString(DATA, data)
                    }
                    if (data != "null") refreshTextFields()
                    findNavController().navigate(
                        R.id.action_searchFragment_to_nav_graph_response, bundle
                    )
                    lifecycleScope.launch {
                        delay(500)
                        featureSearchViewModel.setLoadingEnded()
                    }
                }
            }
        }
    }

    private fun showInternetError() {
        val snackbar = Snackbar.make(
            binding.root, "No internet connection", Snackbar.LENGTH_LONG
        ).setAction("Try Again") {
            initTestButton()
        }

        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.typeface = ResourcesCompat.getFont(requireContext(), com.example.feature_response.R.font.muli_regular)
        val actionView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        actionView.typeface = ResourcesCompat.getFont(requireContext(), com.example.feature_response.R.font.muli_bold)
        actionView.isAllCaps = false

        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.main))

        snackbar.show()
    }

    private fun refreshTextFields() = with(binding) {
        genreText.setText("")
        countryText.setText("")
        moodText.setText("")
        referenceText.setText("")
        releaseText.setText("")
    }

}