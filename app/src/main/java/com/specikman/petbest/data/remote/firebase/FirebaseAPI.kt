package com.specikman.petbest.data.remote.firebase


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.specikman.petbest.data.remote.dto.Image
import com.specikman.petbest.domain.model.Category
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.domain.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class FirebaseAPI {

    private lateinit var auth: FirebaseAuth

    //Firestore
    private val userCollectionRef = Firebase.firestore.collection("users")
    private val productRef = Firebase.firestore.collection("products")
    private val categoryRef = Firebase.firestore.collection("categories")

    //Storage
    private val storageRef = Firebase.storage
    private val downloadSize = 5L * 1024 * 1024


    suspend fun loginWithEmail(
        email: String,
        password: String,
    ) {
        auth = FirebaseAuth.getInstance()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    suspend fun register(
        email: String,
        password: String,
        phone: String,
        name: String
    ) {
        auth = FirebaseAuth.getInstance()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    saveUser(
                        User(
                            email = email,
                            phone = phone,
                            name = name,
                            uid = auth.currentUser?.uid ?: "NaN"
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private suspend fun saveUser(user: User) {
        try {
            userCollectionRef.add(user).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendForgotPasswordEmail(
        email: String
    ) {
        auth = FirebaseAuth.getInstance()
        if (email.isNotBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.sendPasswordResetEmail(email)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    //Product
    suspend fun getProducts(): List<Product> = CoroutineScope(Dispatchers.IO).async {
        val products = mutableListOf<Product>()
        val snapshot =
            productRef.get().await()
        if (snapshot.documents.isNotEmpty()) {
            for (document in snapshot.documents) {
                document.toObject(Product::class.java)?.let {
                    products.add(it)
                }
            }
        }
        return@async products
    }.await()

    suspend fun getBestSellerProducts(): List<Product> = CoroutineScope(Dispatchers.IO).async {
        val products = mutableListOf<Product>()
        val snapshot =
            productRef.orderBy("bought", Query.Direction.DESCENDING).limit(6).get().await()
        if (snapshot.documents.isNotEmpty()) {
            for (document in snapshot.documents) {
                document.toObject(Product::class.java)?.let {
                    products.add(it)
                }
            }
        }
        return@async products
    }.await()

    suspend fun getProductImagesFromStorage(): List<Image> = CoroutineScope(Dispatchers.IO).async {
        val images = mutableListOf<Image>()
        for(product in getProducts()){
            val img =
                storageRef.getReferenceFromUrl(product.image).getBytes(downloadSize).await()
            images.add(
                Image(
                    image = product.image,
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
                )
            )
        }
        return@async images
    }.await()




suspend fun getMostDiscountProducts(): List<Product> = CoroutineScope(Dispatchers.IO).async {
    val products = mutableListOf<Product>()
    val snapshot =
        productRef.orderBy("discount", Query.Direction.DESCENDING).limit(6).get().await()
    if (snapshot.documents.isNotEmpty()) {
        for (document in snapshot.documents) {
            document.toObject(Product::class.java)?.let {
                products.add(it)
            }
        }
    }
    return@async products
}.await()

suspend fun getProductById(id: Int): Product {
    val snapshot = productRef.whereEqualTo("id", id).get().await()
    return if (snapshot.documents.isNotEmpty()) {
        snapshot.documents[0].toObject(Product::class.java) ?: Product()
    } else {
        Product()
    }
}

//Category
suspend fun getCategories(): List<Category> {
    val cats = mutableListOf<Category>()
    val snapshot = categoryRef.get().await()
    return if (snapshot.documents.isNotEmpty()) {
        for (document in snapshot.documents) {
            document.toObject(Category::class.java)?.let {
                cats.add(it)
            }
        }
        cats
    } else {
        emptyList()
    }
}




}