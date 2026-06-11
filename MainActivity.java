package com.calculator.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity — single-screen keypad calculator.
 *
 * State machine:
 *   current  — digits being typed right now
 *   prev     — first operand (stored after operator pressed)
 *   op       — pending operator (+, −, ×, ÷)
 *   justCalc — true immediately after = pressed; next digit starts fresh
 */
public class MainActivity extends AppCompatActivity {

    // Display
    private TextView tvResult, tvExpression, tvOpIndicator, tvHistory;

    // Operator buttons (for highlight toggling)
    private Button btnAdd, btnSub, btnMul, btnDiv;

    // Calculator state
    private String current = "0";
    private String prev = "";
    private String op = "";
    private boolean justCalc = false;

    private final List<String> history = new ArrayList<>();
    /** Formatter to show up to 10 decimal places, ensuring leading zeros (e.g., 0.5 instead of .5). */
    private final DecimalFormat df = new DecimalFormat("0.##########");

    // ── Lifecycle ──────────────────────────────────────────────────

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        wireButtons();
    }

    // ── Binding ────────────────────────────────────────────────────

    /**
     * Binds UI components from activity_main.xml to class variables.
     */
    private void bindViews() {
        tvResult      = findViewById(R.id.tvResult);
        tvExpression  = findViewById(R.id.tvExpression);
        tvOpIndicator = findViewById(R.id.tvOpIndicator);
        tvHistory     = findViewById(R.id.tvHistory);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
    }

    /**
     * Attaches click listeners to all keypad buttons.
     */
    private void wireButtons() {
        // Digits
        int[] numIds = {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
                        R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9};
        String[] digits = {"0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i < numIds.length; i++) {
            final String d = digits[i];
            findViewById(numIds[i]).setOnClickListener(v -> { haptic(v); inputDigit(d); });
        }
        // Operators
        findViewById(R.id.btnAdd).setOnClickListener(v -> { haptic(v); setOp("+"); });
        findViewById(R.id.btnSub).setOnClickListener(v -> { haptic(v); setOp("−"); });
        findViewById(R.id.btnMul).setOnClickListener(v -> { haptic(v); setOp("×"); });
        findViewById(R.id.btnDiv).setOnClickListener(v -> { haptic(v); setOp("÷"); });
        // Controls
        findViewById(R.id.btnEq)   .setOnClickListener(v -> { haptic(v); calculate(false); });
        findViewById(R.id.btnClear).setOnClickListener(v -> { haptic(v); clearAll(); });
        findViewById(R.id.btnNeg)  .setOnClickListener(v -> { haptic(v); toggleNeg(); });
        findViewById(R.id.btnDot)  .setOnClickListener(v -> { haptic(v); inputDot(); });
        findViewById(R.id.btnBack) .setOnClickListener(v -> { haptic(v); backspace(); });
        // Scientific
        findViewById(R.id.btnSqrt).setOnClickListener(v -> { haptic(v); doSci("sqrt"); });
        findViewById(R.id.btnSq)  .setOnClickListener(v -> { haptic(v); doSci("sq"); });
        findViewById(R.id.btnPct) .setOnClickListener(v -> { haptic(v); doSci("pct"); });
    }

    // ── Input handling ─────────────────────────────────────────────

    /**
     * Handles digit input (0-9).
     * @param d The digit string to append.
     */
    private void inputDigit(String d) {
        if (justCalc) { current = ""; justCalc = false; }
        if (current.equals("0") && !d.equals(".")) current = d;
        else if (current.length() < 12) current += d;
        refreshDisplay();
    }

    /**
     * Handles the decimal point button. Ensures only one dot per number.
     */
    private void inputDot() {
        if (justCalc) { current = "0"; justCalc = false; }
        if (!current.contains(".")) current = (current.isEmpty() ? "0" : current) + ".";
        refreshDisplay();
    }

    private void toggleNeg() {
        if (current.isEmpty() || current.equals("0")) return;
        current = current.startsWith("-") ? current.substring(1) : "-" + current;
        refreshDisplay();
    }

    private void backspace() {
        if (justCalc) return;
        if (current.length() > 1) current = current.substring(0, current.length() - 1);
        else current = "0";
        refreshDisplay();
    }

    // ── Operator ───────────────────────────────────────────────────

    /**
     * Logic for applying an arithmetic operator (+, −, ×, ÷).
     * Supports operation chaining (e.g., 5 + 3 + ... triggers calculation of 5+3 first).
     * @param o The operator symbol.
     */
    private void setOp(String o) {
        // Chain: if there's already a pending op and a second operand, compute first
        if (!prev.isEmpty() && !op.isEmpty() && !current.isEmpty()) {
            calculate(true);
        }
        prev = current.isEmpty() ? prev : current;
        op = o;
        current = "";
        justCalc = false;
        highlightOp(o);
        tvExpression.setText(prev + " " + op);
        tvOpIndicator.setText("");
    }

    // ── Calculation ────────────────────────────────────────────────

    /**
     * Performs the pending arithmetic operation.
     * @param chaining True if this is called by another operator press, false if by the Equals button.
     */
    private void calculate(boolean chaining) {
        if (prev.isEmpty() || op.isEmpty() || current.isEmpty()) return;

        double a, b;
        try {
            a = Double.parseDouble(prev);
            b = Double.parseDouble(current);
        } catch (NumberFormatException e) {
            showError(getString(R.string.error_invalid));
            return;
        }

        if (op.equals("÷") && b == 0) {
            showError(getString(R.string.error_div_zero));
            return;
        }

        double result;
        switch (op) {
            case "+": result = a + b; break;
            case "−": result = a - b; break;
            case "×": result = a * b; break;
            case "÷": result = a / b; break;
            default:  return;
        }

        String formatted = fmt(result);
        String entry = df.format(a) + " " + op + " " + df.format(b) + " = " + formatted;

        if (!chaining) {
            addHistory(entry);
            tvExpression.setText(entry);
        }

        clearOpHighlight();
        current = formatted;
        if (!chaining) {
            prev = "";
            op = "";
        } else {
            prev = formatted;
        }
        justCalc = !chaining;
        tvOpIndicator.setText("");
        refreshDisplay();
        if (!chaining) animateResult();
    }

    // ── Scientific ─────────────────────────────────────────────────

    /**
     * Performs scientific operations on the current value.
     * @param fn The function name ("sqrt", "sq", "pct").
     */
    private void doSci(String fn) {
        String src = current.isEmpty() ? prev : current;
        if (src.isEmpty()) return;

        double n;
        try { n = Double.parseDouble(src); }
        catch (NumberFormatException e) { showError(getString(R.string.error_invalid)); return; }

        double result;
        String label;
        switch (fn) {
            case "sqrt":
                if (n < 0) { showError(getString(R.string.error_sqrt_neg)); return; }
                result = Math.sqrt(n); label = "√(" + df.format(n) + ")";
                break;
            case "sq":
                result = n * n; label = "(" + df.format(n) + ")²";
                break;
            default: // pct
                result = n / 100.0; label = df.format(n) + "%";
                break;
        }

        String formatted = fmt(result);
        String entry = label + " = " + formatted;
        addHistory(entry);
        tvExpression.setText(entry);
        current = formatted;
        justCalc = true;
        refreshDisplay();
        animateResult();
    }

    // ── UI helpers ─────────────────────────────────────────────────

    private void refreshDisplay() {
        String display = current.isEmpty() ? "0" : current;
        tvResult.setText(display);
        tvResult.setTextColor(getColor(R.color.text_primary));
    }

    private void showError(String msg) {
        tvResult.setText(msg);
        tvResult.setTextColor(getColor(R.color.accent));
        current = ""; prev = ""; op = "";
        tvExpression.setText("");
        tvOpIndicator.setText("");
        clearOpHighlight();
    }

    private void clearAll() {
        current = "0"; prev = ""; op = ""; justCalc = false;
        tvResult.setText("0");
        tvResult.setTextColor(getColor(R.color.text_primary));
        tvExpression.setText("");
        tvOpIndicator.setText("");
        clearOpHighlight();
    }

    /**
     * Adds a calculation entry to the internal history list.
     * Keeps a maximum of 8 entries as specified in the project report.
     *
     * @param entry The formatted string of the calculation (e.g., "5 + 3 = 8")
     */
    private void addHistory(String entry) {
        history.add(0, entry);
        if (history.size() > 8) history.remove(history.size() - 1);
        tvHistory.setText(history.size() > 1 ? history.get(1) : "");
    }

    private void highlightOp(String o) {
        clearOpHighlight();
        Button target = null;
        if (o.equals("+")) target = btnAdd;
        else if (o.equals("−")) target = btnSub;
        else if (o.equals("×")) target = btnMul;
        else if (o.equals("÷")) target = btnDiv;
        if (target != null) {
            target.setBackgroundResource(R.drawable.bg_btn_op_active);
            target.setTextColor(getColor(R.color.accent));
        }
    }

    private void clearOpHighlight() {
        for (Button b : new Button[]{btnAdd, btnSub, btnMul, btnDiv}) {
            b.setBackgroundResource(R.drawable.bg_btn_op);
            b.setTextColor(getColor(R.color.white));
        }
    }

    private void animateResult() {
        tvResult.setScaleX(0.88f);
        tvResult.setScaleY(0.88f);
        tvResult.setAlpha(0f);
        ObjectAnimator sx = ObjectAnimator.ofFloat(tvResult, "scaleX", 0.88f, 1f);
        ObjectAnimator sy = ObjectAnimator.ofFloat(tvResult, "scaleY", 0.88f, 1f);
        ObjectAnimator al = ObjectAnimator.ofFloat(tvResult, "alpha", 0f, 1f);
        sx.setInterpolator(new OvershootInterpolator(2f));
        sy.setInterpolator(new OvershootInterpolator(2f));
        AnimatorSet set = new AnimatorSet();
        set.playTogether(sx, sy, al);
        set.setDuration(220);
        set.start();
    }

    private void haptic(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
    }

    /** Format result cleanly — no scientific notation for normal ranges. */
    private String fmt(double n) {
        if (Double.isNaN(n)) return "Error";
        if (Double.isInfinite(n)) return n > 0 ? "∞" : "−∞";
        String s = df.format(n);
        if (s.length() > 12) s = String.format("%.4e", n);
        return s;
    }
}
