package com.nithin.cointracker.coins.di

import com.nithin.cointracker.coins.data.impl.CoinRemoteDataSourceImpl
import com.nithin.cointracker.coins.domain.GetCoinsListUseCase
import com.nithin.cointracker.coins.domain.GetPriceHistoryUseCase
import com.nithin.cointracker.coins.domain.api.CoinRemoteDataSource
import com.nithin.cointracker.coins.presentation.CoinsListViewModel
import com.nithin.cointracker.shared.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(platformModule,sharedModule)
    }

}

expect val platformModule: Module

val sharedModule = module {

    single<HttpClient> { HttpClientFactory.create(get()) }

    single<GetCoinsListUseCase> { GetCoinsListUseCase(get()) }
    single<GetPriceHistoryUseCase> { GetPriceHistoryUseCase(get()) }
    single<CoinRemoteDataSource> { CoinRemoteDataSourceImpl(get()) }

    viewModelOf(::CoinsListViewModel)

}