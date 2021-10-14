package com.tdr.app.kimikoscanvas.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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

// TODO: Implement BindingAdapter for canvas images
//@BindingAdapter("canvasImage")
//fun setCanvasImage(imgView: ImageView, imgUrl: String?) {
//    item?.let {
//        Glide.with(imgView.context)
//            .load(R.drawable.kc_logo_black)
//            .into(imgView)
//    }
//}

@BindingAdapter("canvases")
fun bindRecyclerView(recyclerView: RecyclerView, canvases: List<Canvas>?) {
    val adapter = recyclerView.adapter as CanvasCardAdapter
    adapter.submitList(canvases)
}