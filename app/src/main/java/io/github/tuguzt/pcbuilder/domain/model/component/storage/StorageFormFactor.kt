package io.github.tuguzt.pcbuilder.domain.model.component.storage

/**
 * Enum represents different form factor types of storage.
 *
 * @see Storage
 */
enum class StorageFormFactor {
    /** 1.8" */
    INCH_1_8,

    /** 2.5" (U.2) */
    INCH_2_5,

    /** 3.5" */
    INCH_3_5,

    /** M.2, formerly Next Generation Form Factor (NGFF) */
    M_2,

    /**
     * mSATA (Mini-SATA)
     *
     * @see StorageInterface.SATA
     */
    M_SATA,
}
