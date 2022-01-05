package com.specikman.petbest.presentation.main_screen.components

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.specikman.petbest.presentation.bottom_nav_bar.BottomNavItem
import com.specikman.petbest.presentation.bottom_nav_bar.components.BottomNavBar
import com.specikman.petbest.presentation.left_nav_drawer.components.NavigationDrawer
import com.specikman.petbest.presentation.navigation.NavigationMain
import com.specikman.petbest.presentation.ui.theme.Orange


@ExperimentalAnimationApi
@Composable
fun MainScreen(
    context: Context
) {
    val navControllerM = rememberNavController()
    var navigateClick by remember { mutableStateOf(false) }
    val offSetAnim by animateDpAsState(targetValue = if (navigateClick) 240.dp else 0.dp)
    val scaleAnim by animateFloatAsState(targetValue = if (navigateClick) 0.7f else 1.0f)
    NavigationDrawer()
    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    when {
                        dragAmount.x < 0 -> navigateClick = !navigateClick
                    }
                }
            }
            .fillMaxSize()
            .scale(scaleAnim)
            .offset(x = offSetAnim)
            .clip(if (navigateClick) RoundedCornerShape(30.dp) else RoundedCornerShape(0.dp))
            .background(Color.White)

    ) {
        Scaffold(
            bottomBar = {
                BottomNavBar(
                    items = listOf(
                        BottomNavItem(
                            name = "Menu",
                            route = "menu",
                            icon = Icons.Default.ArrowBack
                        ),
                        BottomNavItem(
                            name = "Trang chủ",
                            route = "home",
                            icon = Icons.Default.Home
                        ),
                        BottomNavItem(
                            name = "Tất cả mặt hàng",
                            route = "all_products",
                            icon = Icons.Default.Menu
                        ),
                        BottomNavItem(
                            name = "Dịch vụ chăm sóc",
                            route = "search",
                            icon = Icons.Default.CheckCircle
                        )
                    ),
                    navController = navControllerM,
                    onItemClick = {
                        if (it.name == "Menu") {
                            navigateClick = !navigateClick
                        } else
                            navControllerM.navigate(it.route)
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = Orange,
                    content = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Giỏ hàng"
                        )
                    },
                    onClick = { /*TODO*/ })
            }
        ) {
            NavigationMain(navController = navControllerM)
        }
    }
}
