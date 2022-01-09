package com.specikman.petbest.presentation.main_screen.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.History
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.main_screen.view_models.ImageViewModel
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.primaryColor

@Composable
fun AllProducts(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    imageViewModel: ImageViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SearchBar(viewModel = homeViewModel, imageViewModel = imageViewModel)
            Spacer(modifier = Modifier.height(5.dp))
            TagList(viewModel = homeViewModel, imageViewModel = imageViewModel)
            Spacer(modifier = Modifier.height(5.dp))
            if (imageViewModel._stateShowProduct.value.isEmpty()) {
                Products(
                    viewModel = homeViewModel,
                    products = homeViewModel.stateProducts.value.products,
                    navController = navController,
                    imageViewModel = imageViewModel
                )
            } else {
                Products(
                    viewModel = homeViewModel,
                    products = imageViewModel.stateShowProduct.value,
                    navController = navController,
                    imageViewModel = imageViewModel
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun SearchBar(
    viewModel: HomeViewModel,
    imageViewModel: ImageViewModel
) {
    val searchValue = remember { mutableStateOf("") }
    Row(
        Modifier
            .padding(16.dp)
            .height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextField(
            value = searchValue.value,
            onValueChange = { newValue ->
                searchValue.value = newValue
                imageViewModel._stateShowProduct.value = viewModel.stateProducts.value.products.filter {
                    it.name.lowercase().contains(newValue.lowercase())
                }
            },
            label = { Text(text = "Tìm kiếm", fontSize = 10.sp) },
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = primaryColor
            )
        )
    }
}

@Composable
fun TagList(
    viewModel: HomeViewModel,
    imageViewModel: ImageViewModel
) {
    LazyRow(
        Modifier.height(40.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterHorizontally)
    ) {
        item {
            TAG(tag = "Tất cả") {
                imageViewModel._stateShowProduct.value = viewModel.stateProducts.value.products
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Thức ăn") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Thức ăn" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Dụng cụ") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Dụng cụ" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Quần áo") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Quần áo" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Nhà Chuồng") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Nhà, Chuồng" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Thuốc") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Thuốc" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Bán chạy") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.bought }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Giảm giá sâu") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.discount }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Giá cao đến thấp") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.price }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Giá thấp đến cao") {
                imageViewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedBy { it.price }
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun TAG(
    tag: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(10.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = tag,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun Products(
    products: List<Product>,
    viewModel: HomeViewModel,
    navController: NavController,
    imageViewModel: ImageViewModel
) {
    FlowRow(
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        products.forEach { product ->
            if (imageViewModel.stateImages.value.isLoading || viewModel.stateProducts.value.isLoading) {
                CircularProgressIndicator()
            } else {
                Product(
                    product = product,
                    bitmap = imageViewModel.stateImages.value.images.first { viewModelImage -> product.image == viewModelImage.image }.bitmap
                ) {
                    val auth = FirebaseAuth.getInstance()
                    auth.currentUser?.uid?.let{
                        viewModel.addHistory(
                            History(
                            uid = it,
                            productId = product.id
                        )
                        )
                    }
                    imageViewModel._stateProductDetail.value = product
                    navController.navigate(Screen.ProductDetail.route)
                }
            }
        }
    }
}

@Composable
fun Product(
    product: Product,
    bitmap: Bitmap,
    onClick: () -> Unit
) {
    Card(
        Modifier
            .width(160.dp)
            .clickable { onClick() }
    ) {
        Column {
            Spacer(modifier = Modifier.height(5.dp))
            Image(
                bitmap = bitmap.asImageBitmap(), contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        ToMoneyFormat.toMoney(product.price),
                        textDecoration = if (product.discount > 0)
                            TextDecoration.LineThrough
                        else
                            TextDecoration.None,
                        color = if (product.discount > 0) Color.Gray else Color.Black
                    )
                    if (product.discount > 0) {
                        Text(text = "[${product.discount}%]", color = MaterialTheme.colors.primary)
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = if (product.stock > 0) "Còn hàng" else "Hết hàng",
                    color = if (product.stock > 0) Color.Green else Color.Red
                )
            }
        }
    }
}