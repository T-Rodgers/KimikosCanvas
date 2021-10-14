package com.tdr.app.kimikoscanvas.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.adapters.CanvasCardAdapter
import com.tdr.app.kimikoscanvas.canvas.Canvas


@BindingAdapter("canvasName")
fun TextView.setCanvasText(item: Canvas?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("canvasPrice")
fun TextView.setFormattedPrice(item: Canvas?) {
    item?.let {
        text = convertToCurrency(item.price)
    }

}

@BindingAdapter("canvasImage")
fun setCanvasImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply { RequestOptions()
                .placeholder(R.drawable.kc_watermark_10_opacity)
                .error(R.drawable.kc_aperture_foreground)}
            .into(imgView)
    }
}

@BindingAdapter("canvases")
fun bindRecyclerView(recyclerView: RecyclerView, canvases: List<Canvas>?) {
    val adapter = recyclerView.adapter as CanvasCardAdapter
    adapter.submitList(canvases)
}