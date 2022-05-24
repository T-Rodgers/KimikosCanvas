package com.tdr.app.kimikoscanvas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat.setTransitionName
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.data.Canvas
import com.tdr.app.kimikoscanvas.databinding.CanvasListItemBinding

class CanvasCardAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Canvas, CanvasCardAdapter.ProductCardViewHolder>(CanvasCardDiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductCardViewHolder {

        return ProductCardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductCardViewHolder, position: Int) {
        val item = getItem(position)
        val view = holder.itemView.findViewById<ImageView>(R.id.canvas_image)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item, view)
        }
        holder.bind(item)
    }

    class ProductCardViewHolder(private val binding: CanvasListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Canvas) {
            binding.canvas = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductCardViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CanvasListItemBinding.inflate(inflater, parent, false)
                return ProductCardViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (canvas: Canvas, canvasImageView: ImageView) -> Unit) {
        fun onClick(canvas: Canvas, canvasImageView: ImageView) = clickListener(canvas, canvasImageView)
    }

    class CanvasCardDiffUtilCallback : DiffUtil.ItemCallback<Canvas>() {
        override fun areItemsTheSame(oldItem: Canvas, newItem: Canvas): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Canvas, newItem: Canvas): Boolean {
            return oldItem == newItem
        }
    }
}
