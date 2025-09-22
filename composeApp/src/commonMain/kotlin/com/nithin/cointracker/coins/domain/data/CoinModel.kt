package com.nithin.cointracker.coins.domain.data

import com.nithin.cointracker.shared.domain.Coin

data class CoinModel(
    val coin : Coin,
    val price : Double,
    val change : Double
)