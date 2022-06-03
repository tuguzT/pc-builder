package io.github.tuguzt.pcbuilder.repository.room.converters

import androidx.room.TypeConverter
import io.github.tuguzt.pcbuilder.domain.model.units.Power
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object PowerConverter {
    @JvmStatic
    @TypeConverter
    fun fromPower(value: Measure<Power>?): Double? = value?.let { it `in` watt }

    @JvmStatic
    @TypeConverter
    fun toPower(value: Double?): Measure<Power>? = value?.let { it * watt }
}
