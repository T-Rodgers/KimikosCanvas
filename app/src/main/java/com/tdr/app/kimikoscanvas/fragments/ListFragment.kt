package com.tdr.app.kimikoscanvas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.app.kimikoscanvas.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        // Inflate the layout for this fragment'
        val viewModel = ViewModelProvider(this).get(CanvasViewModel::class.java)
        binding.canvasViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = CanvasCardAdapter(CanvasCardAdapter.OnClickListener {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        })
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}