package com.specikman.petbest.presentation.main_screen.components

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.specikman.petbest.R
import com.specikman.petbest.common.Constants.AppBarCollapsedHeight
import com.specikman.petbest.common.Constants.AppBarExpendedHeight
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.ui.theme.Pink
import com.specikman.petbest.presentation.ui.theme.Shapes
import com.specikman.petbest.presentation.ui.theme.Transparent
import com.specikman.petbest.presentation.ui.theme.primaryColor
import kotlin.math.max
import kotlin.math.min

@Composable
fun ProductDetail(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val scrollState = rememberLazyListState()
    val product = viewModel.stateProductDetail.value
    Box {
        Content(product, scrollState)
        ParallaxToolbar(product, scrollState, viewModel.stateImages.value.images.first { it.image == product.image }.bitmap,
        navController = navController)
    }
}

@Composable
fun ParallaxToolbar(product: Product, scrollState: LazyListState, bitmap: Bitmap, navController: NavController) {
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    val maxOffset =
        with(LocalDensity.current) { imageHeight.roundToPx() } - LocalWindowInsets.current.systemBars.layoutInsets.top

    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)

    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = White,
        modifier = Modifier
            .height(
                AppBarExpendedHeight
            )
            .offset { IntOffset(x = 0, y = -offset) },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            Box(
                Modifier
                    .height(imageHeight)
                    .graphicsLayer {
                        alpha = 1f - offsetProgress
                    }) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    Pair(0.4f, Transparent),
                                    Pair(1f, White)
                                )
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        product.category,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clip(Shapes.small)
                            .background(LightGray)
                            .padding(vertical = 6.dp, horizontal = 16.dp)
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(AppBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    product.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)
                )

            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(AppBarCollapsedHeight)
            .padding(horizontal = 16.dp)
    ) {
        CircularButton(R.drawable.ic_arrow_back){
            navController.popBackStack()
        }
        CircularButton(R.drawable.ic_favorite)
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResouce: Int,
    color: Color = Gray,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(backgroundColor = White, contentColor = color),
        elevation = elevation,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(painterResource(id = iconResouce), null)
    }
}

@Composable
fun Content(product: Product, scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpendedHeight), state = scrollState) {
        item {
            BasicInfo(product)
            Description(product)
            ServingCalculator()
            IngredientsHeader()
            DescriptionAndHowToUse(product)
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun DescriptionAndHowToUse(product: Product) {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "Mô tả",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = product.description,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Lợi ích chính",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = product.benefit,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        )
        if(product.how_to_use.isNotEmpty()){
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Hướng dẫn sử dụng",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = product.benefit,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Composable
fun IngredientsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(Shapes.medium)
            .background(LightGray)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton("Thêm vào giỏ hàng", true, Modifier.weight(1.2f))
        TabButton("Xuất QR", false, Modifier.weight(1f))
    }
}

@Composable
fun TabButton(text: String, active: Boolean, modifier: Modifier) {
    Button(
        onClick = { /*TODO*/ },
        shape = Shapes.medium,
        modifier = modifier.fillMaxHeight(),
        elevation = null,
        colors = if (active) ButtonDefaults.buttonColors(
            backgroundColor = Pink,
            contentColor = White
        ) else ButtonDefaults.buttonColors(
            backgroundColor = LightGray,
            contentColor = DarkGray
        )
    ) {
        Text(text)
    }
}

@Composable
fun ServingCalculator() {
    var value by remember { mutableStateOf(0) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(Shapes.large)
            .background(LightGray)
            .padding(horizontal = 16.dp)
    ) {

        Text(text = "Số lượng", Modifier.weight(1f), fontWeight = FontWeight.Medium)
        CircularButton(
            iconResouce = R.drawable.ic_minus,
            elevation = null,
            color = Pink
        ) { value-- }
        Text(text = "$value", Modifier.padding(16.dp), fontWeight = FontWeight.Medium)
        CircularButton(iconResouce = R.drawable.ic_plus, elevation = null, color = Pink) { value++ }
    }
}

@Composable
fun Description(product: Product) {
    Row(
    ) {
        Text(
            ToMoneyFormat.toMoney(product.price),
            textDecoration = if (product.discount > 0)
                TextDecoration.LineThrough
            else
                TextDecoration.None,
            color = if (product.discount > 0) Color.Gray else Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 30.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium
        )
        if (product.discount > 0) {
            Text(text = "[${product.discount}%]", color = primaryColor,fontSize = 30.sp)
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = if (product.stock > 0) "Còn hàng" else "Hết hàng",
        color = if (product.stock > 0) Color.Green else Color.Red,
        modifier = Modifier.padding(horizontal = 16.dp),
        fontSize = 22.sp,
    )
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun BasicInfo(product: Product) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

    }
}

