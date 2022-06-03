package com.example.hw2

import android.R.drawable.btn_star_big_off
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw2.data.Item
import com.example.hw2.databinding.ItemListItemBinding
import android.R.drawable.btn_star_big_on
import android.R.drawable.btn_star_big_off
import android.widget.ImageView

class ItemListAdapter(private val onItemClicked:(Item) -> Unit, private val onStarClicked: (Item) -> Unit) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(private var binding:ItemListItemBinding):RecyclerView.ViewHolder(binding.root){
        val itemIsFavourite:ImageView = binding.itemIsFavourite
        fun bind(item: Item) {
            binding.apply {
                itemEnglish.text = item.english
                itemChinese.text = item.chinese
                if(item.isFavourite){
                    itemIsFavourite.setImageResource(btn_star_big_on)
                }else{
                    itemIsFavourite.setImageResource(btn_star_big_off)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemListAdapter.ItemViewHolder {
        return ItemViewHolder(ItemListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemListAdapter.ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.itemIsFavourite.setOnClickListener{
            onStarClicked(current)
        }
        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.english == newItem.english
            }
        }
    }
}