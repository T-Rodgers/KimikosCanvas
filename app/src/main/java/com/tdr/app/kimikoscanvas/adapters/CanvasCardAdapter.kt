package com.tdr.app.kimikoscanvas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdr.app.kimikoscanvas.canvas.Canvas
import com.tdr.app.kimikoscanvas.databinding.CanvasListItemBinding

class CanvasCardAdapter :
    ListAdapter<Canvas, CanvasCardAdapter.ProductCardViewHolder>(CanvasCardDiffUtilCallback()) {

    var data = listOf<Canvas>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductCardViewHolder {

        return ProductCardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductCardViewHolder, position: Int) {
        val item = getItem(position)

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
}

class CanvasCardDiffUtilCallback : DiffUtil.ItemCallback<Canvas>() {
    override fun areItemsTheSame(oldItem: Canvas, newItem: Canvas): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Canvas, newItem: Canvas): Boolean {
        return oldItem == newItem
    }

}
