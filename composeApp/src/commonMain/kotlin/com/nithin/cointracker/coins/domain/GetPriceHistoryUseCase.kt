package com.nithin.cointracker.coins.domain

import com.nithin.cointracker.coins.data.dto.CoinPriceHistoryResponseDto
import com.nithin.cointracker.coins.data.mappers.toPriceModel
import com.nithin.cointracker.coins.domain.api.CoinRemoteDataSource
import com.nithin.cointracker.coins.domain.data.PriceModel
import com.nithin.cointracker.shared.domain.DataError
import com.nithin.cointracker.shared.domain.Result
import com.nithin.cointracker.shared.domain.map

class GetPriceHistoryUseCase(
    val client : CoinRemoteDataSource
) {

    suspend fun execute(coinId : String) : Result<List<PriceModel>, DataError.Remote>{
        return client.getPriceHistory(coinId).map { dto ->
            println(dto)
            dto.data.history.map { priceDto -> priceDto.toPriceModel() }
        }
    }
}