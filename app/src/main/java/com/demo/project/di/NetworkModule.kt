package com.demo.project.di

import com.demo.project.network.RepositoryImpl
import com.demo.project.network.RepositoryInterface
import com.demo.project.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Scheduler
import com.demo.project.network.ApiCalls
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    internal fun provideApiCalls(retrofit: Retrofit): ApiCalls {
        return retrofit.create(ApiCalls::class.java)
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(@SchedulersModule.IoScheduler scheduler:Scheduler): Retrofit {
        return RetrofitBuilder(scheduler).retrofit
    }

    @Singleton
    @Provides
    internal fun provideRestRepository(apiCalls: ApiCalls): RepositoryInterface {
        return RepositoryImpl(apiCalls)
    }

}