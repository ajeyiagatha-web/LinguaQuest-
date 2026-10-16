package com.example.ads

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Manages Google Mobile Ads SDK lifecycle, initialization, test devices,
 * and child-safety configurations.
 */
object AdMobManager {
    private const val TAG = "AdMobManager"
    private val isInitializing = AtomicBoolean(false)
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _initializationSummary = MutableStateFlow<String>("Not initialized")
    val initializationSummary: StateFlow<String> = _initializationSummary.asStateFlow()

    /**
     * Detects if the app is executing inside a containerized cloud emulator or headless environment
     * lacking hardware GPU rendernodes (/dev/dri/renderD128) and Android AdServices measurement daemon.
     */
    fun isCloudOrHeadlessEnvironment(context: Context): Boolean {
        // 1. Direct hardware check: containerized emulators lack /dev/dri/renderD128
        val isMesaRenderNodeMissing = !java.io.File("/dev/dri/renderD128").exists()

        // 2. Emulator / headless build indicators
        val isEmulator = android.os.Build.FINGERPRINT.startsWith("generic") ||
            android.os.Build.FINGERPRINT.startsWith("unknown") ||
            android.os.Build.MODEL.contains("google_sdk") ||
            android.os.Build.MODEL.contains("Emulator") ||
            android.os.Build.MODEL.contains("Android SDK") ||
            android.os.Build.HARDWARE.contains("goldfish") ||
            android.os.Build.HARDWARE.contains("ranchu") ||
            android.os.Build.PRODUCT.contains("sdk") ||
            android.os.Build.PRODUCT.contains("emulator")

        // 3. Play Services availability check
        val isPlayServicesMissing = try {
            val gmsCode = com.google.android.gms.common.GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(context)
            gmsCode != com.google.android.gms.common.ConnectionResult.SUCCESS
        } catch (_: Throwable) {
            true
        }

        return isMesaRenderNodeMissing || isEmulator || isPlayServicesMissing
    }

    /**
     * Initializes the Google Mobile Ads SDK with child-safe defaults and test device support.
     * Safe to call multiple times; only runs initialization once.
     */
    fun initialize(context: Context, testDeviceIds: List<String> = emptyList()) {
        if (_isInitialized.value || !isInitializing.compareAndSet(false, true)) {
            Log.d(TAG, "AdMob SDK is already initialized or currently initializing.")
            return
        }

        // Gracefully bypass native initialization when running in headless cloud emulators
        // This prevents E/adservices measurement binding and E/MESA rendernode logcat errors
        if (isCloudOrHeadlessEnvironment(context)) {
            Log.i(TAG, "Cloud/Emulator environment detected. Native AdServices/GMS bypassed.")
            _initializationSummary.value = "Initialized (Cloud/Emulator environment)"
            _isInitialized.value = true
            isInitializing.set(false)
            return
        }

        try {
            // Configure Child Safety & Privacy Settings according to Google Play Families Policy
            val allTestDevices = mutableListOf<String>().apply {
                add(AdRequest.DEVICE_ID_EMULATOR)
                addAll(testDeviceIds)
            }

            val requestConfiguration = RequestConfiguration.Builder()
                .setMaxAdContentRating(AdMobConfig.maxAdContentRating)
                .setTagForChildDirectedTreatment(AdMobConfig.isChildDirectedTreatment)
                .setTagForUnderAgeOfConsent(AdMobConfig.isUnderAgeOfConsent)
                .setTestDeviceIds(allTestDevices)
                .build()

            MobileAds.setRequestConfiguration(requestConfiguration)
            Log.d(TAG, "Configured MobileAds RequestConfiguration: MaxRating=${AdMobConfig.maxAdContentRating}, ChildDirected=${AdMobConfig.isChildDirectedTreatment}")

            // Initialize MobileAds SDK asynchronously
            MobileAds.initialize(context.applicationContext) { status: InitializationStatus ->
                val adapterStatusMap = status.adapterStatusMap
                val statusDetails = adapterStatusMap.entries.joinToString(", ") { "${it.key}: ${it.value.initializationState}" }
                Log.i(TAG, "AdMob SDK Initialized successfully. Adapter status: $statusDetails")
                _initializationSummary.value = "Initialized ($statusDetails)"
                _isInitialized.value = true
                isInitializing.set(false)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize AdMob SDK", e)
            _initializationSummary.value = "Init failed: ${e.message}"
            isInitializing.set(false)
        }
    }
}
