package edu.ucsd.cse110.lab5_room.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.model.db.CourseDao;

public class AddClassesActivity extends AppCompatActivity {
    EditText numberET;
    EditText yearET;
    TextView displayTV;

//    private int year;
    private Course.Quarter quarter;
    private Course.Department department;
//    private int courseNumber;
    private Course.Size size;

    boolean hasAdded = false;

    BoFApplication app;
    CourseDao courses;

//    Set<String> courses = new HashSet<String>();
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
//                Toast.makeText(adapterView.getContext(),quarter + " selected!" , Toast.LENGTH_SHORT);
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
//                subject = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(adapterView.getContext(),subject + " selected!" , Toast.LENGTH_SHORT);
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

        displayTV = findViewById(R.id.tv_added_classes);
        numberET  = findViewById(R.id.editText_course_number);
        yearET    = findViewById(R.id.editText_course_year);
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

            Course course = new Course(department, num, size, quarter, year);
            app.executorService.submit(() -> {
                if (courses.hasTaken(course)) {
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
                else {
                    courses.insert(course);
                    runOnUiThread(() -> {
                        String classesViz = new StringBuilder()
                                .append(displayTV.getText())
                                .append((hasAdded) ? ", " : "")
                                .append(course)
                                .toString();
                        displayTV.setText(classesViz);
                        hasAdded = true;
                    });
                }
            });

        }
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
            Intent pfpIntent = new Intent(AddClassesActivity.this, CreateProfilePictureActivity.class);
            pfpIntent.putExtra(Constants.USER_NAME, getIntent().getStringExtra(Constants.USER_NAME));
//            pfpIntent.putExtra(Constants.USER_COURSES, courses.toArray(new ArrayList<>(courses).toArray(new String[0])));
            startActivity(pfpIntent);
        }
    }

//    public void onAddAnotherClassClicked(View view) {
//        Log.d("clicked", "Viewing all classes");
//    }
}
