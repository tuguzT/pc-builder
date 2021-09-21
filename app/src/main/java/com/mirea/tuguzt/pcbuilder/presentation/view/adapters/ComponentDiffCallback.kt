package com.mirea.tuguzt.pcbuilder.presentation.view.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.mirea.tuguzt.pcbuilder.domain.model.Component

object ComponentDiffCallback : DiffUtil.ItemCallback<Component>() {
    override fun areItemsTheSame(oldItem: Component, newItem: Component) = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Component, newItem: Component) = oldItem == newItem
}
