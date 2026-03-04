import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
}

val keystoreProperties = Properties().apply {
    load(FileInputStream(rootProject.file("keystore-release.properties")))
}

android {
    namespace = "com.kantek.dancer.booking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kantek.dancer.booking"
        minSdk = 21
        targetSdk = 35
        versionCode = 4
        versionName = "1.1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

    applicationVariants.all {
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val date = SimpleDateFormat("MMddyy").format(Date())
            output.outputFileName = "dancer-customer-app-${name}-v${versionName}-${versionCode}-${date}.apk"
        }
    }

    lint {
        checkReleaseBuilds = false
    }
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
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.webkit)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.messaging.ktx)

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
    implementation(libs.material.icons.extended)

    // Load image
    implementation(libs.io.coil)

    // Fishbun using glide lib
    implementation(libs.fishbun)
    implementation(libs.glide)
    implementation(libs.glide.compiler)
    // Resize image
    implementation(libs.compressor)

    // Search Place
    implementation(libs.google.places)

    // Modules
    implementation(project(":support-persistent"))
    implementation(project(":support-core"))

}