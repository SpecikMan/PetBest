package com.specikman.petbest.data.remote.firebase


import android.graphics.BitmapFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.specikman.petbest.data.remote.dto.Image
import com.specikman.petbest.domain.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class FirebaseAPI {

    private lateinit var auth: FirebaseAuth

    //Firestore
    private val userCollectionRef = Firebase.firestore.collection("users")
    private val productRef = Firebase.firestore.collection("products")
    private val categoryRef = Firebase.firestore.collection("categories")
    private val cartRef = Firebase.firestore.collection("carts")
    private val favoriteRef = Firebase.firestore.collection("favorites")
    private val orderRef = Firebase.firestore.collection("orders")
    private val serviceRef = Firebase.firestore.collection("services")
    private val historyRef = Firebase.firestore.collection("history")

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
        for (product in getProducts()) {
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


    //Cart
    suspend fun getCarts(): List<Cart> = CoroutineScope(Dispatchers.IO).async {
        val carts = mutableListOf<Cart>()
        val snapshot =
            cartRef.get().await()
        if (snapshot.documents.isNotEmpty()) {
            for (document in snapshot.documents) {
                document.toObject(Cart::class.java)?.let {
                    carts.add(it)
                }
            }
        }
        return@async carts
    }.await()

    suspend fun addCart(cart: Cart) = CoroutineScope(Dispatchers.IO).launch {
        try {
            auth = FirebaseAuth.getInstance()
            cart.id = getCarts().size + 1
            cart.userUID = auth.currentUser?.uid.toString()
            cartRef.add(cart).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateCart(cart: Cart) = CoroutineScope(Dispatchers.IO).launch {
        try {
            auth = FirebaseAuth.getInstance()
            cart.id = getCarts().size + 1
            cart.userUID = auth.currentUser?.uid.toString()
            val cartSize =
                getCarts().filter { it.productId == cart.productId && it.userUID == cart.userUID }.size
            if (cartSize == 0) {
                cartRef.add(cart).await()
            } else {
                val map = mutableMapOf<String, Any>().apply {
                    this["id"] = cart.id
                    this["productId"] = cart.productId
                    this["amount"] = cart.amount
                    this["userUID"] = cart.userUID
                    this["costEach"] = cart.costEach
                    this["costTotal"] = cart.costTotal
                }
                val query = cartRef.whereEqualTo("userUID", cart.userUID)
                    .whereEqualTo("productId", cart.productId).get().await()
                if (query.documents.isNotEmpty()) {
                    for (document in query) {
                        cartRef.document(document.id).set(
                            map,
                            SetOptions.merge()
                        ).await()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteCart(cart: Cart) = CoroutineScope(Dispatchers.IO).launch {
        try {
            auth = FirebaseAuth.getInstance()
            val query = cartRef.whereEqualTo("userUID", auth.currentUser?.uid)
                .whereEqualTo("productId", cart.productId).get().await()
            if (query.documents.isNotEmpty()) {
                for (document in query.documents) {
                    cartRef.document(document.id).delete()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Favorites

    suspend fun getFavoriteProducts(): List<Favorite> = CoroutineScope(Dispatchers.IO).async {
        val favorites = mutableListOf<Favorite>()
        val snapshot =
            favoriteRef.get().await()
        if (snapshot.documents.isNotEmpty()) {
            for (document in snapshot.documents) {
                document.toObject(Favorite::class.java)?.let {
                    favorites.add(it)
                }
            }
        }
        return@async favorites
    }.await()

    suspend fun addProductToFavorite(favorite: Favorite): Boolean =
        CoroutineScope(Dispatchers.IO).async {
            try {
                auth = FirebaseAuth.getInstance()
                favorite.userUID = auth.currentUser?.uid.toString()
                val favoriteProducts = getFavoriteProducts().filter {
                    it.userUID == auth.currentUser?.uid && it.productId == favorite.productId
                }.size
                if (favoriteProducts == 0) {
                    favoriteRef.add(favorite).await()
                    true
                } else {
                    val query = favoriteRef.whereEqualTo("userUID", favorite.userUID)
                        .whereEqualTo("productId", favorite.productId).get().await()
                    if (query.documents.isNotEmpty()) {
                        for (document in query.documents) {
                            favoriteRef.document(document.id).delete()
                        }
                    }
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }.await()

    //Order

    suspend fun getOrders(): List<Order> = CoroutineScope(Dispatchers.IO).async {
        try {
            val orders = mutableListOf<Order>()
            val snapshot = orderRef.get().await()
            if (snapshot.documents.isNotEmpty()) {
                for (document in snapshot.documents) {
                    orders.add(document.toObject(Order::class.java) ?: Order())
                }
            }
            orders
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Order>()
        }
    }.await()

    suspend fun addOrder(order: Order) = CoroutineScope(Dispatchers.IO).launch {
        try {
            orderRef.add(order).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Services
    suspend fun getServices(): List<Service> = CoroutineScope(Dispatchers.IO).async {
        try {
            val services = mutableListOf<Service>()
            val snapshot = serviceRef.get().await()
            if (snapshot.documents.isNotEmpty()) {
                for (document in snapshot.documents) {
                    services.add(document.toObject(Service::class.java) ?: Service())
                }
            }
            services
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Service>()
        }
    }.await()

    //Users
    suspend fun getUsers(): List<User> = CoroutineScope(Dispatchers.IO).async {
        try {
            val users = mutableListOf<User>()
            val snapshot = userCollectionRef.get().await()
            if (snapshot.documents.isNotEmpty()) {
                for (document in snapshot.documents) {
                    users.add(document.toObject(User::class.java) ?: User())
                }
            }
            users
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }.await()

    suspend fun updateUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val map = mutableMapOf<String, Any>().apply {
                this["uid"] = user.uid
                this["name"] = user.name
                this["email"] = user.email
                this["phone"] = user.phone
                this["creditCard"] = user.creditCard
                this["balance"] = user.balance
            }
            val query = userCollectionRef.whereEqualTo("uid", user.uid).get().await()
            if (query.documents.isNotEmpty()) {
                for (document in query) {
                    userCollectionRef.document(document.id).set(
                        map,
                        SetOptions.merge()
                    ).await()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //History
    suspend fun getHistory(): List<History> = CoroutineScope(Dispatchers.IO).async {
        try {
            val history = mutableListOf<History>()
            val snapshot = historyRef.get().await()
            if (snapshot.documents.isNotEmpty()) {
                for (document in snapshot.documents) {
                    history.add(document.toObject(History::class.java) ?: History())
                }
            }
            history
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }.await()

    suspend fun addHistory(history: History) = CoroutineScope(Dispatchers.IO).launch {
        try {
            historyRef.add(history).await()
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

}