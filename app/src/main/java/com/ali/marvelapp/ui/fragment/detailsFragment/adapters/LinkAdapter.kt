package com.ali.marvelapp.ui.fragment.detailsFragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ali.marvelapp.R
import com.ali.marvelapp.data.model.homeModel.Url
import kotlinx.android.synthetic.main.item_links.view.*

class LinkAdapter(private val interaction: Interaction? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Url>() {

        override fun areItemsTheSame(oldItem: Url, newItem: Url): Boolean {
         return  oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Url, newItem: Url): Boolean {
            return oldItem ==newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return LinkViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_links,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LinkViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Url>) {
        differ.submitList(list)
    }

    class LinkViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Url) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            val link =item.type?.substring(0,1)?.toUpperCase()+item.type?.substring(1)
            itemLinkTypeTv.text=link




        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Url)
    }
}

