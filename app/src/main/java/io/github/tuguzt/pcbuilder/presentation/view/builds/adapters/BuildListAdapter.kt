package io.github.tuguzt.pcbuilder.presentation.view.builds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ItemBuildBinding
import io.github.tuguzt.pcbuilder.domain.model.build.Build
import io.github.tuguzt.pcbuilder.presentation.view.diffutils.DiffCallback

/**
 * [RecyclerView.Adapter] that can display a [Build].
 *
 * @see Build
 */
class BuildListAdapter : ListAdapter<Build, BuildViewHolder>(DiffCallback<String, Build>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBuildBinding.inflate(layoutInflater, parent, false)
        return BuildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val build = currentList[position]
        holder.bind(build)
    }

    fun add(index: Int, build: Build) {
        val newList = currentList.toMutableList().apply {
            add(index, build)
        }
        submitList(newList)
    }

    fun removeAt(position: Int): Build {
        val build = currentList[position]
        submitList(currentList - build)
        return build
    }
}
