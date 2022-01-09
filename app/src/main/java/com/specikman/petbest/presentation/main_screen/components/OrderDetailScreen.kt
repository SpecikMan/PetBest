package com.specikman.petbest.presentation.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.common.QRGenerator
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.Cart
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.main_screen.view_models.ImageViewModel
import com.specikman.petbest.presentation.ui.theme.Orange
import com.specikman.petbest.presentation.ui.theme.Shapes
import com.specikman.petbest.presentation.ui.theme.primaryColor

@Composable
fun OrderDetail(
    imageViewModel: ImageViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val auth = FirebaseAuth.getInstance()
    if (!homeViewModel.stateOrders.value.isLoading && !homeViewModel.stateProducts.value.isLoading) {
        val qrState = remember { mutableStateOf(false) }
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                HeaderBackOrderDetail()
                ProductsOrderDetail(
                    homeViewModel = homeViewModel,
                    imageViewModel = imageViewModel,
                    orders = homeViewModel.stateOrders.value.orders.filter { it.userUID == auth.currentUser?.uid && it.id == imageViewModel._stateOrderDetail.value.id },
                )
                FooterOrderDetail(
                    homeViewModel = homeViewModel,
                    totalPrice = homeViewModel.stateOrders.value.orders.filter { it.userUID == auth.currentUser?.uid && it.id == imageViewModel._stateOrderDetail.value.id }
                        .sumOf { it.costTotal },
                    qrState = qrState,
                    order = homeViewModel.stateOrders.value.orders.first { it.userUID == auth.currentUser?.uid && it.id == imageViewModel._stateOrderDetail.value.id }
                )
            }
        }
    }
}

@Composable
fun HeaderBackOrderDetail(
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Chi tiết ", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FooterOrderDetail(
    homeViewModel: HomeViewModel,
    totalPrice: Long,
    qrState: MutableState<Boolean>,
    order: Order
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 7.dp)
    ) {
        Text(
            text = "Tổng chi phí giỏ hàng: ${ToMoneyFormat.toMoney(totalPrice)}đ",
            modifier = Modifier
                .padding(start = 10.dp, top = 2.dp),
            fontSize = 20.sp,
            color = Orange,
            fontWeight = FontWeight.Medium
        )
    }
    val qrStringState = remember { mutableStateOf("Xuất mã QR") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(Shapes.medium)
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(44.dp)
            .padding(top = 5.dp)
    ) {
        TabButton(text = qrStringState.value, active = false, modifier = Modifier.weight(1f)) {
            if (qrState.value) {
                qrStringState.value = "Xuất mã QR"
                qrState.value = false
            } else {
                qrStringState.value = "Ẩn mã QR"
                qrState.value = true
            }
        }
    }
    if (qrState.value) {
        val auth = FirebaseAuth.getInstance()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .clip(Shapes.medium)
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                bitmap = QRGenerator.generateQRBitmap("order:${auth.currentUser?.uid}:${order.id}")
                    .asImageBitmap(),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }
    }
    Spacer(modifier = Modifier.height(70.dp))
}

@Composable
fun ProductsOrderDetail(
    homeViewModel: HomeViewModel,
    imageViewModel: ImageViewModel,
    orders: List<Order>,
) {
    Column {
        orders.forEach { order ->
            ProductOrderDetail(
                product = homeViewModel.stateProducts.value.products.first { it.id == order.productId },
                order = order,
                imageViewModel = imageViewModel,
            )
        }
    }
}


@Composable
fun ProductOrderDetail(
    product: Product,
    order: Order,
    imageViewModel: ImageViewModel,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { }
            .padding(horizontal = 6.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            bitmap = imageViewModel.stateImages.value.images.first { it.image == product.image }.bitmap.asImageBitmap(),
            contentScale = ContentScale.Fit,
            contentDescription = "Cover",
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
        )
        Column(Modifier.weight(1f)) {
            Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Số lượng: ${order.amount}", fontSize = 13.sp, color = Color.Gray)
            Text(
                text = "Giá mua sản phẩm: ${ToMoneyFormat.toMoney(order.costEach)} ",
                fontSize = 13.sp,
                color = primaryColor
            )
            Text(text = "Giá tổng: ${ToMoneyFormat.toMoney(order.costTotal)}", fontSize = 16.sp, color = Orange)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}