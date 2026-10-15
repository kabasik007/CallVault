/*
 * CallVault development fork security baseline.
 * GPL-3.0-or-later, with additional Section 7 terms; see LICENSE.
 */
package com.baba.callvault.server

/**
 * Only a genuine privileged recorder daemon (shell), Android's system process,
 * or our own UID may deliver a recorder Binder to our exported provider.
 *
 * Pure Kotlin on purpose so this trust boundary is easy to unit test.
 */
internal object RecorderBinderCallerPolicy {
    private const val SYSTEM_UID = 1000
    private const val SHELL_UID = 2000

    fun isTrusted(callingUid: Int, appUid: Int): Boolean =
        callingUid >= 0 && (callingUid == appUid ||
            callingUid == SHELL_UID || callingUid == SYSTEM_UID)
}
