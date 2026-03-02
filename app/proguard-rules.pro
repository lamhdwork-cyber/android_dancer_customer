# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ----------- Common Android rules -----------
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-dontwarn androidx.compose.**

# Koin (DI)
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Retrofit + Gson
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault
-keep interface * {
    @retrofit2.* <methods>;
}
-keep class kotlinx.coroutines.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class com.squareup.moshi.** { *; }
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keep class com.google.gson.** { *; }
-keep class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.kantek.dancer.booking.data.helper.network.model.** { *; }
-keep class com.kantek.dancer.booking.domain.model.** { *; }
# Retrofit API interfaces
-keep interface com.kantek.dancer.booking.data.remote.api.** { *; }
-dontwarn okio.**

# OkHttp3
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-keep class okhttp3.** { *; }

# Firebase
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Firebase Messaging
-keep class com.google.firebase.messaging.FirebaseMessagingService { *; }

# Firebase Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }

# Lottie
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

# Coil (Image loading)
-keep class coil.** { *; }
-dontwarn coil.**

# Glide (used by Fishbun)
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Fishbun
-keep class com.sangcomz.fishbun.** { *; }
-dontwarn com.sangcomz.fishbun.**

# Google Places API
-keep class com.google.android.libraries.places.** { *; }
-dontwarn com.google.android.libraries.places.**

# Socket.IO (Java Client)
-keep class io.socket.** { *; }
-dontwarn io.socket.**
-keep class org.json.** { *; }

# Google Auth
-keep class com.google.android.gms.auth.api.** { *; }
-dontwarn com.google.android.gms.auth.api.**

# Prevent stripping for reflection-based usages
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keepattributes EnclosingMethod
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# For Kotlin coroutines (optional but helps)
-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }

# Prevent stripping of build config
-keep class **.BuildConfig { *; }

# NDK native symbol
-keep class * {
    native <methods>;
}

# Optional: keep all your activities/fragments if you use reflection (e.g. with DI or routing)
# -keep class com.kantek.lawyer.booking.** { *; }

# ----------- End -----------
