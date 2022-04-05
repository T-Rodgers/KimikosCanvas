package com.tdr.app.kimikoscanvas.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.CanvasViewModel
import com.tdr.app.kimikoscanvas.databinding.ListFragmentBinding
import com.tdr.app.kimikoscanvas.utils.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@ExperimentalCoroutinesApi
class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding
    private lateinit var adapter: CanvasCardAdapter
    private lateinit var navController: NavController

    val _viewModel: CanvasViewModel by viewModel()

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        // Inflate the layout for this fragment'
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
        binding.recyclerView.adapter = adapter
        setHasOptionsMenu(true)

        binding.loginBtn.setOnClickListener {
            launchSignInFlow()
        }

        observeStatus()

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

        val navController = this.findNavController()

        when (item.itemId) {
            R.id.action_sign_out -> AuthUI.getInstance().signOut(requireContext())
                .addOnCompleteListener {
                    _viewModel.clearItemList()
                }
            else -> return item.onNavDestinationSelected(navController)

        }
        return true

    }

    private fun observeAuthState() {

        navController = findNavController()
        loginViewModel.authenticationState.observe(
            viewLifecycleOwner
        ) { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    removeErrorLayout()
                    _viewModel.retrieveImagesFromDatabase()
                }

                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {

                    showErrorLayout()
                }
                else -> Timber.i("Unknown Authentication Error")
            }
        }
    }

    private fun observeStatus() {

        loginViewModel.authStatus.observe(viewLifecycleOwner, {event ->
            event?.getContentIfNotHandled()?.let{
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        _viewModel.statusMessage.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let {
                Snackbar.make(requireView(), it, BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction(R.string.error_message) {
                        _viewModel.retrieveImagesFromDatabase()
                    }
                    .show()
            }
        })
    }

    private fun showErrorLayout() {
        _viewModel.clearItemList()
        binding.loginMessage.visibility = VISIBLE
        binding.loginBtn.visibility = VISIBLE
    }

    private fun removeErrorLayout() {
        binding.loginMessage.visibility = GONE
        binding.loginBtn.visibility = GONE
    }

    private fun launchSignInFlow() {

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .setTheme(R.style.Theme_KimikosCanvas)
            .setLogo(R.drawable.kc_logo_black)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Timber.i("${result.resultCode}")

        if (result.resultCode == Activity.RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser

            Timber.i("Sign-in successful, User: ${user?.displayName}")

        } else {
            Timber.i("Error Logging in ${response?.error?.errorCode}")
        }
    }
}