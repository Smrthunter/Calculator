# CalcPro — Android Calculator Application
## Group Assignment Report

---

## 1. Introduction

This report documents the design, development, and testing of **CalcPro**, a simple yet feature-rich Android calculator application built as a group assignment for Android Mobile Application Development. The application demonstrates core Android development concepts including XML layout design, widget usage, Java event handling, resource management, and team-based software development.

CalcPro implements all required basic arithmetic operations while also delivering three bonus scientific functions (square root, square, and percentage), a dark-mode colour scheme, smooth result animations, and a persistent calculation history — all within a single-screen interface.

---

## 2. Objectives

The primary objectives of this project were to:

- Design a clean, modern Android user interface using XML layouts.
- Use `EditText`, `TextView`, and `Button` widgets effectively.
- Handle button click events in Java to trigger arithmetic calculations.
- Perform and display the results of Addition, Subtraction, Multiplication, and Division.
- Implement robust error handling for division by zero and invalid inputs.
- Organise application resources using `strings.xml`, `colors.xml`, and drawable XML files.
- Apply a consistent custom colour theme and user-friendly layout.
- Demonstrate teamwork by dividing responsibilities across three defined roles.
- Earn bonus marks by implementing scientific functions, a dark theme, and calculation history.

---

## 3. Group Members and Roles

| Member | Role | Key Deliverables |
|--------|------|-----------------|
| Member A | UI/UX Designer | `activity_main.xml`, `colors.xml`, drawable XML files |
| Member B | Application Logic Developer | `MainActivity.java` |
| Member C | Resource Manager & Tester | `strings.xml`, `TESTING_REPORT.md`, `themes.xml` |

### Role Details

**Member A — UI/UX Designer**
Responsible for the entire visual presentation of the application. Designed the dark-theme layout using `LinearLayout` inside a `ScrollView` for scroll support on small screens. Created all five drawable XML files for button states (pressed vs normal), input field styling, and the result card border. Applied a deep navy and accent-red colour palette defined in `colors.xml`.

**Member B — Application Logic Developer**
Responsible for all Java logic in `MainActivity.java`. Implemented input retrieval and validation, four arithmetic operations as private methods, three scientific operations, error display, a pop-in animation for results using `AnimatorSet` and `ObjectAnimator`, and a rolling 8-entry history log.

**Member C — Resource Manager & Tester**
Responsible for all string resources in `strings.xml`, application theming in `themes.xml`, and comprehensive testing. Conducted 26 structured test cases covering basic operations, edge cases, error handling, scientific functions, and UI behaviour. Documented two medium-severity bugs (both resolved) and five suggestions for future improvement.

---

## 4. Application Features

### 4.1 Core Features
- Accepts two numeric inputs (supports decimals and negative numbers).
- Four operator buttons: `+`, `−`, `×`, `÷`.
- Result displayed in a styled card with a pop-in animation.
- Clear button resets all fields and history.
- Division by zero error displayed in accent red.
- Invalid/empty input error handling.

### 4.2 Bonus Features Implemented (+5 Marks)
| Feature | Implementation |
|---------|---------------|
| √x (Square Root) | `Math.sqrt()`, with negative-input guard |
| x² (Square) | `num1 * num1` |
| % (Percent) | `num1 / 100 * num2` (or `num1 / 100` standalone) |
| Dark Mode | Deep navy/red colour palette in `colors.xml` and `themes.xml` |
| Calculator History | Rolling `TextView` log of last 8 calculations |

---

## 5. Architecture & File Structure

```
CalcPro/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/calculator/app/
│       │   └── MainActivity.java          ← Logic developer
│       └── res/
│           ├── drawable/
│           │   ├── bg_button_number.xml
│           │   ├── bg_button_operator.xml
│           │   ├── bg_button_clear.xml
│           │   ├── bg_scientific_btn.xml
│           │   ├── bg_input_field.xml
│           │   └── bg_result_card.xml
│           ├── layout/
│           │   └── activity_main.xml      ← UI designer
│           └── values/
│               ├── colors.xml             ← UI designer
│               ├── strings.xml            ← Resource manager
│               └── themes.xml             ← Resource manager
├── build.gradle
├── settings.gradle
├── TESTING_REPORT.md
└── REPORT.md
```

---

## 6. Challenges Encountered

### 6.1 Negative Number Input
**Challenge:** Android's default `numberDecimal` input type does not allow a leading minus sign.  
**Solution:** Changed to `numberDecimal|numberSigned` in the `EditText` XML attributes.

### 6.2 Scientific Notation in Results
**Challenge:** Java's `Double.toString()` uses scientific notation for very small or large results (e.g. `1.0E-4`), which is unfriendly for users.  
**Solution:** Applied `DecimalFormat("#.##########")` to format all output values consistently.

### 6.3 Button Ripple vs Custom Backgrounds
**Challenge:** Material `Button` components have a built-in ripple/shadow (`stateListAnimator`) that overrides custom backgrounds on some API levels.  
**Solution:** Set `android:stateListAnimator="@null"` on each button to disable the default animator and let our custom selector drawables control visual feedback.

### 6.4 Team Coordination
**Challenge:** Parallel development meant the UI designer's button IDs had to match the logic developer's `findViewById` calls precisely.  
**Solution:** Agreed on IDs upfront (`btnAdd`, `btnSubtract`, `btnMultiply`, `btnDivide`, `btnClear`, `btnSqrt`, `btnSquare`, `btnPercent`) before coding began.

---

## 7. Testing Results Summary

Full details are in `TESTING_REPORT.md`. Key summary:

| Category | Tests | Passed | Failed |
|----------|-------|--------|--------|
| Basic Arithmetic | 8 | 8 | 0 |
| Error Handling | 6 | 6 | 0 |
| Scientific Functions | 6 | 6 | 0 |
| UI/UX | 6 | 6 | 0 |
| **Total** | **26** | **26** | **0** |

All 26 test cases passed on both emulator and physical device.

---

## 8. Conclusion

CalcPro successfully meets all functional requirements of the assignment: it accepts two numeric inputs, supports all four basic arithmetic operations, handles division-by-zero and invalid inputs gracefully, includes a functional Clear button, and uses a custom colour scheme. Beyond the core requirements, the team implemented three scientific operations, a dark-mode theme, result animations, and a calculation history log to earn the bonus marks.

The project was a valuable exercise in Android XML layout design, Java event-driven programming, resource management, and collaborative development. The challenges encountered — particularly around input types and button styling — deepened the team's understanding of Android's widget system and the importance of agreeing on interface contracts before parallel development begins.
