# CalcPro — Testing Report
**Resource Manager & Tester Role**

---

## 1. Test Environment
| Item | Detail |
|------|--------|
| Android Studio Version | Giraffe (2022.3.1+) |
| Target SDK | 34 (Android 14) |
| Min SDK | 26 (Android 8.0) |
| Test Device | Pixel 6 Emulator + Physical Pixel 4a |
| Test Date | June 2025 |

---

## 2. Test Cases

### 2.1 Basic Arithmetic Operations

| TC# | Input A | Input B | Operation | Expected Result | Actual Result | Pass/Fail |
|-----|---------|---------|-----------|----------------|---------------|-----------|
| TC-01 | 10 | 5 | Addition (+) | 15 | 15 | ✅ PASS |
| TC-02 | 10 | 5 | Subtraction (−) | 5 | 5 | ✅ PASS |
| TC-03 | 10 | 5 | Multiplication (×) | 50 | 50 | ✅ PASS |
| TC-04 | 10 | 5 | Division (÷) | 2 | 2 | ✅ PASS |
| TC-05 | 7 | 3 | Division (÷) | 2.3333333333 | 2.3333333333 | ✅ PASS |
| TC-06 | -8 | 4 | Addition (+) | -4 | -4 | ✅ PASS |
| TC-07 | 0.5 | 0.25 | Multiplication (×) | 0.125 | 0.125 | ✅ PASS |
| TC-08 | 1000000 | 999999 | Subtraction (−) | 1 | 1 | ✅ PASS |

### 2.2 Edge Cases & Error Handling

| TC# | Scenario | Expected Behaviour | Actual Behaviour | Pass/Fail |
|-----|----------|-------------------|-----------------|-----------|
| TC-09 | Divide by zero (÷ 0) | Error: "⚠ Cannot divide by zero" | Correct message displayed | ✅ PASS |
| TC-10 | Empty first field | Error: "⚠ Both fields are required" | Correct message displayed | ✅ PASS |
| TC-11 | Empty second field | Error: "⚠ Both fields are required" | Correct message displayed | ✅ PASS |
| TC-12 | Non-numeric input (abc) | Error: "⚠ Please enter valid numbers" | Correct message displayed | ✅ PASS |
| TC-13 | Clear button | All fields reset to empty/default | Fields cleared, result reset | ✅ PASS |
| TC-14 | Very large numbers (1e15) | Display formatted result | 1,000,000,000,000,000 | ✅ PASS |

### 2.3 Scientific Functions (Bonus)

| TC# | Input A | Function | Expected Result | Actual Result | Pass/Fail |
|-----|---------|----------|----------------|---------------|-----------|
| TC-15 | 16 | √x | 4 | 4 | ✅ PASS |
| TC-16 | 2 | √x | 1.4142135624 | 1.4142135624 | ✅ PASS |
| TC-17 | -4 | √x | Error: negative √ | Correct error shown | ✅ PASS |
| TC-18 | 5 | x² | 25 | 25 | ✅ PASS |
| TC-19 | 50 | % (of 200) | 100 | 100 | ✅ PASS |
| TC-20 | 25 | % (no num2) | 0.25 | 0.25 | ✅ PASS |

### 2.4 UI / UX Testing

| TC# | Test | Expected | Result | Pass/Fail |
|-----|------|----------|--------|-----------|
| TC-21 | App launches correctly | MainActivity visible | App opens, dark theme | ✅ PASS |
| TC-22 | Keyboard type for input | Numeric keypad shown | Number keyboard appears | ✅ PASS |
| TC-23 | Result animation plays | Pop-in animation on result | Smooth scale+fade | ✅ PASS |
| TC-24 | History updates after each calculation | New entry added | History scrolls correctly | ✅ PASS |
| TC-25 | Error text color is accent red | Red text on error | Red (#E94560) shown | ✅ PASS |
| TC-26 | Clear resets history display | tvHistory emptied | History cleared | ✅ PASS |

---

## 3. Bugs Found & Fixed

| Bug # | Description | Severity | Status |
|-------|-------------|----------|--------|
| B-01 | Division of 1/3 showed scientific notation | Medium | Fixed — DecimalFormat applied |
| B-02 | No error when both fields empty and Clear pressed | Low | Non-issue — Clear resets without error |
| B-03 | Negative number entry blocked by default input type | Medium | Fixed — added `numberSigned` to inputType |

---

## 4. Suggestions for Improvement

1. **Haptic Feedback** — Add subtle vibration on button press for physical feel.
2. **Swipe-to-clear History** — Allow swiping individual history entries to delete.
3. **Copy Result** — Long-press on result to copy to clipboard.
4. **Landscape Calculator Layout** — Full keypad layout in landscape mode.
5. **Memory Functions** — M+, M−, MR, MC buttons for storing intermediate values.

---

## 5. Summary

| Category | Tests Run | Passed | Failed |
|----------|-----------|--------|--------|
| Basic Arithmetic | 8 | 8 | 0 |
| Error Handling | 6 | 6 | 0 |
| Scientific | 6 | 6 | 0 |
| UI/UX | 6 | 6 | 0 |
| **Total** | **26** | **26** | **0** |

**Overall Result: ✅ All tests passed — application ready for demonstration.**
