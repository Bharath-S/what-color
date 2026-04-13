# WhatColor

An Android app that uses your device camera to identify colours in real time. Point the camera at anything and the app will name the colour, describe it, and show its hex, RGB, and HSL values. A text-to-speech button lets you hear the colour name read aloud.

## Features

- **Live colour detection** — samples a 20×20 pixel region at a movable reticle on the camera preview
- **Named colours** — matches against a database of ~150 named colours across the full spectrum (reds, pinks, oranges, yellows, greens, cyans, blues, purples, browns, greys, blacks, whites, metallics)
- **Colour values** — displays hex code, RGB, and HSL values for the detected colour
- **Confidence indicator** — shows how closely the detected colour matches the named colour
- **Text-to-speech** — tap the speaker icon to have the colour name and description read aloud
- **Freeze mode** — tap the screen to lock the current reading while you inspect it

## Architecture

```
app/src/main/java/com/eldorado/whatcolor/
├── MainActivity.kt              # Entry point, camera permission handling
├── camera/
│   └── ColourAnalyser.kt        # CameraX ImageAnalysis: YUV sampling → RGB/HSL conversion
├── data/
│   ├── ColourDatabase.kt        # Named colour database and nearest-match algorithm
│   └── ColourResult.kt          # Data class for a detected colour result
├── ui/
│   ├── CameraScreen.kt          # Main screen composable with camera preview
│   └── components/
│       ├── BottomPanel.kt       # Result panel: swatch, name, confidence bar, value chips, TTS
│       └── Reticle.kt           # Draggable crosshair overlay
├── viewmodel/
│   └── ColourViewModel.kt       # Holds colour result, sample point, and freeze state
└── ui/theme/                    # Material 3 theme
```

## Requirements

- Android Studio Hedgehog or newer
- Android SDK 36 (compile), minimum SDK 24 (Android 7.0)
- A physical device or emulator with a camera
- JDK 11

## Setup

1. **Clone the repository**

   ```bash
   git clone <repo-url>
   cd whatcolor
   ```

2. **Open in Android Studio**

   File → Open → select the `whatcolor` directory.

3. **Sync Gradle**

   Android Studio will prompt you to sync. Click **Sync Now**, or run:

   ```bash
   ./gradlew build
   ```

4. **Connect a device or start an emulator**

   The app requires camera hardware. Use a physical device or an AVD with a camera configured.

## Building

| Task | Command |
|------|---------|
| Debug APK | `./gradlew assembleDebug` |
| Release APK | `./gradlew assembleRelease` |
| Run tests | `./gradlew test` |
| Install on connected device | `./gradlew installDebug` |

The debug APK is output to `app/build/outputs/apk/debug/app-debug.apk`.

## Permissions

| Permission | Reason |
|------------|--------|
| `CAMERA` | Required to capture the live camera preview for colour sampling |

The app requests the camera permission at runtime on first launch. If denied, a prompt is shown to grant it manually.

## Dependencies

- **Jetpack Compose** + Material 3 — UI
- **CameraX** (core, camera2, lifecycle, view) — camera preview and image analysis
- **AndroidX Lifecycle ViewModel** — state management
- Android's built-in `TextToSpeech` — colour name audio readout
