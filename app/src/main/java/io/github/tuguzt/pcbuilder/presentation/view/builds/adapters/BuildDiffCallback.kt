package io.github.tuguzt.pcbuilder.presentation.view.builds.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import io.github.tuguzt.pcbuilder.domain.model.build.Build

object BuildDiffCallback : DiffUtil.ItemCallback<Build>() {
    override fun areItemsTheSame(oldItem: Build, newItem: Build) = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Build, newItem: Build) = oldItem == newItem
}
