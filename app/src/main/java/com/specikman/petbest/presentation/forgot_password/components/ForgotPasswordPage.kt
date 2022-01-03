package com.specikman.petbest.presentation.forgot_password.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.specikman.petbest.R
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.whiteBackground


@Composable
fun ForgotPasswordPage(
    navController: NavController,
    context: Context,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val image = painterResource(id = R.drawable.img_login)

    val emailValue = remember{ mutableStateOf("")}

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(whiteBackground),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(image, "")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "KHÔI PHỤC TÀI KHOẢN", fontSize = 30.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "Email") },
                        placeholder = { Text(text = "Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    //Event click
                    Button(
                        onClick = {
                            viewModel.sendForgotPasswordEmail(email = emailValue.value , context = context)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "XÁC NHẬN", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Đăng Nhập",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.ForgotPasswordScreen.route)
                                launchSingleTop = true
                            }
                        })
                    )
                }
            }
        }
    }
}