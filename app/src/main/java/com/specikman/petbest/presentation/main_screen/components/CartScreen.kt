package com.specikman.petbest.presentation.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.specikman.petbest.R
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.ui.theme.Orange
import com.specikman.petbest.presentation.ui.theme.primaryColor

@Composable
fun Cart(
    viewModel: HomeViewModel,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ){
        item{
            HeaderBack()
            Products(
                viewModel = viewModel,
                products = listOf(Product(), Product())
            )
            Footer()
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
fun Footer() {
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
        modifier = Modifier.padding( top = 7.dp)
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
}

@Composable
fun Products(
    viewModel: HomeViewModel,
    products: List<Product>
) {
    Column {
        products.forEach {
            Product(
                product = Product(image = "gs://petbest.appspot.com/product_image/clothes/img_product_clothes_1.jpg"),
                amount = 2,
                viewModel = viewModel
            )
            Product(
                product = Product(image = "gs://petbest.appspot.com/product_image/clothes/img_product_clothes_6.jpg"),
                amount = 2,
                viewModel = viewModel
            )
        }
    }
}


@Composable
fun Product(
    product: Product,
    amount: Int,
    viewModel: HomeViewModel
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { }
            .padding(horizontal = 28.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val priceTotalState = remember { mutableStateOf(product.price * amount) }

        Image(
            bitmap = viewModel.stateImages.value.images.first { it.image == product.image }.bitmap.asImageBitmap(),
            contentScale = ContentScale.Fit,
            contentDescription = "Cover",
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
        )
        Column(Modifier.weight(1f)) {
            Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Số lượng: $amount", fontSize = 13.sp, color = Color.Gray)
            Text(
                text = "Giá mua sản phẩm: ${product.price} ",
                fontSize = 13.sp,
                color = primaryColor
            )
            Text(text = "Giá tổng: ${priceTotalState.value}", fontSize = 16.sp, color = Orange)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
        }
    }
}