package io.github.tuguzt.pcbuilder.presentation.model.parceler

import android.os.Parcel
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times
import kotlinx.parcelize.Parceler

/**
 * Custom [Parceler] which converts [mass][Mass]
 * to [grams] (represented by [Double]) and vice-versa.
 */
object MassParceler : Parceler<Measure<Mass>> {
    override fun create(parcel: Parcel) = parcel.readDouble() * grams

    override fun Measure<Mass>.write(parcel: Parcel, flags: Int) =
        parcel.writeDouble(this `in` grams)
}
