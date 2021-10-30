package io.github.tuguzt.pcbuilder.presentation.model.component.parceler

import android.os.Parcel
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.times
import kotlinx.parcelize.Parceler

/**
 * Custom [Parceler] which converts [physical size][Size]
 * to its components in [meters] (represented by [Double]) and vice-versa.
 */
object SizeParceler : Parceler<Size> {
    override fun create(parcel: Parcel) = parcel.run {
        val length = readDouble() * meters
        val width = readDouble() * meters
        val height = readDouble() * meters
        Size(length, width, height)
    }

    override fun Size.write(parcel: Parcel, flags: Int) = parcel.run {
        writeDouble(length `in` meters)
        writeDouble(width `in` meters)
        writeDouble(height `in` meters)
    }
}
