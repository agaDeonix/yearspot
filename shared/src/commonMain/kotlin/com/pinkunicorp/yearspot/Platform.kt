package com.pinkunicorp.yearspot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform