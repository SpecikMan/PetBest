package com.specikman.petbest.domain.model

data class Product(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val category: String = "",
    val price: Long = 0L,
    val stock: Int = 0,
    val bought: Int = 0,
    val discount: Int = 0,
    val description: String = "",
    val benefit: String = "",
    val how_to_use: String = ""
)
