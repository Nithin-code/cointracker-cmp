package com.nithin.cointracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform