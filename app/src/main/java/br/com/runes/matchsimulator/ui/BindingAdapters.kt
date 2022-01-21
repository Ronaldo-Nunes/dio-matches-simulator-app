package br.com.runes.matchsimulator.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatRatingBar


object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setImage")
    fun AppCompatImageView.setImage(imageUrl: String?) {
        if (!imageUrl.isNullOrBlank()) {
            Glide.with(context)
                .load(imageUrl)
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("setRating")
    fun AppCompatRatingBar.setRating(value: Int?) {
        rating = if (value != null) {
            val rate = value.toFloat()
            rate
        } else {
            0f
        }
    }
}