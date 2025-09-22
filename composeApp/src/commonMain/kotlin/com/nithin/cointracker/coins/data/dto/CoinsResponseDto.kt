package com.nithin.cointracker.coins.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponseDto(
    val data : CoinListsDto
)

@Serializable
data class CoinListsDto(
    val coins : List<CoinItemDto>
)

@Serializable
data class CoinItemDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val marketCap: String,
    val price: Double,
    val change: Double,
    val rank : Int
)