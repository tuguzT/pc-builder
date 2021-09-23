package io.github.tuguzt.pcbuilder.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ComponentItemBinding
import io.github.tuguzt.pcbuilder.domain.model.Component

/**
 * [RecyclerView.Adapter] that can display a [Component].
 *
 * @see Component
 */
class ComponentListAdapter :
    ListAdapter<Component, ComponentListAdapter.ComponentViewHolder>(ComponentDiffCallback) {

    inner class ComponentViewHolder(private val binding: ComponentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(component: Component) {
            binding.run {
                name.text = component.name
                description.text = component.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val binding = ComponentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ComponentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val component = currentList[position]
        holder.bind(component)
    }
}
