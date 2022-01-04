package com.specikman.petbest.common

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.specikman.petbest.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

class UpdateFirestore {
    fun getMap(old: Product): MutableMap<String, Any> = mutableMapOf<String, Any>().apply {
        this["id"] = old.id
        this["name"] = old.name
        this["image"] = old.image
        this["category"] = old.category
        this["price"] = old.price
        this["stock"] = old.stock
        this["bought"] = Random.nextInt(0, 200)
    }

    val ref = Firebase.firestore.collection("products")
    fun update(product: Product, map: Map<String, Any>) = CoroutineScope(Dispatchers.IO).launch {
        val query = ref.whereEqualTo("id", product.id).get().await()
        if (query.documents.isNotEmpty()) {
            for (document in query) {
                ref.document(document.id).set(
                    map,
                    SetOptions.merge()
                ).await()
            }
        }
    }
}