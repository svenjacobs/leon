package com.svenjacobs.app.leon.repository

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.svenjacobs.app.leon.repository.db.CleanerDatabase
import com.svenjacobs.app.leon.repository.db.Converters
import com.svenjacobs.app.leon.repository.db.model.DbSanitizerDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MoshiModule {

    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        converters: Converters,
    ): CleanerDatabase =
        Room
            .databaseBuilder(
                context,
                CleanerDatabase::class.java,
                DB_NAME
            )
            .addTypeConverter(converters)
            .build()

    @Provides
    fun provideParameterDao(database: CleanerDatabase): DbSanitizerDao =
        database.sanitizerDao()

    private const val DB_NAME = "leon"
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CleanerServiceModule {

    @Binds
    abstract fun bindCleanerRepository(
        cleanerRepositoryImpl: CleanerRepositoryImpl,
    ): CleanerRepository
}
