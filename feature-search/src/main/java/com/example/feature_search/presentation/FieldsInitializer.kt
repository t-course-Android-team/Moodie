package com.example.feature_search.presentation

import android.text.Editable
import android.text.TextWatcher
import com.example.feature_search.databinding.SearchFragmentBinding

internal object FieldsInitializer {
    fun initTextFields(binding: SearchFragmentBinding) = with(binding) {
        genreText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    genreText.setBackgroundResource(com.example.feature_search.R.drawable.text_background_filled)
                    genreImage.visibility = android.view.View.VISIBLE
                } else {
                    genreText.setBackgroundResource(com.example.feature_search.R.drawable.text_background)
                    genreImage.visibility = android.view.View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        moodText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    moodText.setBackgroundResource(com.example.feature_search.R.drawable.text_background_filled)
                    moodImage.visibility = android.view.View.VISIBLE
                } else {
                    moodText.setBackgroundResource(com.example.feature_search.R.drawable.text_background)
                    moodImage.visibility = android.view.View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        releaseText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    releaseText.setBackgroundResource(com.example.feature_search.R.drawable.text_background_filled)
                    releaseImage.visibility = android.view.View.VISIBLE
                } else {
                    releaseText.setBackgroundResource(com.example.feature_search.R.drawable.text_background)
                    releaseImage.visibility = android.view.View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        countryText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    countryText.setBackgroundResource(com.example.feature_search.R.drawable.text_background_filled)
                    countryImage.visibility = android.view.View.VISIBLE
                } else {
                    countryText.setBackgroundResource(com.example.feature_search.R.drawable.text_background)
                    countryImage.visibility = android.view.View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        referenceText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    referenceText.setBackgroundResource(com.example.feature_search.R.drawable.text_background_filled)
                    referenceImage.visibility = android.view.View.VISIBLE
                } else {
                    referenceText.setBackgroundResource(com.example.feature_search.R.drawable.text_background)
                    referenceImage.visibility = android.view.View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}