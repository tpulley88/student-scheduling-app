package TestApp.StudentSchedulingApp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class ModifyCourse extends AppCompatActivity {

    int modifyID;
    EditText modifyTitle;
    EditText courseNotes;
    private Spinner instructorSpinner;
    private Spinner termSpinner;
    private CalendarView startCal;
    private CalendarView endCal;
    private LocalDate startDate;
    private LocalDate endDate;
    RadioButton plannedRB;
    RadioButton inProgressRB;
    RadioButton completedRB;
    RadioButton droppedRB;
    private int iID;
    private int tID;
    private String notes;

    DBRepository dbRepository = new DBRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_course);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        modifyID = getIntent().getExtras().getInt("Course ID");
        String cTitle = getIntent().getStringExtra("Course Title");
        startDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("Start Date"));
        endDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("End Date"));
        String modifyStatus = getIntent().getStringExtra("Course Status");
        iID = getIntent().getExtras().getInt("Instructor ID");
        tID = getIntent().getExtras().getInt("Term ID");
        notes = getIntent().getStringExtra("Course Notes");

        modifyTitle = findViewById(R.id.modifyCourseTitle);
        startCal = findViewById(R.id.modifyCourseStartDate);
        endCal = findViewById(R.id.modifyCourseEndDate);
        plannedRB = findViewById(R.id.modifyCoursePlannedRB);
        inProgressRB = findViewById(R.id.modifyCourseInProgressRB);
        completedRB = findViewById(R.id.modifyCourseCompletedRB);
        droppedRB = findViewById(R.id.modifyCourseDroppedRB);
        courseNotes = findViewById(R.id.modifyCourseNotes);

        modifyTitle.setText(cTitle);
        courseNotes.setText(notes);

        startCal.setDate(startDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());
        endCal.setDate(endDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());

        switch (modifyStatus) {
            case "PLANNED":
                plannedRB.toggle();
                break;
            case "IN PROGRESS":
                inProgressRB.toggle();
                break;
            case "COMPLETED":
                completedRB.toggle();
                break;
            case "DROPPED":
                droppedRB.toggle();
                break;
        }

        instructorSpinner = findViewById(R.id.modifyCourseInstructorSpinner);
        setInstructorSpinner();

        termSpinner = findViewById(R.id.modifyCourseTermSpinner);
        setTermSpinner();

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                startCal.setMinDate(0);
                endCal.setMinDate(0);
                startCal.setMinDate(getCalendarMinRange());
                startCal.setMaxDate(getCalendarMaxRange());
                endCal.setMinDate(getCalendarMinRange());
                endCal.setMaxDate(getCalendarMaxRange());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;

                startDate = LocalDate.of(year, month, dayOfMonth);
            }
        });

        endCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                endDate = LocalDate.of(year, month, dayOfMonth);
            }
        });


    }

    private long getCalendarMinRange() {

        Term selectedTerm = (Term) termSpinner.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(selectedTerm.getStartDate().getYear(), selectedTerm.getStartDate().getMonthValue()-1, selectedTerm.getStartDate().getDayOfMonth());

        return cal.getTimeInMillis();
    }

    private long getCalendarMaxRange(){
        Term selectedTerm = (Term) termSpinner.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(selectedTerm.getEndDate().getYear(), selectedTerm.getEndDate().getMonthValue()-1, selectedTerm.getEndDate().getDayOfMonth());

        return cal.getTimeInMillis();
    }

    private void setInstructorSpinner() {

        List<Instructor> instructorList = dbRepository.getAllInstructors();

        int position = -1;

        ArrayAdapter<Instructor> dataAdapter = new ArrayAdapter<Instructor>(this,
                android.R.layout.simple_spinner_item, instructorList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorSpinner.setAdapter(dataAdapter);

        for (Instructor inst : instructorList) {
            position = position + 1;
            if (iID == inst.getInstructorID()) {
                break;
            }
        }

        instructorSpinner.setSelection(position);
    }

    private void setTermSpinner() {

        List<Term> termList = dbRepository.getAllTerms();

        int position = -1;

        ArrayAdapter<Term> dataAdapter = new ArrayAdapter<Term>(this,
                android.R.layout.simple_spinner_item, termList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(dataAdapter);

        for (Term term : termList) {
            position = position + 1;
            if (tID == term.getTermID()) {
                break;
            }
        }

        termSpinner.setSelection(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ModifyCourse.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {

        Instructor selectedInstructor = (Instructor) instructorSpinner.getSelectedItem();
        Term selectedTerm = (Term) termSpinner.getSelectedItem();
        EditText cNotes = findViewById(R.id.modifyCourseNotes);

        String cTitle = modifyTitle.getText().toString();
        String status;

        if (plannedRB.isChecked()) {
            status = "PLANNED";
        } else if (inProgressRB.isChecked()) {
            status = "IN PROGRESS";
        } else if (completedRB.isChecked()) {
            status = "COMPLETED";
        } else {
            status = "DROPPED";
        }

        int instructorID = selectedInstructor.getInstructorID();
        int termID = selectedTerm.getTermID();
        String courseNotes = cNotes.getText().toString();

        Course course = new Course(modifyID, cTitle, startDate, endDate, status, instructorID, termID, courseNotes);

        if (cTitle.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(ModifyCourse.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Course. Please ensure all fields have valid values.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else if (endDate.isBefore(startDate)) {

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyCourse.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Course. Please ensure start date is before end date.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else if (instructorID == -1 || termID == -1) {

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyCourse.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Course. Please ensure you have selected an instructor.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else {
            dbRepository.updateCourse(course);

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyCourse.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(course.getCourseTitle() + " has been updated successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ModifyCourse.this, SelectCourse.class);
                            startActivity(intent);

                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    public void onClickDelete(View view) {

        List<Course> allCourses = dbRepository.getAllCourses();

        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCourse.this);
        builder.setCancelable(true);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this Course?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Course course: allCourses) {
                            if (course.getCourseID() == modifyID) {
                                dbRepository.deleteCourse(course);

                                AlertDialog alertDialog = new AlertDialog.Builder(ModifyCourse.this).create();
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage(course.getCourseTitle() + " has been deleted successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(ModifyCourse.this, SelectCourse.class);
                                        startActivity(intent);

                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            }
                        }
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}