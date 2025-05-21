package com.example.moodbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.moodbook.databinding.ResponseFragmentBinding

class ResponseFragment : Fragment(R.layout.response_fragment) {

    private var _binding: ResponseFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: ResponseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResponseFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textOfData.text = args.data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}