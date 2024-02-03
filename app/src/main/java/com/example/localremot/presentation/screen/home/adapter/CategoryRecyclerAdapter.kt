package com.example.localremot.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.localremot.databinding.RecyclerCategoryBinding
import com.example.localremot.presentation.model.Category

class CategoryRecyclerAdapter: RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>() {

    private val categoryList = ArrayList<Category>()

    private var onItemClickListener: ((Category) -> Unit)? = null

    inner class CategoryViewHolder(private val binding: RecyclerCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.btnCategory.text = item.category

        }

        fun getCategory(item: Category) {
            binding.btnCategory.setOnClickListener {
                onItemClickListener?.let {
                    it.invoke(item)
                }
            }
        }

    }

    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(category: Category) {
        categoryList.add(category)
        notifyItemChanged(categoryList.size)
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

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.bind(currentItem)
        holder.getCategory(currentItem)
    }




}