package ai.hara.bnvt.di

import ai.hara.bnvt.data.repository.SongRepository
import ai.hara.bnvt.data.service.SongServices
import ai.hara.bnvt.util.AppExecutors
import ai.hara.bnvt.util.network.LiveDataCallAdapterFactory
import ai.hara.bnvt.util.TokenAuthenticator
import ai.hara.bnvt.util.getHostURL
import ai.hara.bnvt.util.network.HostSelectionInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSongRepository(
        songApiServices: SongServices,
        executors: AppExecutors
    ): SongRepository = SongRepository(songApiServices, executors)

    @Provides
    @Singleton
    fun providesSongService(retrofit: Retrofit): SongServices =
        retrofit.create(SongServices::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        return Retrofit.Builder()
            .baseUrl(getHostURL())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .authenticator(TokenAuthenticator())
            .addInterceptor(HostSelectionInterceptor())
        return okHttpClient.build()
    }
}