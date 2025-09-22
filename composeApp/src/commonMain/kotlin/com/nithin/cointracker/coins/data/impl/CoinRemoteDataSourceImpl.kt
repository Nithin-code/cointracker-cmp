package com.nithin.cointracker.coins.data.impl

import com.nithin.cointracker.coins.data.dto.CoinPriceHistoryResponseDto
import com.nithin.cointracker.coins.data.dto.CoinsResponseDto
import com.nithin.cointracker.coins.domain.api.CoinRemoteDataSource
import com.nithin.cointracker.shared.domain.DataError
import com.nithin.cointracker.shared.domain.Result
import com.nithin.cointracker.shared.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get


private const val BASE_URL = "https://api.coinranking.com/v2"

class CoinRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CoinRemoteDataSource {

    override suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coins")
        }
    }

    override suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(BASE_URL)
        }
    }

}