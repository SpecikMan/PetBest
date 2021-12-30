package com.specikman.petbest.presentation.login.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.R
import com.specikman.petbest.presentation.components.google_button.GoogleButton
import com.specikman.petbest.ui.theme.primaryColor
import com.specikman.petbest.ui.theme.whiteBackground

@Composable
fun LoginPage(
    navController: NavController,
    context: Context,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val state = viewModel.state.value

    val image = painterResource(id = R.drawable.img_login)

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val scrollState = rememberScrollState()

    val isLogin = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {
            Image(image, "Đăng nhập")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .clip(RoundedCornerShape(30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Text(
                    text = "Đăng Nhập",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "Địa chỉ Email") },
                        placeholder = { Text(text = "Địa chỉ Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                    )

                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_password_eye),
                                    "Eye",
                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        label = { Text("Mật khẩu") },
                        placeholder = { Text(text = "Mật khẩu") },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester = focusRequester)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    Row() {
                        Checkbox(checked = isLogin.value, onCheckedChange = {
                            isLogin.value = it
                        })
                        Text(
                            text = "Duy trì đăng nhập",
                            modifier = Modifier
                                .padding(start = 10.dp, top = 2.dp)
                                .clickable {
                                    isLogin.value = !isLogin.value
                                }
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        //Event click
                        onClick = {
                            viewModel.login(
                                email = emailValue.value,
                                password = passwordValue.value
                            )
                            if (auth.currentUser == null) {
                                Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Login Success: UID: ${auth.currentUser?.uid}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "ĐĂNG NHẬP", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(text = "Hoặc")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    GoogleButton(
                        text = "Đăng nhập với Google",
                        loadingText = "Đang xử lý",
                        //Event Click Google
                        onClicked = {
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row() {
                        Text(
                            text = "Tạo tài khoản mới\t",
                            modifier = Modifier.clickable(onClick = {
                                navController.navigate("register_page") {
                                    popUpTo("login_page")
                                    launchSingleTop = true
                                }
                            })
                        )
                        Text(
                            text = "\tKhôi phục tài khoản",
                            modifier = Modifier.clickable(onClick = {
                                navController.navigate("register_page") {
                                    popUpTo("login_page")
                                    launchSingleTop = true
                                }
                            })
                        )
                    }
                }
            }
        }
    }
}























