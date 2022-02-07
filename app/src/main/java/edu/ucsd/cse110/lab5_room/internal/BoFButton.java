package edu.ucsd.cse110.lab5_room.internal;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class BoFButton extends androidx.appcompat.widget.AppCompatButton {
    private void setup() {
        setEnabled(false);
    }

    public BoFButton(@NonNull Context context) {
        super(context);
        setup();
    }

    public BoFButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public BoFButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public void trackEnable(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setEnabled(charSequence.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}
