package com.nithin.cointracker

import androidx.compose.ui.window.ComposeUIViewController
import com.nithin.cointracker.coins.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}