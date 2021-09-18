package com.mirea.tuguzt.pcbuilder.presentation.repository.converters

import androidx.room.TypeConverter
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object MassConverter {
    @TypeConverter
    fun fromMass(value: Measure<Mass>?): Double? {
        return value?.let { it `in` grams }
    }

    @TypeConverter
    fun toMass(value: Double?): Measure<Mass>? {
        return value?.let { value * grams }
    }
}
