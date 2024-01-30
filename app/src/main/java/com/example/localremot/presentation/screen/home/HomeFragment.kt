package com.example.localremot.presentation.screen.home

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.localremot.R
import com.example.localremot.databinding.FragmentHomeBinding
import com.example.localremot.presentation.event.ConnectionEvent
import com.example.localremot.presentation.screen.common.base.BaseFragment
import com.example.localremot.presentation.screen.home.adapter.ConnectionRecyclerAdapter
import com.example.localremot.presentation.state.ConnectionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ConnectionRecyclerAdapter

    override fun bind() {
        bindAdapter()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {
        internetPermission()
    }

    private fun internetOK() {

        viewModel.onEvent(ConnectionEvent.FetchConnections)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionState.collect {
                    handleConnectionState(state = it)
                }
            }
        }
    }

    private fun internetNA() {

        viewModel.onEvent(ConnectionEvent.FetchConnectionsFromDb)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasks.collect {
                    if (it.isNotEmpty()) {
                        adapter.submitList(it)
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.not_found_items), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    private fun handleConnectionState(state: ConnectionState) {
        binding.progressBar.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.connections?.let {
            adapter.submitList(it)
        }

        state.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            viewModel.onEvent(ConnectionEvent.ResetErrorMessage)
        }
    }


    private fun bindAdapter() {
        adapter = ConnectionRecyclerAdapter()
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.adapter = adapter
    }

    private fun internetPermission() {
        if (viewModel.isInternetAvailable(requireContext())) {
            internetOK()
            Toast.makeText(requireContext(),
                getString(R.string.success_internet),
                Toast.LENGTH_SHORT).show()
        } else {
            internetNA()
            Toast.makeText(requireContext(),
                getString(R.string.error_internet),
                Toast.LENGTH_SHORT).show()
        }
    }

}