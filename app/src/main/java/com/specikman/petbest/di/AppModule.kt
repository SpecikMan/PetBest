package com.specikman.petbest.di

import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.data.repository.*
import com.specikman.petbest.domain.repository.*
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

    @Provides
    @Singleton
    fun provideProductRepository(api: FirebaseAPI): ProductRepository{
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(api: FirebaseAPI): CategoryRepository{
        return CategoryRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideImageRepository(api: FirebaseAPI): ImageRepository{
        return ImageRepositoryImpl(api)
    }
}