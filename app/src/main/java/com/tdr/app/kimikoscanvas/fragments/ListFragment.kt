package com.tdr.app.kimikoscanvas.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.app.kimikoscanvas.databinding.ListFragmentBinding
import com.tdr.app.kimikoscanvas.utils.FirebaseUtils
import com.tdr.app.kimikoscanvas.utils.LoginViewModel

class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var canvasViewModel: CanvasViewModel

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        // Inflate the layout for this fragment'
        canvasViewModel = ViewModelProvider(this).get(CanvasViewModel::class.java)
        binding.canvasViewModel = canvasViewModel
        binding.lifecycleOwner = this

        val adapter = CanvasCardAdapter(CanvasCardAdapter.OnClickListener { canvas ->
            canvasViewModel.onNavigateToDetails()
            canvasViewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
                if (it) {
                    this.findNavController()
                        .navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(canvas))
                    canvasViewModel.doneNavigatingToDetails()
                }
            })

        })
        binding.recyclerView.adapter = adapter
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAuthState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sign_out -> AuthUI.getInstance().signOut(requireContext())
                .addOnCompleteListener {
                    FirebaseUtils().removeListener()
                }
        }
        return super.onOptionsItemSelected(item)

    }

    fun observeAuthState() {

        navController = findNavController()
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
                when (authenticationState) {
                    LoginViewModel.AuthenticationState.AUTHENTICATED -> {

                        canvasViewModel.retrieveImagesFromDatabase()
                    }

                    else -> navController.navigate(R.id.loginFragment)
                }
            })
    }
}