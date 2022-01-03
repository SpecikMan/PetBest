package com.specikman.petbest.presentation.left_nav_drawer.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.specikman.petbest.R

@Composable
fun NavigationDrawer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        NavigationItem(
            resId = R.drawable.ic_baseline_home_24,
            text = "Trang chủ",
            topPadding = 145.dp
        ) {}
        NavigationItem(
            resId = R.drawable.ic_outline_person_24,
            text = "Tài khoản",
        ) {}
        NavigationItem(
            resId = R.drawable.ic_baseline_history_24,
            text = "Lịch sử"
        ) {}

        Row(
            modifier = Modifier
                .padding(start = 50.dp, bottom = 87.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Đăng xuất",
                color = Color.White,
                fontSize = 17.sp
            )

            Image(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Logout",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}