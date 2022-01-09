package com.specikman.petbest.domain.model

data class User(
    val uid : String = "",
    var name: String = "",
    val email : String = "",
    var phone: String = "",
    var creditCard: String = "",
    val balance: Long = 0
)