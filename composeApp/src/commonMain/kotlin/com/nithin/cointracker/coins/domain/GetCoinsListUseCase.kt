package com.nithin.cointracker.coins.domain

import com.nithin.cointracker.coins.data.mappers.toCoinModel
import com.nithin.cointracker.coins.domain.api.CoinRemoteDataSource
import com.nithin.cointracker.coins.domain.data.CoinModel
import com.nithin.cointracker.shared.domain.DataError
import com.nithin.cointracker.shared.domain.Result
import com.nithin.cointracker.shared.domain.map

class GetCoinsListUseCase(
    private val client : CoinRemoteDataSource
) {

    suspend fun execute() : Result<List<CoinModel>, DataError.Remote>{
        return client.getListOfCoins().map { dto ->
            dto.data.coins.map { it.toCoinModel()}
        }
    }

}