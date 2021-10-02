package io.github.tuguzt.pcbuilder.presentation.view.components.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ItemComponentBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component

/**
 * [RecyclerView.Adapter] that can display a [Component].
 *
 * @see Component
 */
class ComponentListAdapter : ListAdapter<Component, ComponentViewHolder>(ComponentDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val binding = ItemComponentBinding.inflate(
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

    fun add(index: Int, component: Component) {
        val newList = currentList.toMutableList().apply {
            add(index, component)
        }
        submitList(newList)
    }

    fun removeAt(position: Int): Component {
        val component = currentList[position]
        submitList(currentList - component)
        return component
    }
}
