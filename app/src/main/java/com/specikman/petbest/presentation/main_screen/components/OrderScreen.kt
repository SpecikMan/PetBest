package com.specikman.petbest.presentation.main_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.main_screen.view_models.ImageViewModel
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.Orange
import com.specikman.petbest.presentation.ui.theme.Shapes
import com.specikman.petbest.presentation.ui.theme.primaryColor

lateinit var auth: FirebaseAuth

@Composable
fun OrderScreen(
    imageViewModel: ImageViewModel,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    auth = FirebaseAuth.getInstance()
    if (!homeViewModel.stateOrders.value.isLoading) {
        val ordersState =
            remember { mutableStateOf(homeViewModel.stateOrders.value.orders.filter { it.userUID == auth.currentUser?.uid && it.type == "Mua hàng" }) }
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                HeaderBackOrder(homeViewModel = homeViewModel, ordersState = ordersState)
                Orders(orders = ordersState.value, imageViewModel = imageViewModel, homeViewModel = homeViewModel, navController = navController)
            }
        }
    }
}

@Composable
fun HeaderBackOrder(
    homeViewModel: HomeViewModel,
    ordersState: MutableState<List<Order>>
) {
    auth = FirebaseAuth.getInstance()
    val tabButtonState1 = remember { mutableStateOf(true) }
    val tabButtonState2 = remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Lịch sử thanh toán", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(Shapes.medium)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton(
            "Mua hàng", active = tabButtonState1.value,
            Modifier
                .weight(0.8f)
        ) {
            ordersState.value =
                homeViewModel.stateOrders.value.orders.filter { it.userUID == auth.currentUser?.uid && it.type == "Mua hàng" }
            tabButtonState1.value = !tabButtonState1.value
            tabButtonState2.value = !tabButtonState2.value
        }
        TabButton(
            text = "Dịch vụ chăm sóc",
            active = tabButtonState2.value,
            modifier = Modifier.weight(1.2f)
        ) {
            ordersState.value =
                homeViewModel.stateOrders.value.orders.filter { it.userUID == auth.currentUser?.uid && it.type == "Dịch vụ" }
            tabButtonState1.value = !tabButtonState1.value
            tabButtonState2.value = !tabButtonState2.value
        }
    }
}

@Composable
fun Orders(
    orders: List<Order>,
    imageViewModel: ImageViewModel,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val distinctOrders = orders.distinctBy { it.id }
    Column {
        distinctOrders.forEach { order ->
            Order(
                id = order.id,
                productCount = orders.count { it.id == order.id },
                totalPrice = orders.filter { it.id == order.id }.sumOf { it.costTotal },
                date = order.date,
                type = order.type,
                homeViewModel = homeViewModel,
                productId = order.productId
            ){
                if(order.type == "Mua hàng"){
                    imageViewModel._stateFloatingButton.value = false
                    imageViewModel._stateOrderDetail.value = homeViewModel.stateOrders.value.orders.first { it == order }
                    navController.navigate(Screen.OrderDetailScreen.route)
                }
            }
        }
    }
}

@Composable
fun Order(
    id: Int,
    productCount: Int,
    totalPrice: Long,
    date: java.util.Date,
    type: String,
    homeViewModel: HomeViewModel,
    productId: Int,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if(type == "Mua hàng"){
            Column(Modifier.weight(1f)) {
                Text(text = "Order ID:$id - $type", fontWeight = FontWeight.Bold, fontSize = 23.sp)
                Text(text = "Số lượng sản phẩm: $productCount", fontSize = 13.sp, color = Color.Gray)
                Text(text = "Giá tổng: ${ToMoneyFormat.toMoney(totalPrice)}", fontSize = 16.sp, color = Orange)
                Text(text = "Ngày thanh toán: $date", fontSize = 16.sp, color = primaryColor)
                Spacer(modifier = Modifier.weight(1f))
            }
        } else {
            Column(Modifier.weight(1f)) {
                Text(text = "Order ID:$id - $type", fontWeight = FontWeight.Bold, fontSize = 23.sp)
                Text(text = " ${homeViewModel.stateServices.value.services.first { it.id == productId }.name}", fontSize = 13.sp, color = Color.Gray)
                Text(text = "Giá tổng: ${ToMoneyFormat.toMoney(totalPrice)}", fontSize = 16.sp, color = Orange)
                Text(text = "Ngày thanh toán: $date", fontSize = 16.sp, color = primaryColor)
                Spacer(modifier = Modifier.weight(1f))
            }
        }

    }
}

