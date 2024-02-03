package com.example.localremot.presentation.screen.home

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.localremot.databinding.FragmentHomeBinding
import com.example.localremot.presentation.event.ConnectionEvent
import com.example.localremot.presentation.model.Category
import com.example.localremot.presentation.screen.common.base.BaseFragment
import com.example.localremot.presentation.screen.home.adapter.CategoryRecyclerAdapter
import com.example.localremot.presentation.screen.home.adapter.ConnectionRecyclerAdapter
import com.example.localremot.presentation.state.ConnectionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapterConnection: ConnectionRecyclerAdapter
    private lateinit var adapterCategory: CategoryRecyclerAdapter

    override fun bind() {
        bindAdapter()
    }

    override fun bindViewActionListeners() {
        adapterListener()
    }

    override fun bindObserves() {
        viewModel.onEvent(ConnectionEvent.FetchConnections)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionState.collect {
                    handleConnectionState(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCategory.collect { state ->

                    state.connections?.let { item ->
                        adapterConnection.submitList(item)
                    }
                }
            }
        }
    }

    private fun adapterListener() {
        adapterCategory.setOnItemClickListener(
            listener = {
                viewModel.onEvent(ConnectionEvent.GetCategory(it.category))
            }
        )
    }


    private fun handleConnectionState(state: ConnectionState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.connections?.let {
            adapterConnection.submitList(it)

            val uniqueCategories = HashSet<String>()
            it.forEach { connection ->
                if (uniqueCategories.add(connection.category)) {
                    adapterCategory.setData(
                        Category(
                            id = connection.id,
                            category = connection.category
                        )
                    )
                }
            }

        }

        state.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            viewModel.onEvent(ConnectionEvent.ResetErrorMessage)
        }
    }


    private fun bindAdapter() = with(binding) {
        adapterConnection = ConnectionRecyclerAdapter()
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = adapterConnection

        adapterCategory = CategoryRecyclerAdapter()
        recyclerCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerCategory.adapter = adapterCategory
    }

}