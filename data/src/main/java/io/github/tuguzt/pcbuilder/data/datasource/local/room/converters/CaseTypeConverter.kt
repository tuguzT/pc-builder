package io.github.tuguzt.pcbuilder.data.datasource.local.room.converters

import androidx.room.TypeConverter
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseType

object CaseTypeConverter {
    @JvmStatic
    @TypeConverter
    fun fromCaseType(value: CaseType?): String? = value?.toString()

    @JvmStatic
    @TypeConverter
    fun toCaseType(value: String?): CaseType? =
        CaseType.ATX.values().firstOrNull { "$it" == value }
            ?: CaseType.MicroATX.values().firstOrNull { "$it" == value }
            ?: CaseType.MiniITX.values().firstOrNull { "$it" == value }
            ?: CaseType.HTPC.takeIf { "$it" == value }
}
