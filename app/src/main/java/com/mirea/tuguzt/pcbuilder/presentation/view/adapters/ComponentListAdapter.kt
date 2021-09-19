package com.mirea.tuguzt.pcbuilder.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirea.tuguzt.pcbuilder.databinding.ComponentItemBinding
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

/**
 * [RecyclerView.Adapter] that can display a [ComponentDTO].
 *
 * @see ComponentDTO
 */
class ComponentListAdapter(val data: List<ComponentDTO>) :
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
