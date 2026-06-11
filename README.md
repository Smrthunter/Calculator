# CalcPro 🧮

![Android](https://img.shields.io/badge/Android-API%2026%2B-green?logo=android)
![Java](https://img.shields.io/badge/Language-Java-orange?logo=java)
![Status](https://img.shields.io/badge/Tests-26%2F26%20Passing-brightgreen)
![Theme](https://img.shields.io/badge/Theme-Dark%20Mode-1A1A2E?logo=android)

A clean, modern Android calculator with basic arithmetic and scientific functions, built as a group assignment for Android Mobile Application Development.

---

## ✨ Features

| Feature | Details |
|---------|---------|
| ➕ Basic Arithmetic | Addition, Subtraction, Multiplication, Division |
| 🔬 Scientific | Square Root (√), Square (x²), Percentage (%) |
| ⚠️ Error Handling | Division by zero, empty fields, invalid input |
| 🌙 Dark Theme | Deep navy + accent red colour palette |
| 📜 History | Rolling log of last 8 calculations |
| 🎞️ Animations | Pop-in animation on every result |

---

## 📸 Screenshots

> _Add screenshots here after running the app_

---

## 🗂️ Project Structure

```
app/src/main/
├── java/com/calculator/app/
│   └── MainActivity.java        # All arithmetic logic & event handling
└── res/
    ├── layout/
    │   └── activity_main.xml    # UI layout
    ├── drawable/                # Custom button & card backgrounds
    └── values/
        ├── colors.xml           # Colour palette
        ├── strings.xml          # All string resources
        └── themes.xml           # App theme
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Giraffe (2022.3.1) or later
- Android SDK API 26+
- JDK 8+

### Installation

1. **Clone the repo**
   ```bash
   git clone https://github.com/YOUR_USERNAME/CalcPro.git
   cd CalcPro
   ```

2. **Open in Android Studio**
   - File → Open → select the `CalcPro` folder
   - Wait for Gradle sync to complete

3. **Run**
   - Select an emulator (API 26+) or connect a physical device
   - Click ▶ Run

---

## 👥 Group Members & Roles

| Name | Role | Deliverables |
|------|------|-------------|
| Member A | UI/UX Designer | `activity_main.xml`, `colors.xml`, drawable files |
| Member B | Logic Developer | `MainActivity.java` |
| Member C | Resource Manager & Tester | `strings.xml`, `themes.xml`, `TESTING_REPORT.md` |

---

## 🧪 Testing

26 test cases covering arithmetic, edge cases, error handling, scientific functions, and UI behaviour — all passing.

See [`TESTING_REPORT.md`](TESTING_REPORT.md) for the full test matrix.

---

## 📄 Report

See [`REPORT.md`](REPORT.md) for the full group report including objectives, challenges, and conclusions.

---

## 📜 License

For academic use only.
