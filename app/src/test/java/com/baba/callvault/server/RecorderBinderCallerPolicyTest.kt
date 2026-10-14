package com.baba.callvault.server

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RecorderBinderCallerPolicyTest {
    private val appUid = 10654

    @Test
    fun shellSystemAndOwnAppAreAllowed() {
        assertTrue(RecorderBinderCallerPolicy.isTrusted(2000, appUid))
        assertTrue(RecorderBinderCallerPolicy.isTrusted(1000, appUid))
        assertTrue(RecorderBinderCallerPolicy.isTrusted(appUid, appUid))
    }

    @Test
    fun OtherAppsAndUnassignedIdsAreRejected() {
        assertFalse(RecorderBinderCallerPolicy.isTrusted(10655, appUid))
        assertFalse(RecorderBinderCallerPolicy.isTrusted(10000, appUid))
        assertFalse(RecorderBinderCallerPolicy.isTrusted(0, appUid))
        assertFalse(RecorderBinderCallerPolicy.isTrusted(-1, appUid))
    }
}
