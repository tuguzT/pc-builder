package io.github.tuguzt.pcbuilder.domain.model.component.storage

/**
 * Sealed class represents all possible types of storages.
 *
 * @see Storage
 */
sealed class StorageType {
    /** Hard Disk Drive */
    data class HDD(val revolutionsPerMinute: UInt) : StorageType() {
        init {
            require(revolutionsPerMinute > 0u) { "RPM of HDD must be greater than 0" }
        }
    }

    /** Solid-State Drive */
    class SSD(val hasNVMe: Boolean) : StorageType()

    /**
     * Dual-drive hybrid systems or solid-state hybrid drives (SSHD)
     *
     * @see HDD
     * @see SSD
     */
    object Hybrid : StorageType()
}
