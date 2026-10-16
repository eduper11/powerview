import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val keystoreProperties = Properties().apply {
    val propertiesFile = rootProject.file("keystore.properties")
    if (propertiesFile.isFile) {
        propertiesFile.inputStream().use { load(it) }
    }
}

fun releaseCredential(name: String): String =
    System.getenv(name)?.takeIf { it.isNotBlank() }
        ?: keystoreProperties.getProperty(name).orEmpty()

val releaseKeystoreFile = releaseCredential("KEYSTORE_FILE")
val releaseKeystorePassword = releaseCredential("KEYSTORE_PASSWORD")
val releaseKeyAlias = releaseCredential("KEY_ALIAS")
val releaseKeyPassword = releaseCredential("KEY_PASSWORD")
val hasReleaseSigning = listOf(
    releaseKeystoreFile,
    releaseKeystorePassword,
    releaseKeyAlias,
    releaseKeyPassword,
).all { it.isNotBlank() }

val powerviewVersionName = providers.gradleProperty("powerview.versionName").orNull
    ?: error("Set powerview.versionName in gradle.properties, for example 1.0.0")
val powerviewVersionCode = providers.gradleProperty("powerview.versionCode").orNull?.toIntOrNull()
    ?: error("Set powerview.versionCode in gradle.properties to a positive integer")

require(powerviewVersionName.matches(Regex("""\d+\.\d+\.\d+"""))) {
    "powerview.versionName must be major.minor.patch, for example 1.0.1"
}
require(powerviewVersionCode > 0) {
    "powerview.versionCode must be greater than 0"
}

android {
    namespace = "com.powerview"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.powerview"
        minSdk = 23
        targetSdk = 34
        versionCode = powerviewVersionCode
        versionName = powerviewVersionName
    }

    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                storeFile = rootProject.file(releaseKeystoreFile)
                storePassword = releaseKeystorePassword
                keyAlias = releaseKeyAlias
                keyPassword = releaseKeyPassword
            }
        }
    }

    buildTypes {
        release {
            if (hasReleaseSigning) {
                signingConfig = signingConfigs.getByName("release")
            }
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.karoo.ext)
    implementation(libs.kotlinx.coroutines.android)
}
