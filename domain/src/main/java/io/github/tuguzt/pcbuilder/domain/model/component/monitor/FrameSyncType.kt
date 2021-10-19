package io.github.tuguzt.pcbuilder.domain.model.component.monitor

/**
 * Enum represents frame synchronization technology supported by the monitor.
 *
 * @see Monitor
 */
enum class FrameSyncType {
    /**
     * Announced by `AMD` to compete against `Nvidia`'s proprietary `G-Sync`.
     *
     * @see GSync
     */
    FreeSync,

    /**
     * `FreeSync Premium` mandates further requirements of
     * `Low Frame rate Compensation` `(LFC)` and at least 120 Hz refresh rate at FHD resolution.
     *
     * @see FreeSync
     */
    FreeSyncPremium,

    /**
     * `FreeSync Premium Pro` adds luminance and wide color gamut requirements.
     *
     * @see FreeSync
     */
    FreeSyncPremiumPro,

    /**
     * `G-Sync` is a proprietary adaptive sync technology developed by `Nvidia`.
     */
    GSync,

    /**
     * `G-Sync Ultimate` targets monitors with `High Dynamic Range` `(HDR)` content.
     *
     * @see GSync
     */
    GSyncUltimate,

    /**
     * `G-Sync Compatible` means it was tested by `Nvidia`
     * to work as `G-Sync` with `FreeSync` monitors.
     *
     * @see GSync
     * @see FreeSync
     */
    GSyncCompatible,
}
