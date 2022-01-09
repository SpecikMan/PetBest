package com.specikman.petbest.presentation.register.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.R
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.primaryColor
import com.specikman.petbest.presentation.ui.theme.whiteBackground

private lateinit var auth: FirebaseAuth

@Composable
fun RegisterPage(
    navController: NavController,
    context: Context,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    auth = FirebaseAuth.getInstance()

    val image = painterResource(id = R.drawable.img_login)

    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val phoneValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(image, "")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .clip(RoundedCornerShape(30.dp))
                .background(whiteBackground)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "ĐĂNG KÝ", fontSize = 30.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = nameValue.value,
                        onValueChange = { nameValue.value = it },
                        label = { Text(text = "Họ tên") },
                        placeholder = { Text(text = "Họ tên") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "Địa chỉ Email") },
                        placeholder = { Text(text = "Địa chỉ Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    OutlinedTextField(
                        value = phoneValue.value,
                        onValueChange = { phoneValue.value = it },
                        label = { Text(text = "Số điện thoại") },
                        placeholder = { Text(text = "Số điện thoại") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        label = { Text(text = "Mật khẩu") },
                        placeholder = { Text(text = "Mật khẩu") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_password_eye),
                                    "",
                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )

                    OutlinedTextField(
                        value = confirmPasswordValue.value,
                        onValueChange = { confirmPasswordValue.value = it },
                        label = { Text(text = "Nhập lại mật khẩu") },
                        placeholder = { Text(text = "Nhập lại mật khẩu") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_password_eye),
                                    "",
                                    tint = if (confirmPasswordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    //Event click
                    Button(
                        onClick = {
                            if (passwordValue.value == confirmPasswordValue.value) {
                                viewModel.register(
                                    email = emailValue.value.trim(),
                                    password = passwordValue.value.trim(),
                                    name = nameValue.value.trim(),
                                    phone = phoneValue.value.trim(),
                                    context = context,
                                    navController = navController
                                    )
                                navController.navigate(Screen.SplashScreen.route)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Vui lòng nhập lại đúng mật khẩu",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "ĐĂNG KÝ", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "Đăng Nhập",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.RegisterScreen.route)
                                launchSingleTop = true
                            }
                        })
                    )
                }
            }
        }
    }
}






















