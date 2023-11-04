// Top-level build file where you can add configuration options common to all sub-projects/modules.
// plugins {
//     id("com.android.application") version "8.1.1" apply false
//     id("org.jetbrains.kotlin.android") version "1.8.10" apply false
// }

plugins {
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
}

apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")