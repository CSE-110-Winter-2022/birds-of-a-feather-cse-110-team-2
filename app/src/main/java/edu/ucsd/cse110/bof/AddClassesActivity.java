package edu.ucsd.cse110.bof;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class AddClassesActivity extends AppCompatActivity {
    Spinner yearSpinner;
    Spinner quarterSpinner;
    Spinner subjectSpinner;

    EditText courseNumberET;

    Button addBtn;
    Button finishBtn;

    TextView addedClassesTV;

    String year;
    String quarter;
    String subject;
    String courseNumber;

    Set<String> courses = new HashSet<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classes);
        addBtn = (Button) findViewById(R.id.btn_confirm);
        finishBtn = (Button) findViewById(R.id.btn_finish);

        yearSpinner = (Spinner) findViewById(R.id.spinner_course_year);
        quarterSpinner = (Spinner) findViewById(R.id.spinner_course_quarter);
        subjectSpinner = (Spinner) findViewById(R.id.spinner_course_subject);

        addedClassesTV = (TextView) findViewById(R.id.tv_added_classes);

        courseNumberET = (EditText) findViewById(R.id.course_number_et);

        ArrayAdapter<CharSequence> yearStrAdapter = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> quarterStrAdapter = ArrayAdapter.createFromResource(this, R.array.quarter_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> subjectStrAdapter = ArrayAdapter.createFromResource(this, R.array.subject_array, android.R.layout.simple_spinner_item);

        yearStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(yearStrAdapter);
        quarterSpinner.setAdapter(quarterStrAdapter);
        subjectSpinner.setAdapter(subjectStrAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(),year + " selected!" , Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        quarterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quarter = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(),quarter + " selected!" , Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(),subject + " selected!" , Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onAddClicked(View view) {
        courseNumber = courseNumberET.getText().toString();
        if(year.equals("") || quarter.equals("") || subject.equals("") || courseNumber.equals("")) {
            AlertDialog errDialog = new AlertDialog.Builder(AddClassesActivity.this)
                    .setTitle(R.string.error_title)
                    .setMessage(R.string.error_message_1)
                    .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
            errDialog.show();
        } else {
            String course = year + "+" + quarter + "+" + subject + "+"  + courseNumber;
            if(courses.contains(course)) {
                AlertDialog errDialog = new AlertDialog.Builder(AddClassesActivity.this)
                        .setTitle(R.string.error_title)
                        .setMessage(R.string.error_message_2)
                        .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                errDialog.show();

            } else {
                courses.add(course);
                if(courses.size() == 1)
                    addedClassesTV.setText(addedClassesTV.getText().toString() + " " + subject + " " + courseNumber);
                else
                    addedClassesTV.setText(addedClassesTV.getText().toString() + ", " + subject + " " + courseNumber);
            }
        }
    }

    public void onFinishClicked(View view) {
        if(courses.isEmpty()) {
            AlertDialog errDialog = new AlertDialog.Builder(AddClassesActivity.this)
                    .setTitle(R.string.error_title)
                    .setMessage(R.string.error_message_3)
                    .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
            errDialog.show();
        } else {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet("courses", courses);
            editor.apply();

            Intent mainIntent = new Intent(AddClassesActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }
    }
}