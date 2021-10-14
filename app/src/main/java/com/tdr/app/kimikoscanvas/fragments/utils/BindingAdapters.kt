package com.tdr.app.kimikoscanvas.fragments.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.Canvas


@BindingAdapter("canvasName")
fun TextView.setCanvasText(item: Canvas?) {
    item?.let {
        text = item.name }
}

@BindingAdapter("canvasPrice")
fun TextView.setFormattedPrice(item: Canvas?) {
    item?.let {
    text = convertToCurrency(item.price)}

}

@BindingAdapter("canvasImage")
fun setCanvasImage(imgView: ImageView) {
    Glide.with(imgView.context)
        .load(R.drawable.kc_logo_black)
        .into(imgView)
}

@BindingAdapter("canvases")
fun bindRecyclerView(recyclerView: RecyclerView, canvases: List<Canvas>?) {
    val adapter = recyclerView.adapter as CanvasCardAdapter
    adapter.submitList(canvases)
}