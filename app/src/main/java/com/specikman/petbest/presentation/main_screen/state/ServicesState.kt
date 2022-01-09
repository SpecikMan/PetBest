package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.domain.model.Service

data class ServicesState(
    val isLoading: Boolean = false,
    var services: List<Service> = emptyList(),
    val error: String = ""
)