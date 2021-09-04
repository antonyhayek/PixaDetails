package com.antonyhayek.pixadetails.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.antonyhayek.pixadetails.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BindingUtils {
    companion object {

        @BindingAdapter("image"/*,"placeholder"*/)
        @JvmStatic
        fun setImage(imageView: ImageView, image: String?) {

            if (!image.isNullOrEmpty()) {


                Glide.with(imageView.context)
                    .load(image)
                    .centerCrop()
                    .fitCenter()
                    .apply(RequestOptions().placeholder(R.drawable.item_black_bg))
                    .into(imageView)

            } else {
                imageView.setImageResource(R.drawable.item_black_bg)
            }


        }
    }


}