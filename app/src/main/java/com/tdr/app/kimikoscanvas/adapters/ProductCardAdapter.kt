package com.tdr.app.kimikoscanvas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.canvas.Canvas
import com.tdr.app.kimikoscanvas.convertToCurrency

class ProductCardAdapter : RecyclerView.Adapter<ProductCardAdapter.ProductCardViewHolder>() {

    var data = listOf<Canvas>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductCardViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.canvas_card_list_item,
        parent, false)

        return ProductCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductCardViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return data.size

    }

    class ProductCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val canvasImage : ImageView = itemView.findViewById(R.id.canvas_image)
        val canvasName : TextView = itemView.findViewById(R.id.canvas_name)
        val canvasPrice : TextView = itemView.findViewById(R.id.canvas_price)

        fun bind(item: Canvas) {
            canvasImage.setImageResource(R.drawable.kc_logo_black)
            canvasName.text = item.name
            canvasPrice.text = item.price.convertToCurrency()
        }
    }
}