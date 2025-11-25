# 🧠 WellTrack – Personal Health & Nutrition Tracker

WellTrack is a Kotlin Android application developed for the **PROG7314 module (Part 2)**.  
It allows users to sign in with Google, search for food items via barcode, and log meals to track their nutrition. The backend is powered by **Firebase Authentication** and **Firebase Cloud Functions**, while the UI is built with **Jetpack Compose**.

## 🚀 Features

- ✅ **Google Sign-In** (Firebase Authentication)
- ✅ **Biometric Authentication
- ✅ **Jetpack Compose UI**  
- ✅ **Retrofit + Moshi** network layer to call Firebase Functions  
- ✅ **Search food by barcode** and retrieve nutritional info  
- ✅ **Log meals** to Firestore per user  
- ✅ **Settings screen** for basic user preferences  
- ✅ **Unit testing** for API layer using MockWebServer  
- ✅ **Automated build and test pipeline** with GitHub Actions
- ✅ **Room Database for offline meal logging
- ✅ **Multi-language support (English + Afrikaans)

---

## 🧰 Tech Stack
### Frontend / UI
- Kotlin (JDK 17)
- Jetpack Compose
- Material 3
- Navigation Compose

### Backend & Services
- Firebase Authentication
- Firebase Cloud Messaging
- Firestore (future sync)
- Room Database for offline logs
- DataStore Preferences

### Networking
- Retrofit2
- Moshi
- OkHttp Logging Interceptor

### Tools
- Gradle KTS
- GitHub Actions
- Android Studio Ladybug
---

## 🛠 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/welltrack.git
cd welltrack
```

### 2. Open in Android Studio

Just open the folder → Android Studio will auto-sync.

### 3. Build the project

### 4. Run the app on Android device

Minimum Android Version: API 24
Recommended: API 29+
