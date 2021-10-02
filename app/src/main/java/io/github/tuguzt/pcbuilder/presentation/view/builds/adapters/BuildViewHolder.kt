package io.github.tuguzt.pcbuilder.presentation.view.builds.adapters

import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ItemBuildBinding
import io.github.tuguzt.pcbuilder.domain.model.build.Build

class BuildViewHolder(private val binding: ItemBuildBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var _build: Build
    val build get() = _build

    fun bind(build: Build) {
        // todo: not implemented yet
    }
}
