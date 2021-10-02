package io.github.tuguzt.pcbuilder.presentation.view.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto

object ComponentDiffCallback : DiffUtil.ItemCallback<Component>() {
    override fun areItemsTheSame(oldItem: Component, newItem: Component) =
        if (oldItem is ComponentDto && newItem is ComponentDto) {
            oldItem.id == newItem.id
        } else {
            oldItem == newItem
        }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Component, newItem: Component) = oldItem == newItem
}
