import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.Android.plugin)
        classpath(Libs.JetBrains.Kotlin.plugin)
        classpath(Libs.Google.Hilt.plugin)
        classpath(Libs.MikePenz.AboutLibraries.plugin)
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.38.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.create<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {

    fun isNonStable(version: String) =
        listOf("alpha", "beta", "rc", "eap", "-m").any { version.toLowerCase().contains(it) }

    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
