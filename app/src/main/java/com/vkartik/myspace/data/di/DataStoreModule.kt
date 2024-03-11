package com.vkartik.myspace.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.vkartik.myspace.UserStateProtoOuterClass.UserStateProto
import com.vkartik.myspace.data.UserStateProtoSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userStateProtoSerializer: UserStateProtoSerializer,
    ): DataStore<UserStateProto> =
        DataStoreFactory.create(
            serializer = userStateProtoSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("UserStateProto.proto")
        }

    @Provides
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @ApplicationScope
    fun providesCoroutineScope(
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope