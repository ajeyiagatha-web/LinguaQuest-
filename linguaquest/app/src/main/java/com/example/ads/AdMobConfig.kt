package com.example.ads

import android.os.Bundle
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.RequestConfiguration

/**
 * =========================================================================================
 * LINGUAQUEST - CENTRAL ADMOB CONFIGURATION
 * =========================================================================================
 * 
 * This is the SINGLE CENTRAL LOCATION where all Google AdMob identifiers, test IDs,
 * production replacements, and child safety / privacy compliance policies are defined.
 * 
 * NEVER hardcode Ad Unit IDs across different Composable files.
 * 
 * -----------------------------------------------------------------------------------------
 * HOW TO SWITCH FROM TEST ADS TO PRODUCTION ADS:
 * -----------------------------------------------------------------------------------------
 * 1. Set [USE_TEST_ADS] = false (or keep BuildConfig.DEBUG check).
 * 2. In AndroidManifest.xml, replace the test Application ID with [PRODUCTION_APP_ID].
 * 3. Replace [PRODUCTION_BANNER_AD_UNIT_ID] with your actual Banner Ad Unit ID created in
 *    the Google AdMob console (https://admob.google.com).
 * 4. (Optional) Replace Interstitial, Rewarded, or Native Ad Unit IDs as you expand.
 * =========================================================================================
 */
object AdMobConfig {

    /**
     * TOGGLE: Set to FALSE only when releasing production builds to the Google Play Store.
     * Keep TRUE during development, testing, and CI builds to prevent invalid traffic policy violations.
     */
    const val USE_TEST_ADS = true

    // =====================================================================================
    // 1. APPLICATION IDENTIFIERS
    // =====================================================================================
    /**
     * Official Google AdMob Sample/Test Application ID.
     * Guaranteed to return test responses without generating policy penalties.
     */
    const val TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713"

    /**
     * >>> PRODUCTION ADMOB APPLICATION ID <<<
     * Insert your real AdMob Application ID here once created in the AdMob Console.
     * Remember to also update the <meta-data android:name="com.google.android.gms.ads.APPLICATION_ID">
     * in app/src/main/AndroidManifest.xml.
     */
    const val PRODUCTION_APP_ID = "ca-app-pub-XXXXXXXXXXXXXXXX~XXXXXXXXXX"

    // =====================================================================================
    // 2. BANNER AD UNIT IDENTIFIERS
    // =====================================================================================
    /**
     * Official Google AdMob Sample/Test Banner Ad Unit ID.
     * Formats: 320x50 standard banner, smart banner, or anchored adaptive banner.
     */
    const val TEST_BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

    /**
     * >>> PRODUCTION BANNER AD UNIT ID <<<
     * Insert your real Banner Ad Unit ID here from the AdMob Console (App > Ad units > Add ad unit > Banner).
     */
    const val PRODUCTION_BANNER_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX"

    /**
     * Active Banner Ad Unit ID resolved based on [USE_TEST_ADS].
     */
    val bannerAdUnitId: String
        get() = if (USE_TEST_ADS) TEST_BANNER_AD_UNIT_ID else PRODUCTION_BANNER_AD_UNIT_ID

    // =====================================================================================
    // 3. FUTURE EXPANSION AD UNIT IDENTIFIERS (INTERSTITIAL, REWARDED, NATIVE)
    // =====================================================================================
    /**
     * Official Google AdMob Sample/Test Interstitial Ad Unit ID.
     */
    const val TEST_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val PRODUCTION_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX"

    val interstitialAdUnitId: String
        get() = if (USE_TEST_ADS) TEST_INTERSTITIAL_AD_UNIT_ID else PRODUCTION_INTERSTITIAL_AD_UNIT_ID

    /**
     * Official Google AdMob Sample/Test Rewarded Ad Unit ID (e.g. for unlocking bonus gems/hearts).
     */
    const val TEST_REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
    const val PRODUCTION_REWARDED_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX"

    val rewardedAdUnitId: String
        get() = if (USE_TEST_ADS) TEST_REWARDED_AD_UNIT_ID else PRODUCTION_REWARDED_AD_UNIT_ID

    /**
     * Official Google AdMob Sample/Test Native Advanced Ad Unit ID.
     */
    const val TEST_NATIVE_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"
    const val PRODUCTION_NATIVE_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX"

    val nativeAdUnitId: String
        get() = if (USE_TEST_ADS) TEST_NATIVE_AD_UNIT_ID else PRODUCTION_NATIVE_AD_UNIT_ID

    // =====================================================================================
    // 4. CHILD SAFETY, PRIVACY & GOOGLE PLAY FAMILIES POLICY COMPLIANCE
    // =====================================================================================
    /**
     * LinguaQuest is built for learners of all ages, including children.
     * Google Play Families Policy and COPPA require:
     *  - Max Ad Content Rating set to G (General Audiences).
     *  - Child-directed treatment tag enabled or dynamically set.
     *  - Under Age of Consent tag set.
     *  - No personalized tracking/profiling for children (npa = "1").
     */
    val maxAdContentRating: String = RequestConfiguration.MAX_AD_CONTENT_RATING_G
    const val isChildDirectedTreatment: Int = RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE
    const val isUnderAgeOfConsent: Int = RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE

    /**
     * Builds a compliant [AdRequest] adhering to child safety and non-personalized advertising rules.
     * Adds npa="1" (Non-Personalized Ads) to extras when personalized advertising is restricted.
     */
    fun buildCompliantAdRequest(nonPersonalizedOnly: Boolean = true): AdRequest {
        val builder = AdRequest.Builder()
        if (nonPersonalizedOnly) {
            val extras = Bundle().apply {
                putString("npa", "1") // 1 = Non-Personalized Advertising
            }
            builder.addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        }
        return builder.build()
    }
}
