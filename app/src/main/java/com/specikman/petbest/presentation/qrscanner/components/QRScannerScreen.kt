package com.specikman.petbest.presentation.qrscanner.components

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.common.BarCodeAnalyser
import com.specikman.petbest.domain.model.Cart
import com.specikman.petbest.domain.model.History
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.main_screen.view_models.ImageViewModel
import com.specikman.petbest.presentation.navigation.Screen
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@ExperimentalPermissionsApi
@Composable
fun QRScanner(
    context: Context,
    navController: NavController,
    imageViewModel: ImageViewModel
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraPreview(
                context = context,
                navController = navController,
                imageViewModel = imageViewModel
            )
        }
    }

}


@Composable
fun CameraPreview(
    context: Context,
    navController: NavController,
    imageViewModel: ImageViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    val barCodeVal = remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()

    if (!homeViewModel.stateProducts.value.isLoading && !homeViewModel.stateCarts.value.isLoading && !homeViewModel.stateOrders.value.isLoading)
        AndroidView(
            factory = { AndroidViewContext ->
                PreviewView(AndroidViewContext).apply {
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
            },
            modifier = Modifier
                .fillMaxSize(),
            update = { previewView ->
                val cameraSelector: CameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
                val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                    ProcessCameraProvider.getInstance(context)

                cameraProviderFuture.addListener({
                    preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                        barcodes.forEach { barcode ->
                            barcode.rawValue?.let { barcodeValue ->
                                barCodeVal.value = barcodeValue
                                val result = barCodeVal.value.split(":")
                                when (result[0]) {
                                    "product" -> {
                                        auth.currentUser?.uid?.let{
                                            homeViewModel.addHistory(History(
                                                uid = it,
                                                productId = result[1].toInt(),
                                            ))
                                        }
                                        imageViewModel._stateProductDetail.value = homeViewModel.stateProducts.value.products.first { it.id == result[1].toInt() }
                                        imageViewModel._stateFloatingButton.value = true
                                        navController.popBackStack()
                                        navController.navigate(Screen.ProductDetail.route)
                                    }
                                    "cart" -> {
                                        imageViewModel._stateFloatingButton.value = true
                                        val newCarts =
                                            homeViewModel.stateCarts.value.carts.filter { it.userUID == result[1] }
                                        val currentCarts =
                                            homeViewModel.stateCarts.value.carts.filter { it.userUID == auth.currentUser?.uid }
                                        currentCarts.forEach { cart ->
                                            homeViewModel.deleteCart(cart)
                                        }
                                        newCarts.forEach { cart ->
                                            auth.currentUser?.uid?.let { uid ->
                                                cart.also {
                                                    it.userUID = uid
                                                    homeViewModel.addCart(it)
                                                }
                                            }
                                        }
                                        Toast.makeText(context, "Sao chép giỏ hàng thành công", Toast.LENGTH_LONG).show()
                                        navController.navigate(Screen.Home.route)
                                    }
                                    "order" -> {
                                        imageViewModel._stateFloatingButton.value = true
                                        val currentCarts = homeViewModel.stateCarts.value.carts.filter { it.userUID == auth.currentUser?.uid }
                                        currentCarts.forEach { cart ->
                                            homeViewModel.deleteCart(cart)
                                        }
                                        val newCarts = mutableListOf<Cart>()
                                        val cartId = homeViewModel.stateCarts.value.carts.size + 1
                                        val newOrders = homeViewModel.stateOrders.value.orders.filter { it.userUID == result[1] && it.id == result[2].toInt() }
                                        newOrders.forEach {
                                            newCarts.add(Cart(
                                                id = cartId,
                                                productId = it.productId,
                                                amount = it.amount,
                                                userUID =  it.userUID,
                                                costEach = it.costEach,
                                                costTotal = it.costTotal
                                            ))
                                        }
                                        newCarts.forEach {
                                            auth.currentUser?.uid?.let { uid ->
                                                it.also {
                                                    it.userUID = uid
                                                    homeViewModel.addCart(it)
                                                }
                                            }
                                        }
                                        Toast.makeText(context, "Sao chép vào giỏ hàng thành công", Toast.LENGTH_LONG).show()
                                        navController.navigate(Screen.Home.route)
                                    }
                                    else -> {
                                    }
                                }
                            }
                        }
                    }
                    val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                        }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                    }
                }, ContextCompat.getMainExecutor(context))
            })
}