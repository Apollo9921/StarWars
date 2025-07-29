package com.example.starwars.utils.size

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Utility object for handling screen size calculations in Jetpack Compose.
 *
 * This object provides functions to retrieve screen dimensions (width and height) in Dp
 * and to calculate custom dimensions based on the screen size and orientation.
 */
object ScreenSizeUtils {

    /**
     * Retrieves the current screen width in Dp.
     *
     * This composable function accesses the `LocalConfiguration` to get the
     * `screenWidthDp` value.
     *
     * @return The screen width in Dp.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    @ReadOnlyComposable
    fun getScreenWidthDp(): Dp {
        val configuration = LocalConfiguration.current
        return configuration.screenWidthDp.dp
    }

    /**
     * Retrieves the current screen height in Dp.
     *
     * This composable function accesses the `LocalConfiguration` to get the
     * `screenHeightDp` value.
     *
     * @return The screen height in Dp.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    @ReadOnlyComposable
    fun getScreenHeightDp(): Dp {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp.dp
    }

    /**
     * Calculates a custom width based on the screen width.
     *
     * This function adjusts a `baseSize` according to predefined screen width breakpoints:
     * - If screen width is less than 600.dp, the `baseSize` is returned.
     * - If screen width is between 600.dp and 840.dp, the `baseSize` is multiplied by 1.5.
     * - Otherwise (screen width >= 840.dp), the `baseSize` is multiplied by 2.
     *
     * @param baseSize The initial size to be adjusted.
     * @return The calculated custom width as an Int.
     */
    @Composable
    fun calculateCustomWidth(baseSize: Int): Int {
        val screenWidth = getScreenWidthDp()
        val smallWidth = 600.dp
        val mediumWidth = 840.dp
        return when {
            screenWidth < smallWidth -> {
                baseSize
            }
            screenWidth < mediumWidth -> {
                (baseSize * 1.5).toInt()
            }
            else -> {
                (baseSize * 2)
            }
        }
    }

    /**
     * Calculates a custom height based on the screen height and orientation.
     *
     * This function adjusts a `baseSize` differently for portrait and landscape orientations,
     * using predefined screen height breakpoints.
     *
     * **In Landscape mode:**
     * - If screen height is less than 950.dp, the `baseSize` is multiplied by 0.2.
     * - If screen height is between 950.dp and 1400.dp, the `baseSize` is multiplied by 0.5.
     * - Otherwise (screen height >= 1400.dp), the `baseSize` is returned.
     *
     * **In Portrait mode:**
     * - If screen height is less than 950.dp, the `baseSize` is returned.
     * - If screen height is between 950.dp and 1400.dp, the `baseSize` is multiplied by 0.5.
     * - Otherwise (screen height >= 1400.dp), the `baseSize` is multiplied by 0.2.
     *
     * @param baseSize The initial size to be adjusted.
     * @return The calculated custom height as an Int.
     */
    @Composable
    fun calculateCustomHeight(baseSize: Int): Int {
        val screenHeight = getScreenHeightDp()

        val orientation = LocalConfiguration.current.orientation
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT

        if (!isPortrait) {
            val smallHeight = 950.dp
            val mediumHeight = 1400.dp
            return when {
                screenHeight < smallHeight -> {
                    (baseSize * 0.2).toInt()
                }

                screenHeight < mediumHeight -> {
                    (baseSize * 0.5).toInt()
                }

                else -> {
                    baseSize
                }
            }
        } else {
            val smallHeight = 950.dp
            val mediumHeight = 1400.dp
            return when {
                screenHeight < smallHeight -> {
                    baseSize
                }
                screenHeight < mediumHeight -> {
                    (baseSize * 0.5).toInt()
                }
                else -> {
                    (baseSize * 0.2).toInt()
                }
            }
        }
    }
}