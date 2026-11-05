# GiftRelief

GiftRelief is an Android application concept for humanitarian assistance and disaster relief. This repository contains the current Kotlin and Jetpack Compose application source code.

> **Development status:** This is a work in progress. Some integrations and screens are placeholders. In particular, the API base URL must be replaced with a real deployed backend, and social sign-in, offline synchronisation, push notifications, cloud storage and complete volunteer/donation workflows must be implemented and tested before they can be described as finished.

## Technology

- Kotlin 1.9.24
- Android Gradle Plugin 8.7.3
- Gradle 8.9
- Jetpack Compose and Material 3
- Navigation Compose
- Retrofit, OkHttp and Gson
- Jetpack DataStore
- JUnit 4 and kotlinx-coroutines-test

## Project structure

```text
app/src/main/          Android manifest, Kotlin source and resources
app/src/test/          Local JVM unit tests
app/src/androidTest/   Android instrumented tests
.github/workflows/     GitHub Actions CI workflow
```

## Requirements

- Android Studio (a recent stable release)
- JDK 17
- Android SDK Platform 35
- Gradle 8.9 (the Gradle wrapper is recommended)

## First-time setup

1. Clone this repository.
2. Open the repository root folder in Android Studio.
3. Set the Gradle JDK to JDK 17 in Android Studio settings.
4. Install Android SDK Platform 35 using **Tools → SDK Manager**.
5. If the Gradle wrapper scripts are missing, install Gradle 8.9 and run `gradle wrapper --gradle-version 8.9 --distribution-type bin` from the repository root. Commit `gradlew`, `gradlew.bat` and `gradle/wrapper/gradle-wrapper.jar`.
6. Sync the project with Gradle.
7. Replace the placeholder `BASE_URL` in `app/build.gradle.kts` with your actual backend URL before testing network-dependent features.
8. Run the app on an emulator or Android device.

Do not commit `local.properties`; it contains a machine-specific Android SDK path.

## Build and test

With the Gradle wrapper on Windows:

```powershell
.\\gradlew.bat :app:testDebugUnitTest
.\\gradlew.bat :app:assembleDebug
.\\gradlew.bat :app:installDebug
```

On macOS/Linux:

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

Instrumented tests require a running emulator or connected Android device:

```bash
./gradlew :app:connectedDebugAndroidTest
```

## Continuous integration

GitHub Actions workflow: `.github/workflows/android-ci.yml`.

It checks out the repository, configures JDK 17 and the Android SDK, runs local unit tests, builds a debug APK and uploads the APK as a workflow artifact.

## Security and configuration notes

- Never commit passwords, API keys, signing keys, keystores or machine-specific `local.properties` files.
- The app's backend is responsible for securely hashing passwords; client-side password handling is not a substitute for server-side authentication security.
- Confirm that token storage and all API calls meet your assignment's security requirements before release.
- The current API URL is a placeholder and will not connect to a real service until configured.

## Current feature status

Check the implementation before marking a feature complete. The presence of a screen, model or button does not by itself mean its backend integration or full workflow is operational.
