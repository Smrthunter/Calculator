 
 ├── build.gradle
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
