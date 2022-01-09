package com.specikman.petbest.domain.model

data class Cart(
    var id: Int = 0,
    val productId: Int = 0,
    var amount: Int = 0,
    var userUID: String = "",
    val costEach: Long = 0,
    var costTotal: Long = 0
)