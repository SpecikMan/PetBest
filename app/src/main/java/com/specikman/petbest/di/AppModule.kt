package com.specikman.petbest.di

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.data.repository.LoginRepositoryImpl
import com.specikman.petbest.data.repository.RegisterRepositoryImpl
import com.specikman.petbest.domain.repository.LoginRepository
import com.specikman.petbest.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAPI() : FirebaseAPI{
        return FirebaseAPI()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: FirebaseAPI): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(api : FirebaseAPI): RegisterRepository{
        return RegisterRepositoryImpl(api)
    }
}