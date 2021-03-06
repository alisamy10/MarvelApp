package com.ali.marvelapp.ui.fragment.homeFragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali.marvelapp.R
import com.ali.marvelapp.common.*
import com.ali.marvelapp.data.model.homeModel.Results
import com.ali.marvelapp.ui.MainActivity
import com.ali.marvelapp.ui.MarvelViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), HomeAdapter.Interaction {

    private val homeAdapter by lazy { HomeAdapter(this) }

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private lateinit var navController:NavController
    lateinit var viewModel: MarvelViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        navController=Navigation.findNavController(view)

        setupRecyclerView()
        observeHomeData()
        onToolBarClick()
    }

    private fun setupRecyclerView() {

        homeCharactersRecycler.apply {
            adapter=homeAdapter
            addOnScrollListener(this@HomeFragment.scrollListener)
        }
    }

    private fun observeHomeData() {

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Error -> {
                    ProgressBar.gone()
                   isLoading=false
                    it.msg?.let { msg -> showToast(msg) }
                }
                is Resource.Loading -> {
                    ProgressBar.show()
                    isLoading=true
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        ProgressBar.gone()
                        isLoading=false
                        it.data.data?.results?.let { it1 -> homeAdapter.submitList(it1) }
                        val  totalPages=it.data.data?.total!! / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.homePage == totalPages
                    }
                }
            }
        })
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getHomeData()
                isScrolling = false
            } else {
                homeCharactersRecycler.setPadding(0, 0, 0, 0)

            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun onToolBarClick() {
        toolbar_home_search_btn.setOnClickListener {
              navController.navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }




    override fun onItemSelected(position: Int, item: Results) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }

}