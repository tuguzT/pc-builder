package io.github.tuguzt.pcbuilder.presentation.view.components.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ItemComponentBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.component.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.presentation.view.diffutils.DiffCallback
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel

/**
 * [RecyclerView.Adapter] subclass that can display a [Component].
 *
 * @see Component
 */
class ComponentListAdapter(private val sharedViewModel: ComponentsSharedViewModel) :
    ListAdapter<Component, ComponentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val binding = ItemComponentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ComponentViewHolder(binding, sharedViewModel)
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val component = when (val component = currentList[position]) {
            is ComponentData -> component
            is ComponentDto -> ComponentData(component)
            else -> ComponentData(component)
        }
        holder.bind(component)
    }
}
