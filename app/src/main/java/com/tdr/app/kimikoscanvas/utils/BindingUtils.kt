package com.tdr.app.kimikoscanvas.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.Canvas
import com.tdr.app.kimikoscanvas.canvas.FirebaseApiStatus


@BindingAdapter("canvasName")
fun TextView.setCanvasText(item: Canvas?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("canvasPrice")
fun TextView.setFormattedPrice(item: Canvas?) {
    item?.let {
        text = item.price?.let { convertToCurrency(item.price) }
    }

}

@BindingAdapter("status")
fun bindStatus(statusImageView: ImageView, status: FirebaseApiStatus?) {
    when (status) {
        FirebaseApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        FirebaseApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_baseline_error_48)
        }
        FirebaseApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("canvasImage")
fun setCanvasImage(imgView: ImageView, item: Canvas?) {
    item?.let {
        Glide.with(imgView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.kc_logo_black)
            .error(R.drawable.ic_baseline_error_48)
            .into(imgView)
    }
}

@BindingAdapter("canvases")
fun bindRecyclerView(recyclerView: RecyclerView, canvases: List<Canvas>?) {
    val adapter = recyclerView.adapter as CanvasCardAdapter
    adapter.submitList(canvases)
}