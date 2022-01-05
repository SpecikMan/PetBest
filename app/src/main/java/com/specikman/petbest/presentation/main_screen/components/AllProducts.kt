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
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.primaryColor

@Composable
fun AllProducts(
    navController: NavController,
    viewModel: HomeViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SearchBar(viewModel = viewModel)
            Spacer(modifier = Modifier.height(5.dp))
            TagList(viewModel = viewModel, navController = navController)
            Spacer(modifier = Modifier.height(5.dp))
            if (viewModel._stateShowProduct.value.isEmpty()) {
                Products(
                    viewModel = viewModel,
                    products = viewModel.stateProducts.value.products,
                    navController = navController
                )
            } else {
                Products(
                    viewModel = viewModel,
                    products = viewModel.stateShowProduct.value,
                    navController = navController
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun SearchBar(
    viewModel: HomeViewModel
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
                viewModel._stateShowProduct.value = viewModel.stateProducts.value.products.filter {
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
    navController: NavController
) {
    LazyRow(
        Modifier.height(40.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterHorizontally)
    ) {
        item {
            TAG(tag = "Tất cả") {
                viewModel._stateShowProduct.value = viewModel.stateProducts.value.products
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Thức ăn") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Thức ăn" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Dụng cụ") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Dụng cụ" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Quần áo") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Quần áo" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Nhà Chuồng") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Nhà, Chuồng" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Thuốc") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.filter { it.category == "Thuốc" }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Bán chạy") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.bought }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Giảm giá sâu") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.discount }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Giá cao đến thấp") {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.price }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "Giá thấp đến cao") {
                viewModel._stateShowProduct.value =
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
    navController: NavController
) {
    FlowRow(
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        products.forEach { product ->
            if (viewModel.stateImages.value.isLoading || viewModel.stateProducts.value.isLoading) {
                CircularProgressIndicator()
            } else {
                Product(
                    product = product,
                    bitmap = viewModel.stateImages.value.images.first { viewModelImage -> product.image == viewModelImage.image }.bitmap
                ) {
                    viewModel._stateProductDetail.value =
                        viewModel.stateProducts.value.products.first { it.id == product.id }
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
            .width(160.dp).clickable { onClick() }
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