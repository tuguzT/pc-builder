package io.github.tuguzt.pcbuilder.presentation.view.account.adapters

import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.ItemAccountBinding
import io.github.tuguzt.pcbuilder.presentation.model.user.UserData

class AccountViewHolder(
    private val binding: ItemAccountBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserData): Unit = binding.run {
        id.text = root.context.getString(R.string.display_id, user.id)
        role.text = user.role.toString()
    }
}
