plugins {
    // id("com.android.application")
    // id("org.jetbrains.kotlin.android")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "co.edu.udea.compumovil.gr07_20232.lab2"
    // compileSdk = 33
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "co.edu.udea.compumovil.gr07_20232.lab2"
        // minSdk = 21
        minSdk = libs.versions.minSdk.get().toInt()
        // targetSdk = 33
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // vectorDrawables {
        //     useSupportLibrary = true
        // }
    }

    // new
    signingConfigs {
        // We use a bundled debug keystore, to allow debug builds from CI to be upgradable
        named("debug") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }
    // new

    // buildTypes {
    //     release {
    //         isMinifyEnabled = false
    //         proguardFiles(
    //             getDefaultProguardFile("proguard-android-optimize.txt"),
    //             "proguard-rules.pro"
    //         )
    //     }
    // }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro")
        }
    }


    compileOptions {
        // sourceCompatibility = JavaVersion.VERSION_1_8
        // targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // kotlinOptions {
    //     jvmTarget = "1.8"
    // }

    buildFeatures {
        compose = true
        // new
        buildConfig = true
        // new
    }
    composeOptions {
        // kotlinCompilerExtensionVersion = "1.4.3"
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            // new
            // The Rome library JARs embed some internal utils libraries in nested JARs.
            // We don't need them so we exclude them in the final package.
            excludes += "/*.jar"

            // Multiple dependency bring these files in. Exclude them to enable
            // our test APK to build (has no effect on our AARs)
            // excludes += "/META-INF/AL2.0"
            // excludes += "/META-INF/LGPL2.1"
            // new
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// dependencies {

//     implementation("androidx.core:core-ktx:1.9.0")
//     implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
//     implementation("androidx.activity:activity-compose:1.8.0")
//     implementation(platform("androidx.compose:compose-bom:2023.03.00"))
//     implementation("androidx.compose.ui:ui")
//     implementation("androidx.compose.ui:ui-graphics")
//     implementation("androidx.compose.ui:ui-tooling-preview")
//     implementation("androidx.compose.material3:material3")
//     testImplementation("junit:junit:4.13.2")
//     androidTestImplementation("androidx.test.ext:junit:1.1.5")
//     androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//     androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
//     androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//     debugImplementation("androidx.compose.ui:ui-tooling")
//     debugImplementation("androidx.compose.ui:ui-test-manifest")
// }

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.palette)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.materialWindow)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.window)

    implementation(libs.accompanist.adaptive)

    implementation(libs.coil.kt.compose)

    implementation(libs.okhttp3)
    implementation(libs.okhttp.logging)

    implementation(libs.rometools.rome)
    implementation(libs.rometools.modules)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    ksp(libs.androidx.room.compiler)
    coreLibraryDesugaring(libs.core.jdk.desugaring)
}