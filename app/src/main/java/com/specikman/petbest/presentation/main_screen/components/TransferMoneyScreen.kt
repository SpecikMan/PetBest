package com.specikman.petbest.presentation.main_screen.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel

@Composable
fun TransferMoneyScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    context: Context
) {
    val auth = FirebaseAuth.getInstance()
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ){

        item{
            if(!homeViewModel.stateUsers.value.isLoading){
                TransferHeader()
                TransferContent(user = homeViewModel.stateUsers.value.users.first { it.uid == auth.currentUser?.uid }, homeViewModel = homeViewModel, context = context)
            }
        }
    }
}

@Composable
fun TransferHeader() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Thêm tiền vào tài khoản", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TransferContent(
    user: User,
    homeViewModel: HomeViewModel,
    context: Context
) {
    val creditCardNumber = remember{ mutableStateOf(user.creditCard) }
    val balance = remember{ mutableStateOf("") }
    Row() {
        OutlinedTextField(
            value = creditCardNumber.value,
            onValueChange = { creditCardNumber.value = it },
            label = { Text(text = "Số thẻ ghi nợ") },
            placeholder = { Text(text = "Số thẻ ghi nợ") },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row() {
        OutlinedTextField(
            value = balance.value,
            onValueChange = { balance.value = it },
            label = { Text(text = "Số tiền") },
            placeholder = { Text(text = "Số tiền") },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    TagListTransfer(balance = balance)
    Spacer(modifier = Modifier.height(15.dp))
    Row() {
        TabButton(text = "Xác nhận", active = true, modifier = Modifier.weight(1f)) {
            user.apply {
                this.balance = balance.value.toLong()
            }
            homeViewModel.updateUser(user)
            Toast.makeText(context, "Chuyển thành công", Toast.LENGTH_LONG).show()
        }
    }
}
@Composable
fun TagListTransfer(
    balance: MutableState<String>

) {
    LazyRow(
        Modifier.height(40.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterHorizontally)
    ) {
        item {
            TAG(tag = "30.000") {
                balance.value = "30000"
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "50.000") {
                balance.value = "50000"
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "100.000") {
                balance.value = "100000"
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "200.000") {
                balance.value = "200000"
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "500.000") {
                balance.value = "500000"
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "1.000.000") {
                balance.value = "1000000"
            }
            Spacer(modifier = Modifier.width(4.dp))
            TAG(tag = "2.000.000") {
                balance.value = "2000000"
            }

        }
    }
}