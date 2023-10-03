package com.example.githubsearch.viewpager

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.githubsearch.GithubUserSearchViewModel

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
    if (isBookmark) {
        imgView.setImageResource(com.example.githubsearch.R.drawable.img_bookmark_on)
    } else {
        imgView.setImageResource(com.example.githubsearch.R.drawable.img_bookmark_off)
    }
}

@BindingAdapter("onEnterSearch")
fun setOnEnterSearch(view: EditText, viewModel: GithubUserSearchViewModel) {
    view.setOnKeyListener { _, keyCode, event ->
        val isEnterKeyPressed =
            event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER
        val isSearchAction = event.action == EditorInfo.IME_ACTION_SEARCH

        if (isEnterKeyPressed || isSearchAction) {
            view.clearFocus()
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            viewModel.searchGithubUser()
            return@setOnKeyListener true
        }

        return@setOnKeyListener false
    }
}
