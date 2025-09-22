package com.nithin.cointracker.coins.data.mappers

import com.nithin.cointracker.coins.data.dto.CoinItemDto
import com.nithin.cointracker.coins.domain.data.CoinModel
import com.nithin.cointracker.shared.domain.Coin

fun CoinItemDto.toCoinModel() : CoinModel {
    return CoinModel(
        coin = Coin(
            id = uuid,
            name = name,
            symbol = symbol,
            iconUrl = iconUrl
        ),
        price = price,
        change = change
    )
}