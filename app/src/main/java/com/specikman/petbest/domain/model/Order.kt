package com.specikman.petbest.domain.model

data class Order(
    var id: Int = 0,
    val productId: Int = 0,
    val amount: Int = 0,
    val userUID: String = "",
    val costEach: Long = 0,
    val costTotal: Long = 0,
    val type: String = "Mua hàng"
)