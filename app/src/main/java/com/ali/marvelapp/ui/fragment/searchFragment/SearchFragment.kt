package com.ali.marvelapp.ui.fragment.searchFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
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
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchAdapter.Interaction {

    private val searchAdapter by lazy {
        SearchAdapter(
            this
        )
    }
    lateinit var viewModel: MarvelViewModel
    private lateinit var navController: NavController

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        navController= Navigation.findNavController(view)

        setupRecyclerView()
        observeSearchData()



        var job: Job? = null
        etSearch.addTextChangedListener{
            job?.cancel()

            job = MainScope().launch{
                delay(500)
                it?.let {
                    if(it.toString().isNotEmpty()) {
                        viewModel.findCharactersByName(it.toString())
                    }
                }
            }

        }
        toolbarHomeCancelBtn.setOnClickListener{
          navController.navigateUp()
        }


    }


    private fun setupRecyclerView() {
        searchRecyclerView.apply {
            adapter = searchAdapter
            addOnScrollListener(this@SearchFragment.scrollListener)

        }
    }

    private fun observeSearchData() {
        viewModel.getSearchList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Error -> {
                    ProgressBar.gone()
                    isLoading=false
                    it.msg?.let { msg -> showToast(msg) }
                }
                is Resource.Loading -> {
                    paginationProgressBar.show()
                    isLoading=true
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        paginationProgressBar.gone()
                        isLoading=false
                        it.data?.results?.let { it1 -> searchAdapter.submitList(it1) }
                        val  totalPages= it.data.total!! / QUERY_PAGE_SIZE + 2
                        //isLastPage = viewModel.searchPage == totalPages

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
                viewModel.findCharactersByName(etSearch.text.toString())
                isScrolling = false
            } else {
                searchRecyclerView.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    override fun onItemSelected(position: Int, item: Results) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }

}