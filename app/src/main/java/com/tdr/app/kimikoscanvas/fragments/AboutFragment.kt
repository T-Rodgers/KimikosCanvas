package com.tdr.app.kimikoscanvas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.databinding.AboutFragmentBinding

class AboutFragment : Fragment() {

    private lateinit var binding : AboutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false)
        return binding.root
    }

}