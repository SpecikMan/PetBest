package com.specikman.petbest.presentation.main_screen.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.common.ToMoneyFormat
import com.specikman.petbest.domain.model.Service
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.navigation.Screen
import com.specikman.petbest.presentation.ui.theme.Orange
import com.specikman.petbest.presentation.ui.theme.Shapes

@Composable
fun PetCare(
    homeViewModel: HomeViewModel = hiltViewModel(),
    context: Context,
    navController: NavController
) {

    if (!homeViewModel.stateServices.value.isLoading) {
        val servicesState =
            remember { mutableStateOf(homeViewModel.stateServices.value.services.filter { it.type == "Cắt tỉa lông" }) }
        val chooseServiceState = remember { mutableStateOf(Service()) }
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                PetCareHeader(
                    homeViewModel = homeViewModel,
                    servicesState = servicesState
                )
                Services(
                    services = servicesState.value.sortedBy { it.name },
                    choose = chooseServiceState
                )
                Description(services = servicesState.value)
                PetCareFooter(
                    serviceChooseState = chooseServiceState,
                    homeViewModel = homeViewModel,
                    context = context,
                    navController = navController
                )
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun PetCareHeader(
    homeViewModel: HomeViewModel,
    servicesState: MutableState<List<Service>>
) {
    auth = FirebaseAuth.getInstance()
    val tabButtonState1 = remember { mutableStateOf(true) }
    val tabButtonState2 = remember { mutableStateOf(false) }
    val tabButtonState3 = remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Dịch vụ chăm sóc", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(Shapes.medium)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TabButton(
            "Cắt tỉa lông", active = tabButtonState1.value,
            Modifier
                .weight(0.4f)
        ) {
            if (!tabButtonState1.value) {
                tabButtonState1.value = !tabButtonState1.value
                tabButtonState2.value = false
                tabButtonState3.value = false
                servicesState.value =
                    homeViewModel.stateServices.value.services.filter { it.type == "Cắt tỉa lông" }
            }
        }
        TabButton(
            text = "Tắm spa",
            active = tabButtonState2.value,
            modifier = Modifier.weight(0.3f)
        ) {
            if (!tabButtonState2.value) {
                tabButtonState1.value = false
                tabButtonState2.value = !tabButtonState2.value
                tabButtonState3.value = false
                servicesState.value =
                    homeViewModel.stateServices.value.services.filter { it.type == "Tắm spa" }
            }
        }
        TabButton(
            text = "Khách sạn",
            active = tabButtonState3.value,
            modifier = Modifier.weight(0.3f)
        ) {
            if (!tabButtonState3.value) {
                tabButtonState1.value = false
                tabButtonState2.value = false
                tabButtonState3.value = !tabButtonState3.value
                servicesState.value =
                    homeViewModel.stateServices.value.services.filter { it.type == "Khách sạn" }
            }
        }
    }

}

@Composable
fun PetCareFooter(
    serviceChooseState: MutableState<Service>,
    homeViewModel: HomeViewModel,
    context: Context,
    navController: NavController
) {
    auth = FirebaseAuth.getInstance()
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 7.dp)
    ) {
        Text(
            text = "Chi phí dịch vụ ${ToMoneyFormat.toMoney(serviceChooseState.value.price)}",
            modifier = Modifier
                .padding(start = 10.dp, top = 2.dp),
            fontSize = 20.sp,
            color = Orange,
            fontWeight = FontWeight.Medium
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 16.dp)
            .clip(Shapes.medium)
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton(text = "Tiến hành thanh toán", active = true, modifier = Modifier.weight(1f)) {
            auth.currentUser?.uid?.let { uid ->
                com.specikman.petbest.domain.model.Order(
                    id = homeViewModel.stateOrders.value.orders.size + 1,
                    productId = serviceChooseState.value.id,
                    userUID = uid,
                    costTotal = serviceChooseState.value.price,
                    type = "Dịch vụ"
                ).also {
                    homeViewModel.addOrder(it)
                }
                Toast.makeText(context, "Mua dịch vụ thành công", Toast.LENGTH_LONG).show()
                navController.navigate(Screen.Home.route)
            }
        }
    }
}

@Composable
fun Services(
    services: List<Service>,
    choose: MutableState<Service>
) {
    Column {
        services.forEach { service ->
            Service(
                service = service,
                choose = choose
            )
        }
    }
}

@Composable
fun Service(
    service: Service,
    choose: MutableState<Service>
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                if (choose.value == service) {
                    choose.value = Service()
                } else {
                    choose.value = service
                }
            }
            .background(if (choose.value == service) Color.LightGray else Color.White)
            .padding(horizontal = 6.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(Modifier.weight(1f)) {
            Text(text = service.name, fontWeight = FontWeight.Bold, fontSize = 23.sp)
            Text(
                text = service.type,
                fontSize = 13.sp,
                color = Color.Gray
            )
            Text(
                text = "Giá tổng: ${ToMoneyFormat.toMoney(service.price)}",
                fontSize = 16.sp,
                color = Orange
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun Description(services: List<Service>) {
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        when (services[0].type) {
            "Cắt tỉa lông" -> {
                Text(
                    text = "Cam kết",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = """Hết mình vì công việc""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Chúng tôi làm việc hết mình với chữ tâm, trách nhiệm và lòng nhiệt huyết. Thú cưng khỏe mạnh là niềm hạnh phúc của chúng tôi.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = """Giá dịch vụ rẻ nhất""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Chúng tôi cam kết đưa ra mức giá tốt nhất so với thị trường để tất cả thú cưng đều có cơ hội được trải nghiệm dịch vụ của chúng tôi""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = """Chất lượng hàng đầu""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Chúng tôi không ngừng nâng cao phát triển trình độ kỹ năng của nhân sự để phục vụ thú cưng những điều tốt đẹp nhất.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Tại sao nên cắt tỉa lông cho chó mèo tại PetBest?",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = """Với đội ngũ nhân viên  được đào tạo về dịch vụ cắt tỉa lông chó mèo chuyên nghiệp và được tham gia nhiều khóa đào tạo chăm sóc thú cưng tại nước ngoài. Với gần 10 năm kinh nghiệm, chúng tôi có thể đáp ứng được hầu hết nhu cầu làm đẹp của các giống vật nuôi.

Chúng tôi hiểu rằng thú cưng của mỗi khách hàng đều có nhu cầu chăm sóc khác nhau. Và cần có những giải pháp phù hợp với từng giống. Dịch vụ của chúng tôi luôn sử dụng những dòng sản phẩm sữa tắm tốt nhất đảm bảo an toàn cho vật nuôi. Với phương pháp chăm sóc toàn diện kết hợp với những kinh nghiệm, kiến thức chuyên sâu. Pet Mart sẽ tư vấn, cung cấp những dịch vụ chăm sóc hiệu quả và chuyên nghiệp nhất.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )

            }
            "Tắm spa" -> {
                Text(
                    text = "Cam kết",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = """Hết mình vì công việc""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Chúng tôi làm việc hết mình với chữ tâm, trách nhiệm và lòng nhiệt huyết. Thú cưng khỏe mạnh là niềm hạnh phúc của chúng tôi.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = """Giá dịch vụ rẻ nhất""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Chúng tôi cam kết đưa ra mức giá tốt nhất so với thị trường để tất cả thú cưng đều có cơ hội được trải nghiệm dịch vụ của chúng tôi""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = """Chất lượng hàng đầu""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Chúng tôi không ngừng nâng cao phát triển trình độ kỹ năng của nhân sự để phục vụ thú cưng những điều tốt đẹp nhất.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Tại sao nên cho chó mèo tắm spa tại PetBest?",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = """Với đội ngũ nhân viên  được đào tạo về dịch vụ cắt tỉa lông chó mèo chuyên nghiệp và được tham gia nhiều khóa đào tạo chăm sóc thú cưng tại nước ngoài. Với gần 10 năm kinh nghiệm, chúng tôi có thể đáp ứng được hầu hết nhu cầu làm đẹp của các giống vật nuôi.

Chúng tôi hiểu rằng thú cưng của mỗi khách hàng đều có nhu cầu chăm sóc khác nhau. Và cần có những giải pháp phù hợp với từng giống. Dịch vụ của chúng tôi luôn sử dụng những dòng sản phẩm sữa tắm tốt nhất đảm bảo an toàn cho vật nuôi. Với phương pháp chăm sóc toàn diện kết hợp với những kinh nghiệm, kiến thức chuyên sâu. Pet Mart sẽ tư vấn, cung cấp những dịch vụ chăm sóc hiệu quả và chuyên nghiệp nhất.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
            "Khách sạn" -> {
                Text(
                    text = "Chế độ chăm sóc và hoạt đông",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = """Chế độ vui chơi thể thao""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Thú cưng sẽ được huấn luyện để nâng cao thể lực, tiêu hóa tốt và giao lưu với các thú cưng khác tại khách sạn hàng ngày. Chỉ áp dụng cho chó, không áp dụng cho mèo.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = """Chế độ vệ sinh""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = """Tắm miễn phí cho thú cưng khi đăng ký gửi tại khách sạn từ 3 ngày trở lên""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Địa chỉ khách sạn chó mèo uy tín",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = """Khách sạn chó mèo PetBest cung cấp những giá trị dịch vụ tốt nhất cho thú cưng trong thời gian gửi tại đây bao gồm: chăm sóc, dinh dưỡng, vệ sinh, tắm rửa, chải chuốt và cả những kế hoạch huấn luyện cơ bản. Tất cả chó mèo khi gửi tại khách sạn của chúng tôi đều được đối xử như thế chúng là thú cưng của chúng tôi vậy. Và chúng tôi sẽ giúp bạn yên tâm nhất khi trao gửi thú cưng cho PetBest.""",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}
