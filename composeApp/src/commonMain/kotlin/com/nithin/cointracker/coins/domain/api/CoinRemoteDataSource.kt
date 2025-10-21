package com.nithin.cointracker.coins.domain.api

import com.nithin.cointracker.coins.data.dto.CoinItemDto
import com.nithin.cointracker.coins.data.dto.CoinPriceHistoryResponseDto
import com.nithin.cointracker.coins.data.dto.CoinsResponseDto
import com.nithin.cointracker.coins.domain.data.CoinModel
import com.nithin.cointracker.shared.domain.DataError
import com.nithin.cointracker.shared.domain.Result

interface CoinRemoteDataSource {

    suspend fun getListOfCoins() : Result<CoinsResponseDto, DataError.Remote>

    suspend fun getPriceHistory(coinId : String) : Result<CoinPriceHistoryResponseDto, DataError.Remote>

    suspend fun getCoin(coinId : String) : Result<CoinItemDto, DataError.Remote>

}