package io.github.tuguzt.pcbuilder.presentation.view.adapters

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ComponentItemBinding
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.view.ComponentListFragmentDirections

class ComponentViewHolder(private val binding: ComponentItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var _component: Component
    val component get() = _component

    init {
        binding.root.setOnClickListener {
            val component = ComponentData(component)
            val action = ComponentListFragmentDirections.actionComponentFragment(component)
            it.findNavController().navigate(action)
        }
    }

    fun bind(component: Component) {
        _component = component
        binding.run {
            name.text = component.name
            description.text = component.description
        }
    }
}
