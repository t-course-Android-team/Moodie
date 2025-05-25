package com.example.feature_search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.feature_search.R
import com.example.feature_search.databinding.SearchFragmentBinding
import com.example.feature_response.presentation.ResponseFragment.Companion.DATA

class SearchFragment : Fragment(R.layout.search_fragment) {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private var data: String = "Alien Romulus*Interstellar*The Dark Knight*El Camino*minions*Spider-Man*Minecraft*Nobody*five nights at freddy*Central Intelligence"
    // data которая передаётся в фрагмент Response (пока что просто string)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTestButton()
    }

    private fun initTestButton() {
        binding.button.setOnClickListener {
            val bundle = Bundle().apply {
                putString(DATA, data)
            }
            findNavController().navigate(R.id.action_searchFragment_to_nav_graph_response, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}