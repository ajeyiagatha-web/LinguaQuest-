package com.example.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.ads.AdMobConfig
import com.example.ads.AdMobManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

private const val TAG = "AdMobBanner"

/**
 * State representing the lifecycle of an individual banner ad request.
 */
sealed interface AdBannerState {
    data object Loading : AdBannerState
    data object Loaded : AdBannerState
    data class Failed(val errorCode: Int, val message: String) : AdBannerState
}

/**
 * Reusable Google AdMob Banner Ad component for Jetpack Compose.
 *
 * Key features:
 * - Automatically initializes AdMob SDK on first render if not already initialized.
 * - Handles Android Lifecycle events (onResume, onPause, onDestroy) safely.
 * - Adheres to Google Play Families Policy (child-safe request parameters, G-rated ads).
 * - Graceful failure handling: collapses to 0 height when an ad fails to load or is empty,
 *   preventing broken layouts or awkward blank gaps.
 * - Compliant "Ad" indicator badge to avoid accidental clicks.
 * - Never disrupts interactive exercises, active quizzes, or speech sessions.
 *
 * @param modifier Composable modifier for styling and layout constraints.
 * @param adUnitId The AdMob Banner Ad Unit ID (defaults to [AdMobConfig.bannerAdUnitId]).
 * @param showLabel Whether to display a discreet "Ad" indicator badge.
 * @param onAdLoaded Callback invoked when the ad is loaded successfully.
 * @param onAdFailedToLoad Callback invoked when the ad fails to load.
 */
@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = AdMobConfig.bannerAdUnitId,
    showLabel: Boolean = true,
    onAdLoaded: () -> Unit = {},
    onAdFailedToLoad: (errorCode: Int, errorMessage: String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isInspection = LocalInspectionMode.current

    var bannerState by remember { mutableStateOf<AdBannerState>(AdBannerState.Loading) }

    val isHeadless = remember(context) { AdMobManager.isCloudOrHeadlessEnvironment(context) }

    // In Compose preview / inspection mode or containerized cloud emulator, render clean preview placeholder
    // This avoids triggering native Chromium EGL / Mesa rendernode queries and AdServices measurement binding
    if (isInspection || isHeadless) {
        AdPreviewPlaceholder(modifier = modifier)
        return
    }

    // Ensure AdMob SDK is initialized with family-safe configuration on physical devices
    DisposableEffect(Unit) {
        AdMobManager.initialize(context)
        onDispose { }
    }

    // Wrap the AdView instance with remember to manage its lifecycle
    val adView = remember(adUnitId) {
        AdView(context).apply {
            // Use software rendering layer to avoid MESA rendernode access failures on containerized emulators
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
            setAdSize(AdSize.BANNER)
            this.adUnitId = adUnitId
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    Log.d(TAG, "Banner ad loaded successfully ($adUnitId)")
                    bannerState = AdBannerState.Loaded
                    onAdLoaded()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    val code = loadAdError.code
                    val message = loadAdError.message
                    Log.w(TAG, "Banner ad failed to load ($code): $message")
                    bannerState = AdBannerState.Failed(code, message)
                    onAdFailedToLoad(code, message)
                }

                override fun onAdOpened() {
                    Log.d(TAG, "Banner ad opened overlay")
                }

                override fun onAdClosed() {
                    Log.d(TAG, "Banner ad closed overlay")
                }

                override fun onAdClicked() {
                    Log.d(TAG, "Banner ad clicked")
                }
            }

            // Request child-compliant ad
            val adRequest = AdMobConfig.buildCompliantAdRequest(nonPersonalizedOnly = true)
            loadAd(adRequest)
        }
    }

    // Observe host lifecycle events to pause/resume/destroy the AdView
    DisposableEffect(lifecycleOwner, adView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> adView.resume()
                Lifecycle.Event.ON_PAUSE -> adView.pause()
                Lifecycle.Event.ON_DESTROY -> adView.destroy()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            adView.destroy()
        }
    }

    // When ad fails to load, collapse gracefully (AnimatedVisibility = false) so UI doesn't break
    AnimatedVisibility(
        visible = bannerState is AdBannerState.Loaded,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 4.dp)
                .testTag("admob_banner_container"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showLabel) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "SPONSORED",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )
                        )
                    }
                }
            }

            // Embedded AndroidView hosting the Google Mobile Ads AdView
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White)
                    .padding(2.dp)
            ) {
                AndroidView(
                    factory = { adView },
                    modifier = Modifier.testTag("admob_banner_adview")
                )
            }
        }
    }
}

/**
 * Visual placeholder displayed during IDE preview and layout inspection or in cloud emulators.
 */
@Composable
private fun AdPreviewPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "AD",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                Text(
                    text = "Test AdMob Banner (320x50)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Text(
                text = "Free Mode",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            )
        }
    }
}
