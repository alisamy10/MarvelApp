package com.ali.marvelapp.ui.fragment.detailsFragment

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.ali.marvelapp.R
import com.ali.marvelapp.data.model.detailsModel.ResultsDetails
import com.ali.marvelapp.ui.MarvelViewModel
import com.ali.marvelapp.ui.fragment.detailsFragment.adapters.DetailsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pager_details.*

@AndroidEntryPoint
class PagerDetailsFragment : Fragment(R.layout.fragment_pager_details),
    ViewPager.OnPageChangeListener {

    private val args: PagerDetailsFragmentArgs by navArgs()

    private val viewModel: MarvelViewModel by viewModels()
    private lateinit var navController: NavController

    //Initialize pages assuming at least 1 page if this activity opened
    private var currentPage = 1
    private var totalPages = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enableFullScreen(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        val listType: String = args.listItem


        val detailsItems: List<ResultsDetails> = viewModel.getPagerList(listType)?.value!!

        totalPages = detailsItems.size
        currentPage = args.pos + 1
        setupViewpager(detailsItems)
        setCurrentPage(currentPage)

        detailsPagerCloseBtn.setOnClickListener{
            navController.navigateUp()
        }
    }
    private fun enableFullScreen(flagLayoutNoLimits: Int, flagLayoutNoLimits1: Int) {
        activity?.getWindow()?.setFlags(flagLayoutNoLimits, flagLayoutNoLimits1)

    }

    private fun setCurrentPage(currentPage: Int) {
        detailsPagesCountTv.text = "$currentPage/$totalPages"
        this.currentPage = currentPage
    }
    private fun setupViewpager( detailsItems:List<ResultsDetails>) {

        detailsPagerViewpager.adapter = DetailsPagerAdapter(requireContext(), detailsItems)
        detailsPagerViewpager.setCurrentItem(args.pos, true)
        detailsPagerViewpager.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        setCurrentPage(position + 1)
    }

    override fun onPageSelected(position: Int) {
    }

}