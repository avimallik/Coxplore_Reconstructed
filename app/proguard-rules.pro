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

##############################################
# BASIC
##############################################

# Keep line numbers for crash reports
-keepattributes SourceFile,LineNumberTable

# Annotations keep
-keepattributes *Annotation*

##############################################
# ACTIVITIES & FRAGMENTS
##############################################

-keep public class * extends androidx.appcompat.app.AppCompatActivity
-keep public class * extends androidx.fragment.app.Fragment

##############################################
# VOLLEY
##############################################

-keep class com.android.volley.** { *; }

##############################################
# PICASSO
##############################################

-dontwarn com.squareup.picasso.**
-keep class com.squareup.picasso.** { *; }

##############################################
# GOOGLE MAPS
##############################################

-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }
-dontwarn com.google.android.gms.**

##############################################
# ANDROIDX NAVIGATION
##############################################

-keep class androidx.navigation.** { *; }

##############################################
# IMAGE SLIDER (smarteist)
##############################################

-keep class com.smarteist.autoimageslider.** { *; }

##############################################
# NINEOLDANDROID (OLD LIB)
##############################################

-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

##############################################
# SDP (Screen density lib)
##############################################

-dontwarn com.intuit.sdp.**

##############################################
# MULTIDEX
##############################################

-keep class androidx.multidex.** { *; }

##############################################
# KEEP MODEL CLASSES
##############################################

-keep class com.coxplorebd.enigmasystems_armvi.**.model.** { *; }

##############################################
# KEEP CUSTOM VIEWS (if any)
##############################################

-keep class * extends android.view.View {
    <init>(android.content.Context);
    <init>(android.content.Context, android.util.AttributeSet);
}

##############################################
# PREVENT WARNINGS (SAFE)
##############################################

-dontwarn javax.annotation.**

##############################################
# OPTIONAL (TEMP DEBUGGING)
##############################################
# Uncomment if crash
# -dontwarn **

-dontwarn org.conscrypt.**
-dontwarn okhttp3.internal.platform.ConscryptPlatform