package com.specikman.petbest.data.repository

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.model.History
import com.specikman.petbest.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: FirebaseAPI
): HistoryRepository {
    override suspend fun getHistory(): List<History> {
        return api.getHistory()
    }

    override suspend fun addHistory(history: History) {
        api.addHistory(history = history)
    }
}