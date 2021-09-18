package com.mirea.tuguzt.pcbuilder.presentation.repository.converters

import androidx.room.TypeConverter
import io.nacular.measured.units.Distance
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object DistanceConverter {
    @TypeConverter
    fun fromDistance(value: Measure<Distance>?): Double? {
        return value?.let { it `in` meters }
    }

    @TypeConverter
    fun toDistance(value: Double?): Measure<Distance>? {
        return value?.let { value * meters }
    }
}
