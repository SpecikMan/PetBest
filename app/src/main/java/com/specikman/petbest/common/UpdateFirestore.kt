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
    companion object {
        private val ref = Firebase.firestore.collection("products")
        private fun getMap(old: Product): MutableMap<String, Any> = mutableMapOf<String, Any>().apply {
            this["id"] = old.id
            this["name"] = old.name
            this["image"] = old.image
            this["category"] = old.category
            this["price"] = old.price
            this["stock"] = old.stock
            this["bought"] = old.bought
            this["discount"] = old.discount
            this["description"] = "o"
            this["benefit"] = "o"
            this["how_to_use"] = "o"
        }

        private fun update(product: Product, map: Map<String, Any>) =
            CoroutineScope(Dispatchers.IO).launch {
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

        fun updateFirestore() {
            CoroutineScope(Dispatchers.IO).launch {
                val ref = Firebase.firestore.collection("products")
                val snapshot = ref.get().await()
                for (document in snapshot.documents) {
                    val product = document.toObject(Product::class.java) ?: Product()
                    val map =
                        getMap(product)
                    update(product = product, map = map)
                }
            }
        }
    }

}