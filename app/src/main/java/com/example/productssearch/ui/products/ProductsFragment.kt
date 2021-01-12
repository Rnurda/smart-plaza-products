package com.example.productssearch.ui.products

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.productssearch.R
import com.example.productssearch.data.SmartPlazaProduct
import com.example.productssearch.data.filterModel
import com.example.productssearch.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products), SmartPlazaProductAdapter.OnItemClickListener, FilterDialog.DialogForResult {
    val viewModel by viewModels<ProductsViewModel>()

    private var _binding: FragmentProductsBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductsBinding.bind(view)

        val adapter = SmartPlazaProductAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = SmartplazaProductLoadStateAdapter { adapter.retry()},
                footer = SmartplazaProductLoadStateAdapter {adapter.retry()},
            )

            buttonRetry.setOnClickListener { adapter.retry() }
        }

        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadStae ->
            binding.apply {
                progressBar.isVisible = loadStae.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadStae.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadStae.source.refresh is LoadState.Error
                textViewError.isVisible = loadStae.source.refresh is LoadState.Error

                // empty view
                if(loadStae.source.refresh is LoadState.NotLoading &&
                        loadStae.append.endOfPaginationReached&&
                        adapter.itemCount < 1){
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                }else{
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_product, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    var filermod = filterModel(query,"",999999999,0)
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchProducts(filermod)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var dialog = FilterDialog(this)
        return when (item.itemId) {
            R.id.action_filter -> {
                dialog.show(parentFragmentManager,"$$$$$$$$")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResultSuccess(sortName: String, highCost: String, lowCost: String) {
            var filermod = filterModel("", sortName, highCost.toInt(), lowCost.toInt())
            binding.recyclerView.scrollToPosition(0)
            viewModel.searchProducts(filermod)

    }

    override fun onResultFailed(ex: Exception) {
        TODO("Not yet implemented")
    }

    override fun onResultNeutral(item: Any?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

    override fun onItemClick(product: SmartPlazaProduct) {
        val action = ProductsFragmentDirections.actionProductsFragmentToDetatilsFragment(product)
        findNavController().navigate(action)
    }
}
