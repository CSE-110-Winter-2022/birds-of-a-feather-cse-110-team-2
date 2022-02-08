package edu.ucsd.cse110.lab5_room.internal;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.Set;

public class CustomFilter implements InputFilter {
    private final Set<Character> filter;
    private final int maxLength;

    public CustomFilter(Set<Character> filter) {
        this(filter, -1);
    }

    public CustomFilter(Set<Character> filter, int maxLength) {
        this.filter = filter;
        this.maxLength = -1;
    }

    public InputFilter[] asSingleton() {
        return new InputFilter[]{ this };
    }

    @Override
    public CharSequence filter(CharSequence src, int i, int i1, Spanned spanned, int i2, int i3) {
        // support backspace
        if (src.equals(""))
            return "";

        return filter.contains(src.charAt(0)) ? null : "";
    }
}
