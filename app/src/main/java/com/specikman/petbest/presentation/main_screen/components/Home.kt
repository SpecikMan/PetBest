package com.specikman.petbest.presentation.main_screen.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.specikman.petbest.R
import com.specikman.petbest.presentation.ui.theme.HomeTheme

@Composable
fun Home() {
    HomeTheme {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
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
            AppBar()
            Content()
        }
    }
}

@Composable
fun AppBar() {

    var searchValue = remember { mutableStateOf("")}

    Row(
        Modifier
            .padding(16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextField(
            value = "",
            onValueChange = {searchValue.value = it},
            label = { Text(text = "Tìm kiếm", fontSize = 12.sp) },
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
        IconButton(onClick = {}) {
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

@Composable
fun Content() {
    Column {
        Header()
        Spacer(modifier = Modifier.height(16.dp))
        Promotions()
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection()
        Spacer(modifier = Modifier.height(16.dp))
        BestSellerSection()
        BestSellerSection()
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun Header() {
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
            QrButton()

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
                    Text(text = "Tiền trong tài khoản", color = MaterialTheme.colors.primary, fontSize = 12.sp)
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

@Composable
fun QrButton() {
    IconButton(
        onClick = {},
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
fun CategorySection() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Danh Mục", style = MaterialTheme.typography.h6)
            TextButton(onClick = {}) {
                Text(text = "More", color = MaterialTheme.colors.primary)
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryButton(
                text = "Fruits",
                icon = painterResource(id = R.drawable.ic_orange),
                backgroundColor = Color(0xffFEF4E7)
            )
            CategoryButton(
                text = "Vegetables",
                icon = painterResource(id = R.drawable.ic_veg),
                backgroundColor = Color(0xffF6FBF3)
            )
            CategoryButton(
                text = "Dairy",
                icon = painterResource(id = R.drawable.ic_cheese),
                backgroundColor = Color(0xffFFFBF3)
            )
            CategoryButton(
                text = "Meats",
                icon = painterResource(id = R.drawable.ic_meat),
                backgroundColor = Color(0xffF6E6E9)
            )
        }
    }
}

@Composable
fun CategoryButton(
    text: String = "",
    icon: Painter,
    backgroundColor: Color
) {
    Column(
        Modifier
            .width(72.dp)
            .clickable { }
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
fun BestSellerSection() {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mặt hàng bán chạy", style = MaterialTheme.typography.h6)
            TextButton(onClick = {}) {
                Text(text = "More", color = MaterialTheme.colors.primary)
            }
        }

        BestSellerItems()
    }
}

@Composable
fun BestSellerItems() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.item_lettuce),
                title = "Iceberg Lettuce",
                price = "1.99",
                discountPercent = 10
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.item_apple),
                title = "Apple",
                price = "2.64",
                discountPercent = 5
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.item_meat),
                title = "Meat",
                price = "4.76",
                discountPercent = 20
            )
        }
    }
}

@Composable
fun BestSellerItem(
    title: String = "",
    price: String = "",
    discountPercent: Int = 0,
    imagePainter: Painter
) {
    Card(
        Modifier
            .width(160.dp)
    ) {
        Column(
            Modifier
                .padding(bottom = 32.dp)
        ) {
            Image(
                painter = imagePainter, contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Row {
                    Text(
                        "$${price}",
                        textDecoration = if (discountPercent > 0)
                            TextDecoration.LineThrough
                        else
                            TextDecoration.None,
                        color = if (discountPercent > 0) Color.Gray else Color.Black
                    )
                    if (discountPercent > 0) {
                        Text(text = "[$discountPercent%]", color = MaterialTheme.colors.primary)
                    }
                }
            }
        }
    }
}