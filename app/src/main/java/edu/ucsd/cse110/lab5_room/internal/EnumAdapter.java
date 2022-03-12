package edu.ucsd.cse110.lab5_room.internal;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class EnumAdapter<E extends Enum<E>> implements AdapterView.OnItemSelectedListener {
    private final E[] states;
    private final Callback<E> callback;

    public interface Callback<E> {
        void update(E chosen);
    }

    public EnumAdapter(Class<E> eClass, Callback<E> callback) {
        this.states   = eClass.getEnumConstants();
        this.callback = callback;
    }

    public ArrayAdapter<E> toArrayAdapter(Context c) {
        return new ArrayAdapter<>(c, android.R.layout.simple_spinner_dropdown_item, states);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // the warning about an unchecked cast is not a real issue, just stems from generics
        // noinspection unchecked
        callback.update((E) adapterView.getItemAtPosition(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        callback.update(null);
    }
}
