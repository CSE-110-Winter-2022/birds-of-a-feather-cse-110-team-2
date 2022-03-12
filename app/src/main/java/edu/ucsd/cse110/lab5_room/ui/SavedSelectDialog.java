package edu.ucsd.cse110.lab5_room.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import edu.ucsd.cse110.lab5_room.data.FilterableMatchList;
import edu.ucsd.cse110.lab5_room.data.SavedListManager;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;

public class SavedSelectDialog extends AppCompatDialogFragment
        implements AlertDialog.OnClickListener {
    private SavedListManager sm;
    private String[] items;

    private BoFApplication app;

    private final Callback callback;

    public interface Callback {
        void update(FilterableMatchList list);
    }

    public SavedSelectDialog(Callback callback) {
        this.callback = callback;
    }

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.sm == null)
            this.sm = SavedListManager.singleton(getActivity());

        this.items = this.sm.getSaveNames().toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(this.items, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (app == null)
            app = (BoFApplication) (getActivity().getApplication());

        app.executorService.submit(() -> {
            FilterableMatchList list = this.sm.get(getContext(), this.items[i]);
            this.callback.update(list);
        });
    }
}
