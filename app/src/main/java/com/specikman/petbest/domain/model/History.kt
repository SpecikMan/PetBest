package com.specikman.petbest.domain.model

import java.util.*

data class History(
    val uid: String = "",
    val productId: Int = 0,
    val date: Date = Calendar.getInstance().time
)