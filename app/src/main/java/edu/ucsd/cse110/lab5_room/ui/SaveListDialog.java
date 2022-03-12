package edu.ucsd.cse110.lab5_room.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.data.AutoSave;
import edu.ucsd.cse110.lab5_room.data.SavedListManager;
import edu.ucsd.cse110.lab5_room.data.match.StatefulMatchList;

public class SaveListDialog extends AppCompatDialogFragment {
    private final StatefulMatchList list;
    private SavedListManager sm;

    public SaveListDialog(StatefulMatchList list) {
        this.list = list;
    }

    private void save(String name) {
        this.sm.save(name, this.list);
    }

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.sm == null)
            this.sm = SavedListManager.singleton(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_list, null);
        EditText nameET = view.findViewById(R.id.save_list_name);

        builder.setView(view)
                .setTitle(R.string.save_dialog_title)
                .setPositiveButton(R.string.save_text, (dialog, i) -> {
                    String newName = nameET.getText().toString();
                    if (newName.isEmpty())
                        return;

                    // save our new list and delete old autosave
                    save(newName);
                    AutoSave.deleteLast();

                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel_text, (dialog, i) -> dialog.cancel());
        return builder.create();
    }
}
