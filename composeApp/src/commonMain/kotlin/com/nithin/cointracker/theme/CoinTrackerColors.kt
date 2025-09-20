package com.nithin.cointracker.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CoinTrackerColorPalette(
    val profitGreen : Color = Color.Unspecified,
    val lossRed : Color = Color.Unspecified,
)

val LightProfitGreen = Color(0xFF32de84)
val LightLossRed = Color(0xFFD2122E)

val DarkProfitGreen = Color(0xFF32de84)
val DarkLossRed = Color(0xFFD2122E)

val LightThemeColorPalette = CoinTrackerColorPalette(
    profitGreen = LightProfitGreen,
    lossRed = LightLossRed
)

val DarkThemeColorPalette = CoinTrackerColorPalette(
    profitGreen = DarkProfitGreen,
    lossRed = DarkLossRed
)

val LocalCoinTrackerColorPalette = compositionLocalOf { CoinTrackerColorPalette() }