# 🧠 WellTrack – Personal Health & Nutrition Tracker

WellTrack is a Kotlin Android application developed for the **PROG7314 module (Part 2)**.  
It allows users to sign in with Google, search for food items via barcode, and log meals to track their nutrition. The backend is powered by **Firebase Authentication** and **Firebase Cloud Functions**, while the UI is built with **Jetpack Compose**.

---

## 📌 Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Firebase Setup](#-firebase-setup)
- [API Endpoints](#-api-endpoints)
- [Testing](#-testing)
- [GitHub Actions](#-github-actions)
- [Demo Video](#-demo-video)
- [References](#-references)

---

## 🚀 Features

- ✅ **Google Sign-In** (Firebase Authentication)  
- ✅ **Jetpack Compose UI**  
- ✅ **Retrofit + Moshi** network layer to call Firebase Functions  
- ✅ **Search food by barcode** and retrieve nutritional info  
- ✅ **Log meals** to Firestore per user  
- ✅ **Settings screen** for basic user preferences  
- ✅ **Unit testing** for API layer using MockWebServer  
- ✅ **Automated build and test pipeline** with GitHub Actions

---

## 🧰 Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose, Navigation Compose  
- **Backend:** Firebase Authentication, Firestore, Cloud Functions  
- **Networking:** Retrofit2, Moshi  
- **Build Tools:** Gradle, Android Studio  
- **CI/CD:** GitHub Actions

---

## 🛠 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/welltrack.git
cd welltrack
