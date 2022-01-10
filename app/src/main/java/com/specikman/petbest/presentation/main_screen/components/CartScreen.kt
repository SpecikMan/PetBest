package com.specikman.petbest.presentation.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.R
import com.specikman.petbest.common.QRGenerator
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.Cart
import com.specikman.petbest.domain.model.Order
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.state.CartsState
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.main_screen.view_models.ImageViewModel
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.Orange
import com.specikman.petbest.presentation.ui.theme.Shapes
import com.specikman.petbest.presentation.ui.theme.primaryColor
import kotlinx.coroutines.flow.merge

@Composable
fun Cart(
    imageViewModel: ImageViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    if (!homeViewModel.stateCarts.value.isLoading && !homeViewModel.stateProducts.value.isLoading) {
        val totalPrice =
            remember { mutableStateOf(homeViewModel.stateCarts.value.carts.filter {  it.userUID == auth.currentUser?.uid}.sumOf { it.costTotal })}
        val cartsState = remember { mutableStateOf(homeViewModel.stateCarts.value.carts) }
        val qrState = remember { mutableStateOf(false) }
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                HeaderBack()
                Products(
                    homeViewModel = homeViewModel,
                    imageViewModel = imageViewModel,
                    carts = cartsState.value.filter { it.userUID == auth.currentUser?.uid },
                    totalPrice = totalPrice,
                    cartsState = cartsState
                )
                Footer(
                    homeViewModel = homeViewModel,
                    totalPrice = totalPrice,
                    qrState = qrState,
                    carts = cartsState.value,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun HeaderBack(
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Giỏ hàng của bạn", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Footer(
    homeViewModel: HomeViewModel,
    totalPrice: MutableState<Long>,
    qrState: MutableState<Boolean>,
    carts: List<Cart>,
    navController: NavController
) {
    val chooseState1 = remember { mutableStateOf(true) }
    val chooseState2 = remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 7.dp)
    ) {

        Checkbox(checked = chooseState1.value, onCheckedChange = {
            chooseState1.value = !chooseState1.value
            chooseState2.value = !chooseState2.value
        })
        Text(
            text = "Thanh toán tại cửa hàng",
            modifier = Modifier
                .padding(start = 10.dp, top = 2.dp)
                .clickable {
                    chooseState1.value = !chooseState1.value
                    chooseState2.value = !chooseState2.value
                }
        )
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 7.dp)
    ) {
        Checkbox(checked = chooseState2.value, onCheckedChange = {
            chooseState1.value = !chooseState1.value
            chooseState2.value = !chooseState2.value
        })
        Text(
            text = "Thanh toán bằng tiền trong tài khoản",
            modifier = Modifier
                .padding(start = 10.dp, top = 2.dp)
                .clickable {
                    chooseState1.value = !chooseState1.value
                    chooseState2.value = !chooseState2.value
                }
        )
    }
    if (chooseState2.value) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 7.dp)
        ) {
            Text(
                text = "Trong tài khoản hiện có: 700.000đ",
                modifier = Modifier
                    .padding(start = 10.dp, top = 2.dp),
                fontSize = 15.sp
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 7.dp)
    ) {
        Text(
            text = "Tổng chi phí giỏ hàng: ${ToMoneyFormat.toMoney(totalPrice.value)}đ",
            modifier = Modifier
                .padding(start = 10.dp, top = 2.dp),
            fontSize = 20.sp,
            color = Orange,
            fontWeight = FontWeight.Medium
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 16.dp)
            .clip(Shapes.medium)
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton(text = "Tiến hành thanh toán", active = true, modifier = Modifier.weight(1f)) {
            val orderID = homeViewModel.stateOrders.value.orders.size + 1
            carts.forEach { cart ->
                Order(
                    id = orderID,
                    productId = cart.productId,
                    amount = cart.amount,
                    userUID = cart.userUID,
                    costEach = cart.costEach,
                    costTotal = cart.costTotal
                ).also {
                    homeViewModel.addOrder(it)
                }
                homeViewModel.deleteCart(cart)
                navController.navigate(Screen.Home.route)
            }
        }
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
                bitmap = QRGenerator.generateQRBitmap("cart:${auth.currentUser?.uid}")
                    .asImageBitmap(),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }
    }
    Spacer(modifier = Modifier.height(70.dp))
}

@Composable
fun Products(
    homeViewModel: HomeViewModel,
    imageViewModel: ImageViewModel,
    carts: List<Cart>,
    totalPrice: MutableState<Long>,
    cartsState: MutableState<List<Cart>>
) {
    Column {
        carts.forEach { cart ->
            Product(
                product = homeViewModel.stateProducts.value.products.first { it.id == cart.productId },
                cart = cart,
                homeViewModel = homeViewModel,
                imageViewModel = imageViewModel,
                totalPrice = totalPrice,
                cartsState = cartsState
            )
        }
    }
}


@Composable
fun Product(
    product: Product,
    cart: Cart,
    homeViewModel: HomeViewModel,
    imageViewModel: ImageViewModel,
    totalPrice: MutableState<Long>,
    cartsState: MutableState<List<Cart>>
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { }
            .padding(horizontal = 6.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val priceTotalState = remember { mutableStateOf(product.price * cart.amount) }
        val amountState = remember { mutableStateOf(cart.amount) }
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
            Text(text = "Số lượng: ${amountState.value}", fontSize = 13.sp, color = Color.Gray)
            Text(
                text = "Giá mua sản phẩm: ${ToMoneyFormat.toMoney(product.price)} ",
                fontSize = 13.sp,
                color = primaryColor
            )
            Text(text = "Giá tổng: ${ToMoneyFormat.toMoney(priceTotalState.value)}", fontSize = 16.sp, color = Orange)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    if (amountState.value > 0) {
                        amountState.value--
                        priceTotalState.value -= product.price
                        totalPrice.value -= product.price
                        cart.apply {
                            amount = amountState.value
                            costTotal = priceTotalState.value
                        }
                        homeViewModel.updateCart(cart = cart)
                    }
                    if (amountState.value == 0) {
                        if (cartsState.value.isNotEmpty()) {
                            val sub1 = cartsState.value.subList(0, cartsState.value.indexOf(cart))
                            val sub2 = cartsState.value.subList(
                                cartsState.value.indexOf(cart),
                                cartsState.value.size
                            )
                            cartsState.value = sub1 + sub2
                        } else {
                            cartsState.value = emptyList()
                        }
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    amountState.value++
                    priceTotalState.value += product.price
                    totalPrice.value += product.price
                    cart.apply {
                        amount = amountState.value
                        costTotal = priceTotalState.value
                    }
                    homeViewModel.updateCart(cart = cart)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    homeViewModel.deleteCart(cart)
                    if (cartsState.value.isNotEmpty()) {
                        val sub1 = cartsState.value.subList(0, cartsState.value.indexOf(cart))
                        val sub2 = cartsState.value.subList(
                            cartsState.value.indexOf(cart),
                            cartsState.value.size
                        )
                        cartsState.value = sub1 + sub2
                    } else {
                        cartsState.value = emptyList()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
