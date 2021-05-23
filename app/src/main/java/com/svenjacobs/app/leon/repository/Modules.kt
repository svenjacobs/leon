/*
 * Léon - The URL Cleaner
 * Copyright (C) 2021 Sven Jacobs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
