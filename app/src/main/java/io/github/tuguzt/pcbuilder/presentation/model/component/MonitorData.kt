package io.github.tuguzt.pcbuilder.presentation.model.component

import android.os.Parcelable
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.model.component.parceler.MassParceler
import io.github.tuguzt.pcbuilder.presentation.model.component.parceler.SizeParceler
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.monitor.MonitorComponent
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

/**
 * Data class for monitor data used in application.
 *
 * @see Monitor
 */
@Parcelize
data class MonitorData(
    override val id: String,
    override val name: String,
    override val description: String,
    override val weight: @WriteWith<MassParceler> Measure<Mass>,
    override val size: @WriteWith<SizeParceler> Size,
    val imageUri: String?,
) : Monitor, Parcelable {
    constructor(m: Monitor) : this(m.id, m.name, m.description, m.weight, m.size, null)
    constructor(m: MonitorComponent) : this(m.id, m.name, m.description, m.weight, m.size, m.imageUri)
}
