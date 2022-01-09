package com.specikman.petbest.presentation.main_screen.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel

@Composable
fun AccountScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    context: Context
) {
    val auth = FirebaseAuth.getInstance()
    LazyColumn(
        Modifier.padding(16.dp)
    ) {
        item {
            if(!homeViewModel.stateProducts.value.isLoading && !homeViewModel.stateUsers.value.isLoading){
                AccountHeader()
                AccountContent(user = homeViewModel.stateUsers.value.users.first { it.uid == auth.currentUser?.uid }, homeViewModel = homeViewModel
                    , context = context)
            }
        }
    }
}

@Composable
fun AccountHeader() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Thông tin tài khoản", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AccountContent(user: User, homeViewModel: HomeViewModel, context: Context) {
    val nameState = remember { mutableStateOf(user.name) }
    val phoneNumberState = remember { mutableStateOf(user.phone) }
    val creditCardNumber = remember{ mutableStateOf(user.creditCard)}
    Row() {
        Text(text = "Tên tài khoản", fontSize = 20.sp)
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = nameState.value, onValueChange = { nameState.value = it },
            label = { Text(text = "Họ tên") },
            placeholder = { Text(text = "Họ tên") },
            singleLine = true
        )
    }
    Row() {
        Text(text = "Tên tài khoản", fontSize = 20.sp)
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = phoneNumberState.value, onValueChange = { phoneNumberState.value = it },
            label = { Text(text = "Số điện thoại") },
            placeholder = { Text(text = "Số điện thoại") },
            singleLine = true
        )
    }
    Row() {
        Text(text = "Số thẻ ghi nợ", fontSize = 20.sp)
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = creditCardNumber.value, onValueChange = { creditCardNumber.value = it },
            label = { Text(text = "Số thẻ ghi nợ") },
            placeholder = { Text(text = "Số thẻ ghi nợ") },
            singleLine = true
        )
    }
    Row() {
        TabButton(text = "Xác nhận", active = true, modifier = Modifier.weight(1f)) {
            user.apply {
                name = nameState.value
                phone = phoneNumberState.value
                creditCard = creditCardNumber.value
            }
            homeViewModel.updateUser(user)
            Toast.makeText(context, "Sửa thông tin thành công", Toast.LENGTH_LONG).show()
        }
    }
}