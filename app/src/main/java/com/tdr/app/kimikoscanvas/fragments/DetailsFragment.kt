package com.tdr.app.kimikoscanvas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {

    private lateinit var binding : DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)

        val canvas = DetailsFragmentArgs.fromBundle(requireArguments()).selectedImage
        binding.canvas = canvas

        return binding.root
    }
}