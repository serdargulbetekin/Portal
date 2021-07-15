package com.example.portal.di

import com.example.portal.api.RocketApi
import com.example.portal.modules.rocket.RocketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RocketModule {

    @Provides
    @Singleton
    internal fun provideRocketRepository(rocketApi: RocketApi) = RocketRepository(rocketApi)

}