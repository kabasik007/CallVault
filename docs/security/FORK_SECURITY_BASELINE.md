# Development fork: Phase 1 security baseline

Owner: `kabasik007/CallVault`. Upstream: `madkongo/CallVault`.
Baseline: upstream v2.4.6, commit `3b72f7481bc8dc9ada150007f83b39f0dbc16590`.

This is a **development-only** baseline, not a complete security certification.
Source code review is not equivalent to verification of a downloaded APK.

## Addressed in this change

1. **Untrusted Binder injection:** `RecorderBinderProvider.call("sendBinder")`
   and `sendHandoff` now require a trusted Android UID before unpacking
   a received Binder. The trust policy is JVM-unit-tested.
2. **Local copy catalog correctness:** both immediate and scheduled SAF cloud
   sync retain the local catalog reference if the provider refuses to delete
   a device copy in cloud-only mode.
3. **Foreign APK update channel:** the fork cannot check for or install
   upstream in-app updates. The default setting is off and
   `UpdateManager` additionally fails closed for direct callers.
4. **Future release links:** GitHub release URLs point to the owner's fork.
   They do **not** enable upgrades while `ForkUpdatePolicy.ENABLED=false`.

## Remaining work before installing on a primary phone

- Test IPC denial with a separate Android app on the same device.
- Evaluate removing the system UID exception if it is not needed on devices.
- Audit all native sources/submodules and build-time downloads.
- Review the off-Wi-Fi classic ADB TCP feature: while an app can connect to
  127.0.0.1, `adbd` may listen on all network interfaces. Keep the feature
  disabled until the OEM network exposure has been measured.
- Add a fork-specific package ID, visible attribution, product branding,
  and dedicated signing certificate without breaking ADB/binder authorities.
- Build, inspect and test our own APK on Android 12 and newer. Do not
  assume WhatsApp/Telegram/Viber recording works across all OEMs.
- Harden cloud backup: keep local copies; add encryption before upload and
  verification before deleting the only local original.
- Audit private metadata in exported diagnostic logs.

## Gate for enabling updates

Do not flip `ForkUpdatePolicy.ENABLED` until the fork owns its signing key,
`ApkVerifier.PINNED_CERT_SHA256` is updated to the **fork** certificate,
an independent GitHub Actions release is built, and a full installation/
upgrade test has passed. Never copy the upstream author's private key.

## Validation

- `./gradlew :app:testDebugUnitTest`
- `./gradlew :app:assembleDebug`
- GitHub CodeQL and dependency review on the pull request
- Instrumented test: untrusted separate UID cannot send `sendBinder`
- Confirm update scheduler stays disabled and a user cannot install an
  upstream APK through CallVault's updater.

No automated Gradle run or device-level check is implied by this document.
