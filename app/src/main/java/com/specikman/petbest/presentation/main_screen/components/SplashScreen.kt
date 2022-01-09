package com.specikman.petbest.presentation.main_screen.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ark.coding.grocery.ui.theme.railwayFamily
import com.specikman.petbest.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        // Customize the delay time
        delay(13000)
        navController.navigate(Screen.MainScreen.route)
    }

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Change the logo
        Column() {
            Text(
                text = "PETBEST",
                fontFamily = railwayFamily,
                fontSize = 45.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.scale(scale = scale.value)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Đang tải dữ liệu",
                    fontFamily = railwayFamily,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.scale(scale = scale.value)
                )
            }
        }


    }
}