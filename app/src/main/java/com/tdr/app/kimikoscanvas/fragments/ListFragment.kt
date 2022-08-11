package com.tdr.app.kimikoscanvas.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.app.kimikoscanvas.databinding.ListFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding
    private lateinit var adapter: CanvasCardAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val _viewModel: CanvasViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)

        binding.canvasViewModel = _viewModel
        binding.lifecycleOwner = this

        adapter = CanvasCardAdapter(CanvasCardAdapter.OnClickListener { canvas ->
            _viewModel.onNavigateToDetails()
            _viewModel.navigateToDetails.observe(viewLifecycleOwner) {
                if (it) {
                    this.findNavController()
                        .navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(canvas))
                    _viewModel.doneNavigatingToDetails()
                }
            }

        })
        val staggeredLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recyclerView.layoutManager = staggeredLayoutManager
        binding.recyclerView.adapter = adapter
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val navController = this.findNavController()

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    }

}