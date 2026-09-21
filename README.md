# GiftRelief

A mobile humanitarian assistance and disaster-relief platform for Android, built with **Kotlin** and **Jetpack Compose**. GiftRelief connects donors with relief projects: one-time and recurring donations, multi-currency support, volunteer registration, and project updates.

## Screens

| Onboarding | Login | Home |
|---|---|---|
| Logo, tagline, illustration, "Get Started" / "I already have an account" | Email + password, show/hide password, social sign-in placeholders, "Sign Up" link | Mission statement + core feature list, settings entry point |

Screens are implemented in `app/src/main/java/com/giftrelief/app/ui/screens/`:
`OnboardingScreen.kt`, `LoginScreen.kt`, `RegisterScreen.kt`, `HomeScreen.kt`, `SettingsScreen.kt`.

## Architecture

MVVM, single-Activity, Jetpack Navigation Compose:

```
ui/screens        Composable screens (View)
viewmodel/        AuthViewModel, SettingsViewModel (state + validation)
data/repository/  AuthRepository — wraps every API call, never throws
data/remote/      Retrofit ApiService + RetrofitInstance
data/local/       SessionManager (encrypted token storage), SettingsManager (DataStore)
data/model/       Request/response DTOs
util/             ValidationUtils — shared input validation
navigation/       NavGraph.kt — route graph
```

`ViewModelFactory` is a small manual DI container that wires a `SessionManager`,
`SettingsManager`, and `AuthRepository` into each screen's ViewModel — no
Hilt/Koin dependency required.

## Feature checklist (assignment requirements)

- **User registration and login** — `RegisterScreen` / `LoginScreen` +
  `AuthViewModel` + `AuthRepository`. The plaintext password is sent to the
  backend over HTTPS only (see Security below) and is never written to disk
  or logged on the device; the server is expected to salt + hash it
  (e.g. bcrypt) before storing it.
- **App settings** — `SettingsScreen`: dark mode, push-notification toggle,
  preferred currency (ZAR/USD/EUR), log out. Persisted locally with
  Jetpack DataStore (`SettingsManager`).
- **REST API** — `ApiService` (Retrofit) defines `auth/register`,
  `auth/login`, `donations`, `donations/{id}/donate`, `users/me`. Point
  `BASE_URL` (in `app/build.gradle.kts`) at your hosted backend.
- **App features** — donation data model + `donate()` endpoint, multi-
  currency, anonymous/recurring donation flags, volunteer & relief-project
  placeholders on the Home screen — extend these into full screens as your
  backend grows.
- **User interface** — every text field is validated locally
  (`ValidationUtils`) before any network call; invalid input surfaces an
  inline error message instead of crashing. All repository calls are
  wrapped in try/catch and return a typed `ApiResult`, so network failures
  and bad server responses show a friendly message instead of crashing.
- **Testing** — JUnit unit tests for `ValidationUtils` and `AuthViewModel`
   See
  `app/src/test/`.
- **CI (GitHub Actions)** — `.github/workflows/android-ci.yml` runs unit
  tests and builds a debug APK on every push/PR, plus an emulator job for
  instrumented tests.
- **GitHub** — this repo is structured to commit as-is; see *Getting
  Started* below for the first commit.

## Security notes

- Passwords travel to the server over HTTPS only (`usesCleartextTraffic="false"`
  + a `network_security_config.xml` that trusts only system CAs). The
  server is responsible for hashing (e.g. bcrypt/Argon2) before storage —
  no Android app should do that itself, since anything done client-side
  can be reverse-engineered out of the APK.
- The session/auth **token** returned after login is stored using
  `EncryptedSharedPreferences` (AES-256), not plain `SharedPreferences`, so
  it can't be read by pulling the app's data directory off a rooted device.
- Non-sensitive preferences (theme, currency, notifications) are kept
  separately in DataStore.

## Getting started

1. Open the project folder in **Android Studio (Koala or newer)** and let
   it sync Gradle. Android Studio will generate the `gradle-wrapper.jar`
   automatically the first time it syncs (this repo ships
   `gradle/wrapper/gradle-wrapper.properties`, which pins Gradle 8.7, but
   not the binary jar itself — normal for a hand-authored project).
2. Set your backend URL in `app/build.gradle.kts`:
   ```kotlin
   buildConfigField("String", "BASE_URL", "\"https://your-api.example.com/api/\"")
   ```
3. Run on an emulator or device (minSdk 24 / targetSdk 34).

### Running tests locally

```bash
./gradlew testDebugUnitTest        # unit tests
./gradlew connectedDebugAndroidTest # instrumented tests (device/emulator required)
```

## Tech stack

Kotlin · Jetpack Compose · Material 3 · Navigation Compose · Retrofit +
OkHttp + Gson · Jetpack DataStore · AndroidX Security Crypto (encrypted
prefs) · JUnit4 + kotlinx-coroutines-test

## Next steps to extend

- Wire real Google/Apple sign-in SDKs into the two social buttons on
  `LoginScreen` (currently no-op placeholders).
- Build out a donations list/detail screen backed by `getDonations()` /
  `donate()`.
- Add a volunteer-registration and relief-project-updates screen.
- Add Room for offline caching of donations/projects (the "Offline Access
  & Sync" feature listed on the Home screen is a placeholder for this).
