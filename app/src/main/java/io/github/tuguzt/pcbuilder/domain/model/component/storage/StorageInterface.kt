package io.github.tuguzt.pcbuilder.domain.model.component.storage

/**
 * Enum represents different interface types of storage.
 *
 * @see Storage
 */
enum class StorageInterface {
    /** Serial Attached SCSI (Small Computer System Interface) */
    SAS,

    /**
     * Serial ATA
     *
     * @see PATA
     */
    SATA,

    /**
     * Micro SATA
     *
     * @see SATA
     */
    MicroSATA,

    /**
     * Parallel AT Attachment
     *
     * Before the existence of SATA, it was called ATA.
     *
     * @see SATA
     */
    PATA,

    /** Peripheral Component Interconnect Express */
    PCIe,
}
