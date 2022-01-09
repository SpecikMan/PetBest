package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.domain.model.History

data class HistoryState(
    val isLoading: Boolean = false,
    val history: List<History> = emptyList(),
    val error: String = ""
)