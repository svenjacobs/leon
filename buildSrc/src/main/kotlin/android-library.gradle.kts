/*
 * Léon - The URL Cleaner
 * Copyright (C) 2023 Sven Jacobs
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

@file:Suppress("UnstableApiUsage")

val catalogs = extensions.getByType<VersionCatalogsExtension>()
val libs: VersionCatalog = catalogs.named("libs")

plugins {
	id("com.android.library")
	kotlin("android")
}

android {
	compileSdk = Android.compileSdk

	defaultConfig {
		minSdk = Android.minSdk
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	kotlinOptions {
		jvmTarget = "11"
	}

	buildFeatures {
		compose = true
	}

	composeOptions {
		kotlinCompilerExtensionVersion =
			libs.findVersion("androidx.compose.compiler").get().requiredVersion
	}

	testOptions {
		unitTests.all {
			it.useJUnitPlatform()
		}
	}
}

dependencies {
	api(project(":core-common"))

	val composeBom = platform(libs.findLibrary("androidx.compose.bom").get())
	api(composeBom)
	api(platform(libs.findLibrary("kotlin.bom").get()))

	api(libs.findLibrary("kotlin.stdlib.jdk8").get())
	api(libs.findBundle("androidx.compose").get())

	testApi(libs.findLibrary("kotest.runner.junit5").get())
	testApi(libs.findLibrary("kotest.assertions.core").get())
	testApi(libs.findLibrary("mockk").get())

	androidTestApi(composeBom)
}
