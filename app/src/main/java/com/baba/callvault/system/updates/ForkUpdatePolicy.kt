/*
 * CallVault development fork security baseline.
 * GPL-3.0-or-later, with additional Section 7 terms; see LICENSE.
 */
package com.baba.callvault.system.updates

/**
 * The fork has no independently signed and verified release yet.
 *
 * DO NOT enable until all of these are true:
 * 1. We own and securely store a stable release-signing key.
 * 2. ApkVerifier.PINNED_CERT_SHA256 is changed to that key's certificate hash.
 * 3. GitHubReleases points only at the fork's controlled release channel.
 * 4. We verify the release pipeline and perform an on-device upgrade test.
 *
 * Keep hard-coded to false rather than tying it to an untrusted runtime setting.
 */
internal object ForkUpdatePolicy {
    const val ENABLED: Boolean = false
}
