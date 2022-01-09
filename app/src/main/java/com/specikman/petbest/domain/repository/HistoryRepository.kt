package com.specikman.petbest.domain.repository

import com.specikman.petbest.domain.model.History

interface HistoryRepository {
    suspend fun getHistory(): List<History>

    suspend fun addHistory(history: History)
}