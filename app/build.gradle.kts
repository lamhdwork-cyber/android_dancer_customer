import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
}

val keystoreProperties = Properties().apply {
    load(FileInputStream(rootProject.file("keystore-release.properties")))
}

android {
    namespace = "com.kantek.dancer.booking"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kantek.dancer.booking"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 3
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        manifestPlaceholders["PLACE_API_KEY"] = project.findProperty("PLACE_API_KEY") as String
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        val placeApiKey: String = project.findProperty("PLACE_API_KEY") as String
        release {
            buildConfigField("String", "PLACE_API_KEY", placeApiKey)
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "PLACE_API_KEY", placeApiKey)
            isMinifyEnabled = false
        }
    }

    compileOptions {
        val javaVersion = JavaVersion.toVersion(libs.versions.java.get())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

    applicationVariants.all {
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val date = SimpleDateFormat("MMddyy").format(Date())
            output.outputFileName = "nightease-app-${name}-v${versionName}-${versionCode}-${date}.apk"
        }
    }

    lint {
        checkReleaseBuilds = false
    }
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

dependencies {
    // Core Android & Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material.icons.extended)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.webkit)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)

    // Login with Google
    implementation(libs.google.auth)

    // Socket IO
    implementation(libs.socket.io.client)

    // lottie
    implementation(libs.lottie.compose)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // DI & Navigation
    implementation(libs.koin.compose)
    implementation(libs.navigation.compose)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofitConvertGson)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3LoggingInterceptor)

    // Others
    implementation(libs.coroutinesCore)
    implementation(libs.google.accompanist.systemuicontroller)

    // Load image
    implementation(libs.io.coil)

    implementation(libs.glide)
    // Resize image
    implementation(libs.compressor)

    // Search Place
    implementation(libs.google.places)

    // Modules
    implementation(project(":support-persistent"))
    implementation(project(":support-core"))

}