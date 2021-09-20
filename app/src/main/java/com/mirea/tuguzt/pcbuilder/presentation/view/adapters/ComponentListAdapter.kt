package com.mirea.tuguzt.pcbuilder.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirea.tuguzt.pcbuilder.databinding.ComponentItemBinding
import com.mirea.tuguzt.pcbuilder.domain.model.Component

/**
 * [RecyclerView.Adapter] that can display a [Component].
 *
 * @see Component
 */
class ComponentListAdapter(val data: List<Component>) :
    RecyclerView.Adapter<ComponentListAdapter.ComponentViewHolder>() {

    inner class ComponentViewHolder(val binding: ComponentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val binding = ComponentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ComponentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val item = data[position]
        holder.binding.run {
            name.text = item.name
            description.text = item.description
        }
    }

    override fun getItemCount() = data.size
}
