package com.example.feature_response.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_response.R
import com.example.feature_response.data.retrofit.RetrofitHelper
import com.example.feature_response.databinding.ResponseFragmentBinding
import com.example.feature_response.domain.RemoteFilmRepository
import com.example.feature_response.presentation.recycler.FilmsAdapter
import com.example.feature_response.presentation.recycler.OverlayLayoutManager
import com.example.feature_response.presentation.recycler.SwipeToDeleteCallback
import kotlinx.coroutines.launch

class ResponseFragment : Fragment(R.layout.response_fragment) {

    private val sharedViewModel: ResponseViewModel by activityViewModels {
        ViewModelFactory(RemoteFilmRepository(RetrofitHelper.createRetrofit()))
    }

    private val customAdapter = FilmsAdapter()

    private var _binding: ResponseFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResponseFragmentBinding.inflate(layoutInflater)
        arguments?.getString(DATA)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recyclerView

        recyclerView.layoutManager = OverlayLayoutManager(requireContext()).apply { initializeLayout() }
        recyclerView.adapter = customAdapter

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                customAdapter.removeFilm(position)
                if (customAdapter.itemCount == 0) {
                    sharedViewModel.refresh()
                    findNavController().popBackStack()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        sharedViewModel.getFilms(arguments?.getString(DATA))

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.refresh()
                findNavController().popBackStack()
            }
        })

        binding.errorButton.setOnClickListener {
            sharedViewModel.refresh()
            findNavController().popBackStack()
        }

        manageLoading()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        sharedViewModel.setDataToSave(customAdapter.getFilms())
    }

    private fun initViewModel() {
        sharedViewModel.films.observe(this) { films ->
            val data = films.toMutableList()
            customAdapter.setNewData(data)
            customAdapter.notifyDataSetChanged()
        }

        sharedViewModel.loadProgress.observe(this) { progress ->
            binding.loading.text = getString(R.string.loading_films_progress, progress)
        }
    }

    private fun manageLoading() {
        lifecycleScope.launch {
            sharedViewModel.state.collect {
                val recyclerView = view?.findViewById<View>(R.id.recyclerView)
                val loading = view?.findViewById<View>(R.id.loading)
                val progressBar = view?.findViewById<View>(R.id.progressBar)
                val error = view?.findViewById<View>(R.id.error)
                val errorButton = view?.findViewById<View>(R.id.errorButton)
                when(it) {
                    State.START -> {
                        // может в будущем пригодиться
                    }
                    State.LOADING -> {
                        recyclerView?.visibility = View.INVISIBLE
                        loading?.visibility = View.VISIBLE
                        progressBar?.visibility = View.VISIBLE
                        error?.visibility = View.INVISIBLE
                        errorButton?.visibility = View.INVISIBLE
                    }
                    State.OK -> {
                        recyclerView?.visibility = View.VISIBLE
                        loading?.visibility = View.INVISIBLE
                        progressBar?.visibility = View.INVISIBLE
                        error?.visibility = View.INVISIBLE
                        errorButton?.visibility = View.INVISIBLE
                    }
                    State.ERROR -> {
                        recyclerView?.visibility = View.INVISIBLE
                        loading?.visibility = View.INVISIBLE
                        progressBar?.visibility = View.INVISIBLE
                        error?.visibility = View.VISIBLE
                        errorButton?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    companion object {
        const val DATA = "data"
    }

}