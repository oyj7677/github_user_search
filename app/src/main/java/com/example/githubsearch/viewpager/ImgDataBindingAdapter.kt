package com.example.githubsearch.viewpager

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("profileImgUrl")
fun profileImgBindingAdapter(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("bookmarkImg")
fun bookmarkImgBindingAdapter(imgView: ImageView, isBookmark: Boolean) {
    if(isBookmark) {
        imgView.setImageResource(com.example.githubsearch.R.drawable.img_bookmark_on)
    } else {
        imgView.setImageResource(com.example.githubsearch.R.drawable.img_bookmark_off)
    }
}
