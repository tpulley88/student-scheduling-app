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
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class AddCourse extends AppCompatActivity {

    private Spinner instructorSpinner;
    private Spinner termSpinner;
    private LocalDate startDate;
    private LocalDate endDate;
    private CalendarView startCal;
    private CalendarView endCal;

    DBRepository dbRepository = new DBRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        if (dbRepository.getAllTerms().size() < 1 ||
                dbRepository.getAllInstructors().size() < 1 ) {

            AlertDialog alertDialog = new AlertDialog.Builder(AddCourse.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please add Terms and Instructors before adding Courses.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(AddCourse.this, Home.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }

        instructorSpinner = findViewById(R.id.addCourseInstructorSpinner);
        termSpinner = findViewById(R.id.addCourseTermSpinner);
        startCal = findViewById(R.id.addCourseStartDate);
        endCal = findViewById(R.id.addCourseEndDate);

        setInstructorSpinner();
        setTermSpinner();

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                startCal.setMinDate(0);
                endCal.setMinDate(0);

                startCal.setMaxDate(getCalendarMaxRange());
                startCal.setMinDate(getCalendarMinRange());

                endCal.setMaxDate(getCalendarMaxRange());
                endCal.setMinDate(getCalendarMinRange());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        startCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                startDate = LocalDate.of( year, month, dayOfMonth );
            }
        });

        endCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                endDate = LocalDate.of( year, month, dayOfMonth );
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

        ArrayAdapter<Instructor> dataAdapter = new ArrayAdapter<Instructor>(this,
                android.R.layout.simple_spinner_item, instructorList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorSpinner.setAdapter(dataAdapter);

    }

    private void setTermSpinner() {

        List<Term> termList = dbRepository.getAllTerms();

        ArrayAdapter<Term> dataAdapter = new ArrayAdapter<Term>(this,
                android.R.layout.simple_spinner_item, termList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(dataAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(AddCourse.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {

        EditText eTitle = findViewById(R.id.addCourseTitle);
        RadioButton plannedRB = findViewById(R.id.coursePlannedRB);
        RadioButton inProgressRB = findViewById(R.id.courseInProgressRB);
        RadioButton completedRB = findViewById(R.id.courseCompletedRB);
        RadioButton droppedRB = findViewById(R.id.courseDroppedRB);

        Instructor selectedInstructor = (Instructor) instructorSpinner.getSelectedItem();
        Term selectedTerm = (Term) termSpinner.getSelectedItem();

        EditText cNotes = findViewById(R.id.addCourseNotes);

        String cTitle = eTitle.getText().toString();

        if (startDate == null) {
            Date sDate = new Date(startCal.getDate());
            startDate = sDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        if (endDate == null) {
            Date eDate = new Date(endCal.getDate());
            endDate = eDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        String status = null;

        if (plannedRB.isChecked()) {
            status = "PLANNED";
        } else if (inProgressRB.isChecked()) {
            status = "IN PROGRESS";
        } else if (completedRB.isChecked()) {
            status = "COMPLETED";
        } else if (droppedRB.isChecked()){
            status = "DROPPED";
        }

        int instructorID = selectedInstructor.getInstructorID();
        int termID = selectedTerm.getTermID();
        String courseNotes;

        try {
            courseNotes = cNotes.getText().toString();

        } catch (NullPointerException ignored) {

            courseNotes = "No notes.";
        }

        Course course = new Course(0, cTitle, startDate, endDate, status, instructorID, termID, courseNotes);

        if (cTitle.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(AddCourse.this).create();
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

            AlertDialog alertDialog = new AlertDialog.Builder(AddCourse.this).create();
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

            AlertDialog alertDialog = new AlertDialog.Builder(AddCourse.this).create();
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
            dbRepository.addCourse(course);

            AlertDialog alertDialog = new AlertDialog.Builder(AddCourse.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(course.getCourseTitle() + " has been added successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(AddCourse.this, SelectCourse.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }
    }
}