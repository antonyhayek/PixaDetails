package com.antonyhayek.pixadetails.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.antonyhayek.pixadetails.data.remote.responses.ImageResponse

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.antonyhayek.pixadetails.databinding.ItemRecyclerImageBinding

class ImagesRecyclerAdapter(
    private val clickListener: (ImageResponse) -> Unit
) :
    PagingDataAdapter<ImageResponse, ImagesRecyclerAdapter.ImageViewHolder>(ImagesComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val binding = ItemRecyclerImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindImage(it) }
    }

    inner class ImageViewHolder(private val binding: ItemRecyclerImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindImage(item: ImageResponse) /*= with(binding)*/ {
            binding.image = item
            binding.executePendingBindings()

            itemView.setOnClickListener {
                clickListener(item)
            }
        }
    }

    object ImagesComparator : DiffUtil.ItemCallback<ImageResponse>() {
        override fun areItemsTheSame(oldItem: ImageResponse, newItem: ImageResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageResponse, newItem: ImageResponse): Boolean {
            return oldItem == newItem
        }
    }
}

