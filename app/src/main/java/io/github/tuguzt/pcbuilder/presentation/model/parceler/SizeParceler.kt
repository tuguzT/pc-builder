package io.github.tuguzt.pcbuilder.presentation.model.parceler

import android.os.Parcel
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.times
import kotlinx.parcelize.Parceler

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
