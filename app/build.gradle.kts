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

import com.adarshr.gradle.testlogger.theme.ThemeType.STANDARD
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.parcelize")
    id("dagger.hilt.android.plugin")
    id("com.github.triplet.play") version "3.6.0"
    id("com.mikepenz.aboutlibraries.plugin")
    id("com.adarshr.test-logger") version "3.0.0"
}

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "com.svenjacobs.app.leon"
        minSdk = 21
        targetSdk = 31
        versionCode = 227
        versionName = "0.6.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    signingConfigs {
        val signingFile = rootProject.file("signing.properties")
        if (!signingFile.exists()) {
            logger.warn("No signing properties found. Release signing not possible.")
            return@signingConfigs
        }

        create("release") {
            val props = Properties()
            signingFile.inputStream().use { props.load(it) }

            storeFile = rootProject.file("upload-keystore.jks")
            storePassword = props.getProperty("storePassword")
            keyPassword = props.getProperty("keyPassword")
            keyAlias = "upload"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

play {
    serviceAccountCredentials.set(rootProject.file("google-play-service-account.json"))
    defaultToAppBundles.set(true)
}

testlogger {
    theme = STANDARD
}

dependencies {
    implementation(Libs.JetBrains.Kotlin.stdlib)
    implementation(Libs.AndroidX.Core.ktx)
    implementation(Libs.AndroidX.AppCompat.appcompat)
    implementation(Libs.AndroidX.Activity.ktx)
    implementation(Libs.Google.Material.material)

    //region Compose
    implementation(Libs.AndroidX.Activity.compose)
    implementation(Libs.AndroidX.Lifecycle.viewModelCompose)
    implementation(Libs.AndroidX.ConstraintLayout.compose)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.uiTooling)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Navigation.compose)
    implementation(Libs.AndroidX.Hilt.navigationCompose)
    implementation(Libs.Google.Accompanist.insets)
    //endregion

    implementation(Libs.AndroidX.Startup.runtime)
    implementation(Libs.AndroidX.Lifecycle.runtimeKtx)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.AndroidX.DataStore.preferences)
    implementation(Libs.JetBrains.KotlinX.Coroutines.android)
    implementation(Libs.Google.Hilt.android)
    implementation(Libs.AndroidX.Room.ktx)
    implementation(Libs.AndroidX.Browser.browser)
    implementation(Libs.JakeWharton.Timber.timber)
    implementation(Libs.Square.Moshi.moshi)
    implementation(Libs.MikePenz.AboutLibraries.ui)

    kapt(Libs.Google.Hilt.androidCompiler)
    kapt(Libs.AndroidX.Room.compiler)

    testImplementation(Libs.Test.Kotest.runnerJunit5)
    testImplementation(Libs.Test.Kotest.assertionsCore)
    testImplementation(Libs.Test.MockK.mockk)
}
