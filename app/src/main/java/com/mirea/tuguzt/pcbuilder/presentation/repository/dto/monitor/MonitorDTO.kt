package com.mirea.tuguzt.pcbuilder.presentation.repository.dto.monitor

import androidx.room.*
import com.mirea.tuguzt.pcbuilder.domain.model.monitor.*
import com.mirea.tuguzt.pcbuilder.domain.model.units.Frequency
import com.mirea.tuguzt.pcbuilder.domain.model.units.Luminance
import com.mirea.tuguzt.pcbuilder.presentation.repository.converters.*
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentChild
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO
import io.nacular.measured.units.Distance
import io.nacular.measured.units.Measure
import io.nacular.measured.units.Time

/**
 * Data Transfer Object for [Monitor].
 *
 * @see Monitor
 */
@Entity(
    tableName = "monitor",
    foreignKeys = [ForeignKey(
        entity = ComponentDTO::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE,
    )],
)
@TypeConverters(
    DistanceConverter::class,
    FrequencyConverter::class,
    TimeConverter::class,
    LuminanceConverter::class,
    MonitorPWMTypeConverter::class,
)
data class MonitorDTO(
    val id: Long,
    @ColumnInfo(name = "screen_size") override val screenSize: Measure<Distance>,
    @Embedded(prefix = "resolution_") override val resolution: MonitorResolution,
    @ColumnInfo(name = "refresh_rate") override val refreshRate: Measure<Frequency>,
    @ColumnInfo(name = "response_time") override val responseTime: Measure<Time>,
    @Embedded(prefix = "aspect_ratio_") override val aspectRatio: AspectRatio,
    @ColumnInfo(name = "panel_type") override val panelType: PanelType,
    override val luminance: Measure<Luminance>,
    @ColumnInfo(name = "screen_type") override val screenType: ScreenType,
    @Embedded(prefix = "contrast_ratio_") override val contrastRatio: ContrastRatio,
    @ColumnInfo(name = "monitor_interface") override val monitorInterface: MonitorInterface,
    @ColumnInfo(name = "pwm") override val pwmType: MonitorPWMType,
    @ColumnInfo(name = "frame_sync_type") override val frameSyncType: FrameSyncType?,
) : Monitor, ComponentChild
