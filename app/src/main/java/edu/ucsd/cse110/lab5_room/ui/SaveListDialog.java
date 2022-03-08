package edu.ucsd.cse110.lab5_room.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import edu.ucsd.cse110.lab5_room.R;

public class SaveListDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_list, null);
        EditText nameET = (EditText) view.findViewById(R.id.save_list_name);
        builder.setView(view)
                .setTitle(R.string.save_dialog_title)
                .setPositiveButton(R.string.save_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle studentSave = getArguments();
                        String savedStudents = studentSave.getString("CurrentSave",null);
                        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Save-" + nameET.getText().toString(), savedStudents);
                        editor.apply();
                        //String currSave = sharedPreferences.getString("Save-" + nameET.getText().toString(), "ERROR");
                        //Log.i("DIALOG-SAVE:", currSave);
                        SaveListDialog.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveListDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
