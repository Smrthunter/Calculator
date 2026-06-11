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

public class MainActivity extends AppCompatActivity {

    private TextView tvResult, tvExpression, tvOpIndicator, tvHistory;
    private Button btnAdd, btnSub, btnMul, btnDiv;

    private String current = "0";
    private String prev = "";
    private String op = "";
    private boolean justCalc = false;

    private final List<String> history = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("#,##0.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        wireButtons();
    }

    private void bindViews() {
        tvResult = findViewById(R.id.tvResult);
        tvExpression = findViewById(R.id.tvExpression);
        tvOpIndicator = findViewById(R.id.tvOpIndicator);
        tvHistory = findViewById(R.id.tvHistory);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
    }

    private void wireButtons() {

        int[] ids = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        String[] digits = {"0","1","2","3","4","5","6","7","8","9"};

        for (int i = 0; i < ids.length; i++) {
            final String d = digits[i];
            findViewById(ids[i]).setOnClickListener(v -> {
                haptic(v);
                inputDigit(d);
            });
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> { haptic(v); setOp("+"); });
        findViewById(R.id.btnSub).setOnClickListener(v -> { haptic(v); setOp("−"); });
        findViewById(R.id.btnMul).setOnClickListener(v -> { haptic(v); setOp("×"); });
        findViewById(R.id.btnDiv).setOnClickListener(v -> { haptic(v); setOp("÷"); });

        findViewById(R.id.btnEq).setOnClickListener(v -> { haptic(v); calculate(false); });
        findViewById(R.id.btnClear).setOnClickListener(v -> { haptic(v); clearAll(); });
        findViewById(R.id.btnNeg).setOnClickListener(v -> { haptic(v); toggleNeg(); });
        findViewById(R.id.btnDot).setOnClickListener(v -> { haptic(v); inputDot(); });
        findViewById(R.id.btnBack).setOnClickListener(v -> { haptic(v); backspace(); });

        findViewById(R.id.btnSqrt).setOnClickListener(v -> { haptic(v); doSci("sqrt"); });
        findViewById(R.id.btnSq).setOnClickListener(v -> { haptic(v); doSci("sq"); });
        findViewById(R.id.btnPct).setOnClickListener(v -> { haptic(v); doSci("pct"); });

        // Clear history
        tvHistory.setOnLongClickListener(v -> {
            history.clear();
            tvHistory.setText("");
            return true;
        });
    }

    private void inputDigit(String d) {
        if (justCalc) {
            current = "";
            justCalc = false;
        }

        if (current.equals("0")) current = d;
        else if (current.length() < 12) current += d;

        refreshDisplay();
    }

    private void inputDot() {
        if (justCalc) {
            current = "0";
            justCalc = false;
        }

        if (!current.contains(".")) {
            current = current + ".";
        }

        refreshDisplay();
    }

    private void toggleNeg() {
        if (current.equals("0")) return;

        current = current.startsWith("-")
                ? current.substring(1)
                : "-" + current;

        refreshDisplay();
    }

    private void backspace() {
        if (justCalc) return;

        if (current.length() <= 1 || (current.length() == 2 && current.startsWith("-"))) {
            current = "0";
        } else {
            current = current.substring(0, current.length() - 1);
        }

        refreshDisplay();
    }

    private void setOp(String o) {
        if (!prev.isEmpty() && !op.isEmpty() && !current.isEmpty()) {
            calculate(true);
        }

        prev = current;
        op = o;
        current = "";
        justCalc = false;

        tvExpression.setText(prev + " " + op);
        tvOpIndicator.setText("");
    }

    private void calculate(boolean chaining) {

        if (prev.isEmpty() || op.isEmpty() || current.isEmpty()) return;

        double a = Double.parseDouble(prev);
        double b = Double.parseDouble(current);

        if (op.equals("÷") && b == 0) {
            showError("Cannot divide by zero");
            return;
        }

        double result;

        switch (op) {
            case "+": result = a + b; break;
            case "−": result = a - b; break;
            case "×": result = a * b; break;
            case "÷": result = a / b; break;
            default: return;
        }

        String formatted = fmt(result);

        if (!chaining) {
            addHistory(a + " " + op + " " + b + " = " + formatted);
        }

        current = formatted;
        prev = chaining ? formatted : "";
        op = chaining ? op : "";
        justCalc = !chaining;

        refreshDisplay();
        animateResult();
    }

    private void doSci(String fn) {

        double n = Double.parseDouble(current);

        double result;
        String label;

        switch (fn) {

            case "sqrt":
                result = Math.sqrt(n);
                label = "√(" + n + ")";
                break;

            case "sq":
                result = n * n;
                label = "(" + n + ")²";
                break;

            default: // percentage fix
                result = n / 100.0;
                label = n + "%";
                break;
        }

        String formatted = fmt(result);

        addHistory(label + " = " + formatted);

        current = formatted;
        justCalc = true;

        refreshDisplay();
        animateResult();
    }

    private void refreshDisplay() {
        tvResult.setText(current.isEmpty() ? "0" : current);
    }

    private void clearAll() {
        current = "0";
        prev = "";
        op = "";
        justCalc = false;

        tvResult.setText("0");
        tvExpression.setText("");
        tvOpIndicator.setText("");
    }

    private void addHistory(String entry) {
        history.add(0, entry);
        if (history.size() > 8) history.remove(history.size() - 1);

        StringBuilder sb = new StringBuilder();
        for (String h : history) sb.append(h).append("\n");

        tvHistory.setText(sb.toString());
    }

    private void animateResult() {
        tvResult.setScaleX(0.85f);
        tvResult.setScaleY(0.85f);
        tvResult.setAlpha(0f);

        ObjectAnimator sx = ObjectAnimator.ofFloat(tvResult, "scaleX", 0.85f, 1f);
        ObjectAnimator sy = ObjectAnimator.ofFloat(tvResult, "scaleY", 0.85f, 1f);
        ObjectAnimator al = ObjectAnimator.ofFloat(tvResult, "alpha", 0f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(sx, sy, al);
        set.setDuration(200);
        set.setInterpolator(new OvershootInterpolator());
        set.start();
    }

    private void haptic(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
    }

    private String fmt(double n) {
        if (Double.isNaN(n)) return "Error";
        if (Double.isInfinite(n)) return "∞";
        if (Math.abs(n) > 1e12) return String.format("%.4e", n);
        return df.format(n);
    }

    private void showError(String msg) {
        tvResult.setText(msg);
        current = "0";
        prev = "";
        op = "";
    }
}

