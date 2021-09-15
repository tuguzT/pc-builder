package com.mirea.tuguzt.pcbuilder.presentation.room.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mirea.tuguzt.pcbuilder.domain.Grams
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.Size

/**
 * Data Transfer Object for [Component].
 *
 * @see Component
 */
@Entity(tableName = "component")
data class ComponentDTO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    override val name: String,
    override val description: String,
    override val weight: Grams,
    @Embedded override val size: Size,
) : Component
