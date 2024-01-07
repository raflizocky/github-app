plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.zocky.githubapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zocky.githubapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // UI
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.appcompat:appcompat-resources:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")                // Load picture
    implementation("de.hdodenhof:circleimageview:3.1.0")                   // ImageView
    implementation("androidx.viewpager2:viewpager2:1.0.0")                 // ViewPager
    implementation("androidx.core:core-splashscreen:1.0.1")                // SplashScreen

    // Networking (API)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Testing (Unit & UI Testing)
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("junit:junit:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Database (Room & DataStore)
    ksp("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}