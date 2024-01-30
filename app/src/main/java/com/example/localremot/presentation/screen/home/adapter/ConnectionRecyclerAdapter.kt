package com.example.localremot.presentation.screen.home.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.localremot.R
import com.example.localremot.databinding.RecyclerItemBinding
import com.example.localremot.presentation.model.Connection

class ConnectionRecyclerAdapter: ListAdapter<Connection, ConnectionRecyclerAdapter.ConnectionViewHolder>(ConnectionsDiffUtil()) {
    inner class ConnectionViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind() = with(binding) {
            val item = currentList[adapterPosition]

            if (!item.favorite) {
                val color = root.context.getColor(R.color.main_bg)
                binding.imgCircle.imageTintList = ColorStateList.valueOf(color)
            }
            tvPrice.text = item.price
            tvTitle.text = item.title
            Glide.with(binding.root)
                .load(item.cover)
                .into(imgGirl)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectionViewHolder {
        return ConnectionViewHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ConnectionViewHolder, position: Int) {
        holder.bind()
    }


    class ConnectionsDiffUtil : DiffUtil.ItemCallback<Connection>() {
        override fun areItemsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem == newItem
        }
    }
}