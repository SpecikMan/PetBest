package com.specikman.petbest.presentation.main_screen.components

import android.Manifest
import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.specikman.petbest.R
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.HomeTheme

@ExperimentalPermissionsApi
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel
) {
    HomeTheme {
        HomeScreen(navController = navController, viewModel = viewModel)
    }
}

@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Box(Modifier.verticalScroll(rememberScrollState())) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, (-30).dp),
            painter = painterResource(id = R.drawable.bg_main),
            contentDescription = "Header Background",
            contentScale = ContentScale.FillWidth
        )
        Column {
            AppBar(navController = navController, viewModel = viewModel)
            Content(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun AppBar(
    navController: NavController,
    viewModel: HomeViewModel
) {

    val searchValue = remember { mutableStateOf("") }

    Row(
        Modifier
            .padding(16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextField(
            value = searchValue.value,
            onValueChange = { searchValue.value = it },
            label = { Text(text = "Tìm kiếm", fontSize = 9.sp) },
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = {
        }) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "",
                tint = Color.White
            )
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun Content(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Column {
        Header(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        Promotions()
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        BestSellerSection(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        DiscountSection(navController = navController, viewModel = viewModel)
        Spacer(modifier = Modifier.height(90.dp))
    }
}

@ExperimentalPermissionsApi
@Composable
fun Header(
    navController: NavController
) {
    Card(
        Modifier
            .height(64.dp)
            .padding(horizontal = 16.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            QrButton(navController = navController)

            VerticalDivider()
            Row(Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable { }
                .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = "",
                    tint = Color(0xFF6FCF97)
                )
                Column(Modifier.padding(8.dp)) {
                    Text(text = "560.000đ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(
                        text = "Tiền trong tài khoản",
                        color = MaterialTheme.colors.primary,
                        fontSize = 12.sp
                    )
                }
            }

            VerticalDivider()
            Row(Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable { }
                .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
                Column(Modifier.padding(8.dp)) {
                    Text(text = "320.000đ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(text = "Chi phí giỏ hàng", color = Color.LightGray, fontSize = 12.sp)
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun QrButton(
    navController: NavController
) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    IconButton(
        onClick = {
            if(!cameraPermissionState.hasPermission){
                cameraPermissionState.launchPermissionRequest()
            }
            navController.navigate(Screen.QRScanner.route)
        },
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_scan),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun VerticalDivider() {
    Divider(
        color = Color(0xFFF1F1F1),
        modifier = Modifier
            .width(1.dp)
            .height(32.dp)
    )
}

@Composable
fun Promotions() {
    LazyRow(
        Modifier.height(160.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.img_promotion_food),
                title = "Thức ăn",
                subtitle = "Giảm giá đến",
                header = "20%",
                backgroundColor = Color(0xff6EB6F5)
            )
        }
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.img_promotion_clothes),
                title = "Phụ kiện",
                subtitle = "Chỉ từ",
                header = "30.000đ",
                backgroundColor = MaterialTheme.colors.primary
            )
        }
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.img_promotion_dog_house),
                title = "Nhà, Chuồng",
                subtitle = "Chỉ từ",
                header = "900.000đ",
                backgroundColor = Color(0xffbf37d4)
            )
        }
    }
}

@Composable
fun PromotionItem(
    title: String = "",
    subtitle: String = "",
    header: String = "",
    backgroundColor: Color = Color.Transparent,
    imagePainter: Painter
) {
    Card(
        Modifier.width(300.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor,
        elevation = 0.dp
    ) {
        Row {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, fontSize = 14.sp, color = Color.White)
                Text(
                    text = subtitle,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = header,
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Image(
                painter = imagePainter, contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                alignment = Alignment.CenterEnd,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CategorySection(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Danh Mục", style = MaterialTheme.typography.h6)
        }
        CategoryItems(navController = navController, viewModel = viewModel)
    }
}

@Composable
fun CategoryButton(
    text: String = "",
    icon: Painter,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .width(72.dp)
            .clickable {
                onClick()
            }
    ) {
        Box(
            Modifier
                .size(72.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(18.dp)
        ) {
            Image(painter = icon, contentDescription = "", modifier = Modifier.fillMaxSize())
        }
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

@Composable
fun BestSellerSection(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mặt hàng bán chạy", style = MaterialTheme.typography.h6)
            TextButton(onClick = {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.bought }
                navController.navigate(Screen.AllProducts.route)
            }) {
                Text(text = "Xem thêm", color = MaterialTheme.colors.primary)
            }
        }
        BestSellerItems(
            products = viewModel.stateBestSellerProducts.value.products,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun DiscountSection(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Siêu giảm giá", style = MaterialTheme.typography.h6)
            TextButton(onClick = {
                viewModel._stateShowProduct.value =
                    viewModel.stateProducts.value.products.sortedByDescending { it.discount }
                navController.navigate(Screen.AllProducts.route)
            }) {
                Text(text = "Xem thêm", color = MaterialTheme.colors.primary)
            }
        }
        DiscountItems(
            products = viewModel.stateMostDiscountProducts.value.products,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun BestSellerItems(
    products: List<Product>,
    navController: NavController,
    viewModel: HomeViewModel
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { item ->
            if (viewModel.stateImages.value.isLoading || viewModel.stateBestSellerProducts.value.isLoading) {
                CircularProgressIndicator()
            } else {
                BestSellerItem(
                    product = item,
                    bitmap = viewModel.stateImages.value.images.first {
                        item.image == it.image
                    }.bitmap
                ){
                    viewModel._stateProductDetail.value = viewModel.stateProducts.value.products.first { it.id == item.id }
                    navController.navigate(Screen.ProductDetail.route)
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    navController: NavController,
    viewModel: HomeViewModel
) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CategoryButton(
                text = "Thức ăn",
                icon = painterResource(id = R.drawable.img_category_food),
                backgroundColor = Color(0xffFEF4E7)
            ) {
                viewModel._stateShowProduct.value = viewModel.stateProducts.value.products.filter { it.category == "Thức ăn" }
                navController.navigate(Screen.AllProducts.route)
            }
        }
        item {
            CategoryButton(
                text = "Dụng cụ",
                icon = painterResource(id = R.drawable.img_category_equipment),
                backgroundColor = Color(0xffF6FBF3)
            ) {
                viewModel._stateShowProduct.value = viewModel.stateProducts.value.products.filter { it.category == "Dụng cụ" }
                navController.navigate(Screen.AllProducts.route)
            }
        }
        item {
            CategoryButton(
                text = "Quần áo",
                icon = painterResource(id = R.drawable.img_category_clothes),
                backgroundColor = Color(0xffF6FBF3)
            ) {
                viewModel._stateShowProduct.value = viewModel.stateProducts.value.products.filter { it.category == "Quần áo" }
                navController.navigate(Screen.AllProducts.route)
            }
        }
        item {
            CategoryButton(
                text = "Thuốc",
                icon = painterResource(id = R.drawable.img_category_medicine),
                backgroundColor = Color(0xffFFFBF3)
            ) {
                viewModel._stateShowProduct.value = viewModel.stateProducts.value.products.filter { it.category == "Thuốc" }
                navController.navigate(Screen.AllProducts.route)
            }
        }
        item {
            CategoryButton(
                text = "Nhà, Chuồng",
                icon = painterResource(id = R.drawable.img_category_house),
                backgroundColor = Color(0xffF6E6E9)
            ) {
                viewModel._stateShowProduct.value = viewModel.stateProducts.value.products.filter { it.category == "Nhà, Chuồng" }
                navController.navigate(Screen.AllProducts.route)
            }
        }
    }
}

@Composable
fun DiscountItems(
    products: List<Product>,
    navController: NavController,
    viewModel: HomeViewModel
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { item ->
            if (viewModel.stateImages.value.isLoading || viewModel.stateMostDiscountProducts.value.isLoading) {
                CircularProgressIndicator()
            } else {
                BestSellerItem(
                    product = item,
                    bitmap = viewModel.stateImages.value.images.first {
                        item.image == it.image
                    }.bitmap
                ){
                    viewModel._stateProductDetail.value = viewModel.stateProducts.value.products.first { it.id == item.id }
                    navController.navigate(Screen.ProductDetail.route)
                }
            }
        }
    }
}

@Composable
fun BestSellerItem(
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

