package com.specikman.petbest.domain.use_case.login_use_cases.forgot_password

import android.content.Context
import android.widget.Toast
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: LoginRepository
){
    operator fun invoke(
        email: String,
        context: Context
    ): Flow<Resource<String>> = flow{
        try{
            emit(Resource.Loading<String>())
            repository.sendForgotPasswordEmail(email = email)
            Toast.makeText(context,"Gửi thành công. Vui lòng kiểm tra mail để khôi phục tài khoản",Toast.LENGTH_LONG).show()
        }catch(e: Exception){
            Toast.makeText(context,"Gửi mail thất bại. Đảm bảo bạn phải đăng kí tài khoản sử dụng email này",Toast.LENGTH_LONG).show()
        }
    }
}