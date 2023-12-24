package ai.hara.bnvt.di

import ai.hara.bnvt.data.repository.PlaylistRepository
import ai.hara.bnvt.data.repository.SongRepository
import ai.hara.bnvt.data.repository.UserRepository
import ai.hara.bnvt.data.service.PlaylistService
import ai.hara.bnvt.data.service.SongService
import ai.hara.bnvt.data.service.UserService
import ai.hara.bnvt.util.AppExecutors
import ai.hara.bnvt.util.network.LiveDataCallAdapterFactory
import ai.hara.bnvt.util.TokenAuthenticator
import ai.hara.bnvt.util.getHostURL
import ai.hara.bnvt.util.network.AuthorizationInterceptor
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providePlaylistRepository(
        playlistApiServices: PlaylistService,
        executors: AppExecutors
    ): PlaylistRepository = PlaylistRepository(playlistApiServices, executors)


    @Provides
    @Singleton
    fun provideSongRepository(
        songApiServices: SongService,
        executors: AppExecutors
    ): SongRepository = SongRepository(songApiServices, executors)

    @Provides
    @Singleton
    fun provideUserRepository(
        userApiServices: UserService,
        executors: AppExecutors
    ): UserRepository = UserRepository(userApiServices, executors)


    @Provides
    @Singleton
    fun providesSongService(retrofit: Retrofit): SongService =
        retrofit.create(SongService::class.java)

    @Provides
    @Singleton
    fun providesPlaylistService(retrofit: Retrofit): PlaylistService =
        retrofit.create(PlaylistService::class.java)


    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }

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
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .authenticator(TokenAuthenticator())
            .addInterceptor(AuthorizationInterceptor(sharedPreferences))
        return okHttpClient.build()
    }
}