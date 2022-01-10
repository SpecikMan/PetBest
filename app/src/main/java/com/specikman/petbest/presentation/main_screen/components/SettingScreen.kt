package com.specikman.petbest.presentation.main_screen.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.processphoenix.ProcessPhoenix
import com.specikman.petbest.presentation.navigation.Screen

@Composable
fun SettingScreen(
    context: Context,
    navController: NavController
) {
    Column(
        Modifier.padding(16.dp)
    ) {
        SettingHeader()
        SettingContent(context = context, navController = navController)
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

@Composable
fun SettingContent(
    context: Context,
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier.clickable {
            navController.navigate(Screen.AccountScreen.route)
        }
    ) {
        Icon(imageVector = Icons.Default.Person, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Thông tin tài khoản", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier.clickable {
            navController.navigate(Screen.HistoryScreen.route)
        }
    ) {
        Icon(imageVector = Icons.Default.History, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Lịch sử", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier.clickable {
            navController.navigate(Screen.TransferMoney.route)
        }
    ) {
        Icon(imageVector = Icons.Default.Payment, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Chuyển tiền từ thẻ ghi nợ vào tài khoản", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier.clickable {
            auth.signOut()
            ProcessPhoenix.triggerRebirth(context)
        }
    ) {
        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Đăng xuất", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}