# VS Code Android Development Setup

## Quick Start Commands

### 1. Check Device Connection
```bash
adb devices
```

### 2. Clean Build
```bash
./gradlew clean
```

### 3. Build Debug APK
```bash
./gradlew assembleDebug
```

### 4. Install APK to Device
```bash
./gradlew installDebug
# atau manual:
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### 5. Launch App
```bash
adb shell am start -n com.codelearn.android/.MainActivity
```

### 6. View Logs
```bash
adb logcat -v color
# atau filter by app:
adb logcat | grep "com.codelearn.android"
```

### 7. Clear App Data
```bash
adb shell pm clear com.codelearn.android
```

### 8. Uninstall App
```bash
adb uninstall com.codelearn.android
```

## VS Code Tasks (Ctrl+Shift+P → "Tasks: Run Task")

- `gradle: clean` - Clean build cache
- `gradle: assembleDebug` - Build debug APK
- `gradle: installDebug` - Install APK to device
- `Build and Install APK` - Complete build and install process
- `ADB: Check Devices` - List connected devices
- `ADB: Logcat` - View device logs
- `ADB: Install APK` - Manual APK installation

## Debug Configuration

Press F5 or go to Run and Debug panel to:
- Debug Android App (attach to running app)
- Launch App on Device

## File Structure

```
.vscode/
├── extensions.json    # Recommended extensions
├── settings.json      # Workspace settings
├── tasks.json         # Build and ADB tasks
└── launch.json        # Debug configurations
```

## Troubleshooting

### Build Issues
- Run `./gradlew clean assembleDebug --stacktrace` for detailed errors
- Check Android SDK installation: `echo $ANDROID_HOME`
- Verify Java version: `java -version`

### Device Issues
- Enable USB Debugging in Developer Options
- Install device drivers if needed
- Try different USB cable/port
- Restart ADB: `adb kill-server && adb start-server`

### Common VS Code Issues
- Reload VS Code window: Ctrl+Shift+P → "Developer: Reload Window"
- Clear Java cache: Ctrl+Shift+P → "Java: Clean Workspace"
- Check Android Studio for SDK installation

## App Features to Test

1. **Home Screen**
   - Progress cards display
   - Recent courses horizontal scroll
   - Achievements showcase
   - Quick access buttons

2. **Course List**
   - Search functionality
   - Filter by category (HTML/CSS/JS)
   - Sort by title/difficulty/popularity
   - Pull-to-refresh
   - Course cards with progress

3. **Course Detail**
   - Collapsing toolbar with hero image
   - Theory/Practice/Quiz tabs
   - Enrollment state management
   - Share functionality
   - Learning outcomes and requirements

## Development Tips

- Use `adb logcat -v color` in VS Code terminal for real-time logs
- Use VS Code Java debugger for breakpoints
- Modify layouts in XML files and hot reload by reinstalling APK
- Test on multiple device screen sizes if possible