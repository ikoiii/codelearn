# CodeLearn Android - Educational Programming App

## Project Overview

CodeLearn Android is a comprehensive educational application for learning web development (HTML, CSS, and JavaScript) on Android devices. The app provides interactive lessons, a code editor with live preview, quizzes, and progress tracking.

## Features

### 🎓 Learning System
- **Interactive Lessons**: HTML, CSS, and JavaScript tutorials with syntax highlighting
- **Progressive Learning**: Structured learning paths from beginner to advanced
- **Code Examples**: Practical examples embedded in lessons
- **Achievement System**: Gamification with badges and milestones

### 💻 Code Editor
- **Multi-file Support**: HTML, CSS, and JavaScript files with tabbed interface
- **Syntax Highlighting**: Powered by Sora Editor
- **Live Preview**: Real-time web preview using WebView
- **Code Templates**: Boilerplate templates and snippets
- **Auto-save**: Prevents loss of work

### 📊 Progress Tracking
- **User Progress**: Track completion status for courses and lessons
- **Learning Streaks**: Daily engagement tracking
- **Achievements**: Unlock badges for milestones
- **Performance Analytics**: Detailed progress insights

### 🎯 Assessment
- **Interactive Quizzes**: Multiple choice and coding challenges
- **Instant Feedback**: Real-time answer validation
- **Score Tracking**: Performance monitoring over time
- **Retake Options**: Practice until mastery

## Technical Architecture

### Architecture Pattern
- **MVVM (Model-View-ViewModel)** with Android Architecture Components
- **Repository Pattern** for data management
- **Clean Architecture** principles

### Core Technologies
- **Language**: Java
- **Target SDK**: Android 8.0+ (API Level 26+)
- **Build Tools**: Gradle 9.2.0
- **JDK**: OpenJDK 17.0.16

### Key Libraries
```gradle
// Android Architecture Components
implementation "androidx.lifecycle:lifecycle-viewmodel:2.7.0"
implementation "androidx.lifecycle:lifecycle-livedata:2.7.0"
implementation "androidx.navigation:navigation-fragment:2.7.5"

// UI/UX
implementation "com.google.android.material:material:1.11.0"
implementation "com.github.bumptech.glide:glide:4.16.0"

// Code Editor
implementation "io.github.rosemoe.sora:editor:0.21.0"

// Database
implementation "androidx.room:room-runtime:2.6.1"

// Animation
implementation "com.airbnb.android:lottie:6.1.0"
```

## Project Structure

```
CodeLearnAndroid/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/codelearn/android/
│   │   │   │   ├── model/           # Data models
│   │   │   │   ├── view/            # UI fragments and activities
│   │   │   │   ├── viewmodel/       # ViewModels
│   │   │   │   ├── repository/      # Data repositories
│   │   │   │   ├── utils/           # Utility classes
│   │   │   │   ├── data/            # Data layer (DAOs, Database)
│   │   │   │   ├── ui/              # UI components by feature
│   │   │   │   ├── adapter/         # RecyclerView adapters
│   │   │   │   └── listener/        # Event listeners
│   │   │   ├── res/                 # Android resources
│   │   │   └── assets/              # Static assets
│   │   ├── test/                    # Unit tests
│   │   └── androidTest/             # Instrumentation tests
│   ├── build.gradle                 # App-level build configuration
│   └── proguard-rules.pro          # ProGuard obfuscation rules
├── keystore/                        # Signing configuration
├── build.gradle                     # Project-level build configuration
├── settings.gradle                  # Gradle settings
└── README.md                        # This file
```

## Database Schema

### Core Entities
- **Course**: Course information and metadata
- **Lesson**: Individual learning units
- **UserProgress**: Tracking user learning progress
- **Quiz**: Assessment and evaluation
- **Achievement**: Gamification elements

## Development Environment Setup

### Prerequisites
- **Android Studio**: Latest stable version
- **JDK**: OpenJDK 17.0.16 ✅ (Verified)
- **Android SDK**: API 26-34
- **Gradle**: Version 8.4 (via Gradle Wrapper)
- **Device**: Android smartphone for USB debugging

### Setup Instructions

1. **Clone the project**
   ```bash
   git clone <repository-url>
   cd CodeLearnAndroid
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the project directory

3. **Configure signing (for release builds)**
   ```bash
   cp keystore/keystore.properties.example keystore/keystore.properties
   # Edit keystore.properties with your keystore information
   ```

4. **Build and run**
   ```bash
   ./gradlew assembleDebug
   # For release:
   ./gradlew assembleRelease
   ```

## Configuration

### Build Variants
- **Debug**: Development build with debugging enabled
- **Release**: Production build with obfuscation enabled

### Key Configuration Files
- `build.gradle`: Dependencies and build configuration
- `proguard-rules.pro`: Code obfuscation rules
- `keystore.properties.example`: Signing configuration template
- `gradle.properties`: Gradle optimization settings

## Environment Verification

### System Requirements Check
✅ **OpenJDK 17.0.16**: Correctly installed and verified
✅ **Gradle 8.4**: Configured via wrapper
✅ **Target SDK 34**: Android 14 compatibility
✅ **Min SDK 26**: Android 8.0+ support
✅ **Architecture Components**: Latest versions integrated

## Getting Started

### First Run
1. Open the project in Android Studio
2. Wait for Gradle synchronization
3. Connect an Android device or start an emulator
4. Click "Run" to build and install the app

### Development Workflow
1. Create feature branches from `main`
2. Implement features following MVVM architecture
3. Write unit tests for ViewModels and repositories
4. Test on real device (not just emulator)
5. Submit pull requests for review

## Testing Strategy

### Unit Tests
- ViewModel logic testing
- Repository layer testing
- Database operation testing
- Utility function testing

### UI Tests
- User flow testing with Espresso
- Fragment navigation testing
- Screen rotation testing
- Accessibility testing

## Performance Requirements

- **App Launch Time**: < 3 seconds
- **Screen Load Time**: < 1.5 seconds
- **Code Editor Response**: < 100ms latency
- **Memory Usage**: < 150MB during normal usage

## Security Considerations

- **Local Storage**: Encrypted SharedPreferences for user data
- **Input Validation**: Sanitization in code editor
- **Network Security**: HTTPS for future API calls
- **Code Obfuscation**: ProGuard enabled for release builds

## Contributing

1. Follow Android architecture guidelines
2. Commit frequently with descriptive messages
3. Test on real devices, not just emulators
4. Monitor memory usage and optimize
5. Collect user feedback early

## License

[Your License Here]

---

**Project Version**: 1.0.0
**Created**: 20 November 2025
**Last Updated**: 20 November 2025
**Compatibility**: OpenJDK 17.0.16 ✓