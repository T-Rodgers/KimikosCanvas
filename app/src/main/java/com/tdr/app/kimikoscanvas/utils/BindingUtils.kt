package com.tdr.app.kimikoscanvas.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.Transition
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.FirebaseApiStatus
import com.tdr.app.kimikoscanvas.data.Canvas


@BindingAdapter("canvasName")
fun TextView.setCanvasText(item: Canvas?) {
    item?.let {
        text = item.name
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
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .error(R.drawable.ic_baseline_error_48)
            .into(imgView)
    }
}

@BindingAdapter("canvases")
fun bindRecyclerView(recyclerView: RecyclerView, canvases: List<Canvas>?) {
    val adapter = recyclerView.adapter as CanvasCardAdapter
    adapter.submitList(canvases)
}