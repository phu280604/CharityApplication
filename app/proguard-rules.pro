# Giữ các lớp và thành phần của Jetpack Compose
-keep class androidx.compose.** { *; }
-keep interface androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Giữ các lớp liên quan đến Activity Compose
-keep class androidx.activity.compose.** { *; }
-keep interface androidx.activity.compose.** { *; }

# Giữ các lớp của Retrofit và các phụ thuộc
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn okio.**
-dontwarn retrofit2.**

# Giữ các lớp của Gson (dùng bởi Retrofit Converter-Gson)
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Giữ các lớp của Hilt
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }
-keep class androidx.hilt.** { *; }
-keep interface androidx.hilt.** { *; }
-dontwarn dagger.hilt.**

# Giữ các lớp của Navigation Compose
-keep class androidx.navigation.** { *; }
-keep interface androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# Giữ các lớp của Firebase
-keep class com.google.firebase.** { *; }
-keep interface com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Giữ các lớp của Coil và uCrop
-keep class io.coil.** { *; }
-keep interface io.coil.** { *; }
-keep class com.yalantis.ucrop.** { *; }
-keep interface com.yalantis.ucrop.** { *; }
-dontwarn io.coil.**
-dontwarn com.yalantis.ucrop.**

# Giữ các lớp của Kotlin Serialization
-keep class org.jetbrains.kotlinx.serialization.** { *; }
-keep interface org.jetbrains.kotlinx.serialization.** { *; }
-keepattributes Annotation,Signature
-keep class * implements org.jetbrains.kotlinx.serialization.KSerializer { *; }
-dontwarn org.jetbrains.kotlinx.serialization.**

# Giữ các lớp của DataStore
-keep class androidx.datastore.** { *; }
-keep interface androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# Giữ các lớp của Accompanist
-keep class com.google.accompanist.** { *; }
-keep interface com.google.accompanist.** { *; }
-dontwarn com.google.accompanist.**

# Giữ các lớp của Kotlin Reflect
-keep class kotlin.reflect.** { *; }
-keep interface kotlin.reflect.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.reflect.**

# Giữ các lớp liên quan đến AndroidX Core và Lifecycle
-keep class androidx.core.** { *; }
-keep interface androidx.core.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep interface androidx.lifecycle.** { *; }
-dontwarn androidx.core.**
-dontwarn androidx.lifecycle.**

# Giữ các annotation và generated code của Hilt
-keep class **_HiltModules { *; }
-keep class **_HiltComponents { *; }
-keep class **_Module { *; }
-keepclassmembers class * { @dagger.hilt.* *; }

# Giữ các lớp Material3
-keep class androidx.compose.material3.** { *; }
-keep interface androidx.compose.material3.** { *; }
-dontwarn androidx.compose.material3.**

# Ngăn ProGuard xóa các thuộc tính và annotation quan trọng
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Giữ các mô hình dữ liệu (data classes) được dùng bởi Gson hoặc Serialization
-keep class com.developing.charityapplication.data.model.** { *; }