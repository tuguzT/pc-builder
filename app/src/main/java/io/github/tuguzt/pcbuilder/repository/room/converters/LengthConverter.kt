package io.github.tuguzt.pcbuilder.repository.room.converters

import androidx.room.TypeConverter
import io.nacular.measured.units.Length
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

/**
 * Converts [length][Length] to [meters] (represented by [Double]) and vice-versa.
 */
object LengthConverter {
    @JvmStatic
    @TypeConverter
    fun fromDistance(value: Measure<Length>?): Double? = value?.let { it `in` meters }

    @JvmStatic
    @TypeConverter
    fun toDistance(value: Double?): Measure<Length>? = value?.let { it * meters }
}
