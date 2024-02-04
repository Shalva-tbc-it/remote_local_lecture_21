package com.example.localremot.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.localremot.databinding.RecyclerCategoryBinding

class CategoryRecyclerAdapter: ListAdapter<String,CategoryRecyclerAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    private var onItemClickListener: ((String) -> Unit)? = null

    inner class CategoryViewHolder(private val binding: RecyclerCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = currentList[adapterPosition]
            binding.btnCategory.text = item
        }

        fun getCategory() {
            val item = currentList[adapterPosition]
            binding.btnCategory.setOnClickListener {
                onItemClickListener?.let {
                    it.invoke(item)
                }
            }
        }

    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            RecyclerCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind()
        holder.getCategory()
    }

    class CategoryDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

}