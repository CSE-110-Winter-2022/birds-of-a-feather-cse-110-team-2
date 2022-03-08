package edu.ucsd.cse110.lab5_room.auth;

import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.model.db.CourseDao;

public class AddClassesActivity extends AuthActivity {
    EditText numberET;
    EditText yearET;
    TextView displayTV;

    private Course.Quarter quarter;
    private Course.Department department;
    private Course.Size size;

    boolean hasAdded = false;

    BoFApplication app;
    CourseDao courses;
    Set<Course> courseList = Collections.synchronizedSet(new HashSet<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classes);

        // get DB to save courses
        app = (BoFApplication) getApplication();
        AppDatabase db = AppDatabase.singleton(this);
        courses = db.courseDao();

        // Set up quarter spinner
        Spinner quarterSpinner = findViewById(R.id.spinner_course_quarter);
        quarterSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Course.Quarter.values()
        ));
        quarterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quarter = (Course.Quarter) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                quarter = null;
            }
        });

        // set up department spinner
        Spinner subjectSpinner = findViewById(R.id.spinner_course_subject);
        subjectSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Course.Department.values()
        ));
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department = (Course.Department) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                department = null;
            }
        });

        // set up size spinner
        Spinner classSizeSpinner = findViewById(R.id.spinner_course_size);
        classSizeSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Course.Size.values()
        ));
        classSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                size = (Course.Size) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                size = null;
            }
        });

        // make sure we can access the edittexts and textviews
        numberET  = findViewById(R.id.editText_course_number);
        yearET    = findViewById(R.id.editText_course_year);
        displayTV = findViewById(R.id.tv_added_classes);
    }

    public void onAddClicked(View view) {
        if (quarter == null || department == null || size == null ||
                              (numberET.length() <= 0) || (yearET.length() <= 0)) {
            // alert user if a field is empty
            AlertDialog errDialog = new AlertDialog.Builder(AddClassesActivity.this)
                    .setTitle(R.string.error_title)
                    .setMessage(R.string.error_message_1)
                    .setPositiveButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            errDialog.show();
        }
        else {
            int num  = Integer.decode(numberET.getText().toString());
            int year = Integer.decode(yearET.getText().toString());

            app.executorService.submit(() -> {
                Course course = courses.getOrCreate(department, num, size, quarter, year);
                if (!courseList.contains(course)) {
                    courseList.add(course);
                    updateDTV(course);
                }
                else {
                    // alert user if class has already been entered
                    runOnUiThread(() -> {
                        AlertDialog errDialog = new AlertDialog.Builder(AddClassesActivity.this)
                                .setTitle(R.string.error_title)
                                .setMessage(R.string.error_message_2)
                                .setPositiveButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss())
                                .create();
                        errDialog.show();
                    });
                }
            });
        }
    }

    private void updateDTV(Course c) {
        runOnUiThread(() -> {
            String classesViz = displayTV.getText() + ((hasAdded) ? ", " : "") + c;
            displayTV.setText(classesViz);
            hasAdded = true;
        });
    }

    public void onFinishClicked(View view) {
        if (!hasAdded) {
            // do not let the user proceed if no courses have been added
            AlertDialog errDialog = new AlertDialog.Builder(AddClassesActivity.this)
                    .setTitle(R.string.error_title)
                    .setMessage(R.string.error_message_3)
                    .setPositiveButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            errDialog.show();
        }
        else {
            accumulateList(Constants.USER_COURSES, new ArrayList<>(courseList));
            moveOn(CreateProfilePictureActivity.class);
        }
    }
}
