package com.nithin.cointracker.coins.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoinPriceHistoryResponseDto(
    val data : CoinPriceHistoryDto
)


@Serializable
data class CoinPriceHistoryDto(
    val historyDto: List<CoinPriceDto>
)


@Serializable
data class CoinPriceDto(
    val price : Double?,
    val timeStamp : Long
)