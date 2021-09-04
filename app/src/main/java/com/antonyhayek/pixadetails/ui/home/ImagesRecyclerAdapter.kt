package com.antonyhayek.pixadetails.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.data.remote.responses.ImageResponse
import com.antonyhayek.pixadetails.databinding.ItemRecyclerImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImagesRecyclerAdapter(
    private val images: MutableList<ImageResponse>,
    private val activity: FragmentActivity,
    private val clickListener: (ImageResponse) -> Unit
    ) :
    RecyclerView.Adapter<ImagesRecyclerAdapter.ViewHolder>()
    {

        class ViewHolder(val binding: ItemRecyclerImageBinding) : View.OnClickListener,
            RecyclerView.ViewHolder(binding.root) {
            private lateinit var image: ImageResponse

            init {
                itemView.setOnClickListener(this)
            }


            fun bind(
                image: ImageResponse,
                activity: FragmentActivity,
                clickListener: (ImageResponse) -> Unit
            ) {
                binding.image = image
                binding.executePendingBindings()

                //set to true for ellipsize marquee to work
                binding.tvUserName.isSelected = true

                itemView.setOnClickListener {
                    clickListener(image)
                }
            }

            override fun onClick(view: View?) {}
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemRecyclerImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)
        }

        override fun getItemCount() = images.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(images[position], activity, clickListener)
        }
    }
