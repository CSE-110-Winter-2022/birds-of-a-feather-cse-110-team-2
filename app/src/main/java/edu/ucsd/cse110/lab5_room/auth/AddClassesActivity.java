package edu.ucsd.cse110.lab5_room.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.Constants;

public class AddClassesActivity extends AppCompatActivity {
    Spinner yearSpinner;
    Spinner quarterSpinner;
    Spinner subjectSpinner;
    Spinner classSizeSpinner;

    EditText courseNumberET;

    Button addBtn;
    Button finishBtn;

    TextView addedClassesTV;

    String year;
    String quarter;
    String subject;
    String courseNumber;
    String classSize;

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
        classSizeSpinner = (Spinner) findViewById(R.id.spinner_course_size);


        addedClassesTV = (TextView) findViewById(R.id.tv_added_classes);

        courseNumberET = (EditText) findViewById(R.id.editText_course_number);


        ArrayAdapter<CharSequence> yearStrAdapter = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> quarterStrAdapter = ArrayAdapter.createFromResource(this, R.array.quarter_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> subjectStrAdapter = ArrayAdapter.createFromResource(this, R.array.subject_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> classSizeStrAdapter = ArrayAdapter.createFromResource(this, R.array.classSize_array, android.R.layout.simple_spinner_item);


        yearStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSizeStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(yearStrAdapter);
        quarterSpinner.setAdapter(quarterStrAdapter);
        subjectSpinner.setAdapter(subjectStrAdapter);
        classSizeSpinner.setAdapter(classSizeStrAdapter);

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


        classSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //only the word is parsed, not the bracket size
                classSize = adapterView.getItemAtPosition(i).toString().split(" ")[0];
                Toast.makeText(adapterView.getContext(), subject + "selected!", Toast.LENGTH_SHORT);
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
            String course = year + "+" + quarter + "+" + subject + "+"  + courseNumber + "+" + classSize;
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
                Log.d("added", "Added class");
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
            Intent mainIntent = new Intent(AddClassesActivity.this, CreateProfilePictureActivity.class);
            mainIntent.putExtra(Constants.USER_NAME, getIntent().getStringExtra(Constants.USER_NAME));
            mainIntent.putExtra(Constants.USER_COURSES, courses.toArray(new ArrayList<>(courses).toArray(new String[0])));
            startActivity(mainIntent);
        }
    }

    public void onAddAnotherClassClicked(View view) {
        Log.d("clicked", "Viewing all classes");
    }
}