package com.ali.marvelapp.ui.fragment.detailsFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.ali.marvelapp.R
import com.ali.marvelapp.common.*
import com.ali.marvelapp.data.model.detailsModel.ResultsDetails
import com.ali.marvelapp.ui.MarvelViewModel
import com.ali.marvelapp.ui.fragment.detailsFragment.adapters.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details), DetailsAdapter.Interaction {

    private val viewModel: MarvelViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

   // private val detailsAdapter by lazy { DetailsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindData()
        viewModel.loadDetails()

         observeDetailsData()

    }

    private fun setupRecyclers(recyclerView: RecyclerView, listType: String, detailsItems: List<ResultsDetails>) {
        var adapter =DetailsAdapter(this, listType, detailsItems)
        recyclerView.adapter = adapter
        adapter.differ.submitList(detailsItems)

    }


    private fun observeDetailsData() {

        viewModel.getComicsList().observe(viewLifecycleOwner, Observer {
           setupRecyclers(detailsComicsRecycler, COMICS,it)


        })


        viewModel.getEventsList().observe(viewLifecycleOwner, Observer {
            setupRecyclers(detailsEventsRecycler, EVENTS,it)


        })

        viewModel.getSeriesList().observe(viewLifecycleOwner, Observer {
            setupRecyclers(detailsSeriesRecycler, SERIES,it)


        })


        viewModel.getStoriesList().observe(viewLifecycleOwner, Observer {
            setupRecyclers(detailsStoriesRecycler, STORIES,it)


        })

    }


    private fun bindData() {
        detailsNameValueTv.text=args.results.name
        if(args.results.description.isNullOrEmpty())
            detailsDescriptionValueTv.text=detailsDescriptionValueTv.text.toString()
        else
            detailsDescriptionValueTv.text=args.results.description

        var image ="${args.results.thumbnail?.path}/landscape_xlarge.${args.results.thumbnail?.extension}"

        detailsCharacterPoster.loadImage(image)

    }

    override fun onItemSelected(position: Int, listType:String) {


        Log.d("a",listType+position)
         val action = DetailsFragmentDirections.actionDetailsFragmentToPagerDetailsFragment(listItem = listType,pos = position)
        findNavController().navigate(action)
    }


}