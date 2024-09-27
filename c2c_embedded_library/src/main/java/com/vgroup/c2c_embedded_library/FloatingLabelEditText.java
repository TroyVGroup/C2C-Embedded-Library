package com.vgroup.c2c_embedded_library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FloatingLabelEditText extends RelativeLayout {

    private TextView floatingLabel;
    private EditText editText;
    private String labelText;
    private int labelColor;
    private boolean isLabelShown;

    public FloatingLabelEditText(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public FloatingLabelEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingLabelEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Inflate the custom layout
        LayoutInflater.from(context).inflate(R.layout.view_floating_label_edittext, this, true);

        // Bind the views
        floatingLabel = findViewById(R.id.floating_label);
        editText = findViewById(R.id.edit_text);

        // Set default properties
        labelText = "Label";
        labelColor = Color.GRAY;

        if (attrs != null) {
            // Get attributes from XML
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.FloatingLabelEditText, 0, 0);

            try {
                labelText = a.getString(R.styleable.FloatingLabelEditText_labelText);
                labelColor = a.getColor(R.styleable.FloatingLabelEditText_labelColor, Color.GRAY);
            } finally {
                a.recycle();
            }
        }

        // Set label properties
        floatingLabel.setText(labelText);
        floatingLabel.setTextColor(labelColor);
        floatingLabel.setVisibility(INVISIBLE);
        editText.setHint(labelText);
        editText.setHintTextColor(Color.LTGRAY);
        // Add listeners to EditText
        setupEditTextListeners();
    }

    private void setupEditTextListeners() {
        // TextWatcher to manage floating label visibility
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    hideFloatingLabel();
                } else {
                    showFloatingLabel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Focus listener to handle focus changes
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus || !editText.getText().toString().isEmpty()) {
                    showFloatingLabel();
                } else {
                    hideFloatingLabel();
                }
            }
        });
    }

    private void showFloatingLabel() {
        if (!isLabelShown) {
            floatingLabel.setVisibility(VISIBLE);
            editText.setHint("");
            floatingLabel.animate().translationY(-2).scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
            isLabelShown = true;
        }
    }

    private void hideFloatingLabel() {
        if (isLabelShown && editText.getText().toString().isEmpty()) {
            editText.setHint(labelText);
            floatingLabel.animate().translationY(0).scaleX(1f).scaleY(1f).setDuration(200)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            floatingLabel.setVisibility(INVISIBLE);
                        }
                    }).start();
            isLabelShown = false;
        }
    }

    // Additional setters and getters for custom properties
    public void setLabelText(String text) {
        labelText = text;
        floatingLabel.setText(text);
    }

    public void setLabelColor(int color) {
        labelColor = color;
        floatingLabel.setTextColor(color);
    }

    public String getEditText() {
        if (editText != null && !TextUtils.isEmpty(editText.getText().toString())){
            return editText.getText().toString();
        }
        return "";
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int i, int i1, int verified_icon, int i2) {
        editText.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.verified_icon,
                0
        );
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
}

