package com.specikman.petbest.presentation.main_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingScreen() {
    Column(
        Modifier.padding(16.dp)
    ) {
        SettingHeader()
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row() {
        Icon(imageVector = Icons.Default.Person, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Thông tin tài khoản", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row() {
        Icon(imageVector = Icons.Default.History, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Lịch sử", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row() {
        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Đăng xuất", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SettingHeader() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Hệ thống", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}