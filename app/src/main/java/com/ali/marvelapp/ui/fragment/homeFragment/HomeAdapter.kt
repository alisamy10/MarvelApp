package com.ali.marvelapp.ui.fragment.homeFragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.ali.marvelapp.R
import com.ali.marvelapp.common.loadImage
import com.ali.marvelapp.data.model.Results
import kotlinx.android.synthetic.main.item_char.view.*

class HomeAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Results>() {

        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return HomeViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_char,
                    parent,
                    false
                ),
                interaction
            )

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Results>) {
        differ.submitList(list)
    }

    class HomeViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Results) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            var image ="${item.thumbnail?.path}/landscape_xlarge.${item.thumbnail?.extension}"
            itemCharImg.loadImage(image)
            itemCharName.text = item.name



        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Results)
    }
}

