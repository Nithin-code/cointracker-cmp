package com.nithin.cointracker.coins.domain

import com.nithin.cointracker.coins.data.mappers.toCoinModel
import com.nithin.cointracker.coins.domain.api.CoinRemoteDataSource
import com.nithin.cointracker.coins.domain.data.CoinModel
import com.nithin.cointracker.shared.domain.DataError
import com.nithin.cointracker.shared.domain.Result
import com.nithin.cointracker.shared.domain.map

class GetCoinDetailsUseCase(
    private val client : CoinRemoteDataSource
) {

    suspend fun execute (coinId : String) : Result<CoinModel, DataError.Remote>{
        return client.getCoin(coinId).map { coinItemDto ->
            coinItemDto.toCoinModel()
        }
    }

}