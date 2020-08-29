package com.ali.marvelapp.ui.fragment.detailsFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.ali.marvelapp.R
import com.ali.marvelapp.common.loadImage
import com.ali.marvelapp.data.model.detailsModel.ResultsDetails
import kotlinx.android.synthetic.main.item_details_pager.view.*

class DetailsPagerAdapter(private val context: Context, private val detailsItems: List<ResultsDetails>) :
    PagerAdapter() {



    override fun getCount(): Int {
        return detailsItems.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val pagerLayout: View = LayoutInflater.from(context).inflate(R.layout.item_details_pager, container, false)

        if (detailsItems[position].thumbnail == null)

            pagerLayout.itemDetailsPagerImg.setImageResource(R.drawable.ic_broken_image)

        else{
            val image:String= "${detailsItems[position].thumbnail?.path}/portrait_uncanny.${detailsItems[position].thumbnail?.extension}"
            pagerLayout.itemDetailsPagerImg.loadImage(image)
        }

            pagerLayout.itemDetailsPagerText.text = detailsItems[position].title
            container.addView(pagerLayout, 0)
            return pagerLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}
