package TestApp.StudentSchedulingApp.UI;

import android.annotation.SuppressLint;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class AddAssessment extends AppCompatActivity {

    private Spinner termSpinner;
    private Spinner courseSpinner;
    private Spinner instructorSpinner;
    CalendarView startCal;
    CalendarView endCal;
    private LocalDate startDate;
    private LocalDate endDate;

    DBRepository dbRepository = new DBRepository(getApplication());

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        termSpinner = findViewById(R.id.addAssessmentTermSpinner);
        courseSpinner = findViewById(R.id.addAssessmentCourseSpinner);
        instructorSpinner = findViewById(R.id.addAssessmentInstructorSpinner);
        startCal = findViewById(R.id.addAssessmentStartDate);
        endCal = findViewById(R.id.addAssessmentEndDate);

        setTermSpinner();
        setCourseSpinner();
        setInstructorSpinner();

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setCourseSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                startCal.setMinDate(0);
                endCal.setMinDate(0);

                startCal.setMaxDate(getCalendarMaxRange());
                startCal.setMinDate(getCalendarMinRange());

                endCal.setMaxDate(getCalendarMaxRange());
                endCal.setMinDate(getCalendarMinRange());

                startCal.setDate(getCalendarMinRange());
                endCal.setDate(getCalendarMaxRange());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startCal.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;

                startDate = LocalDate.of( year, month, dayOfMonth );
            }
        });

        endCal.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                endDate = LocalDate.of( year, month, dayOfMonth );
            }
        });
    }

    private long getCalendarMinRange() {

        Course selectedCourse = (Course) courseSpinner.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(selectedCourse.getStartDate().getYear(), selectedCourse.getStartDate().getMonthValue()-1, selectedCourse.getStartDate().getDayOfMonth());

        return cal.getTimeInMillis();
    }

    private long getCalendarMaxRange(){
        Course selectedCourse = (Course) courseSpinner.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(selectedCourse.getEndDate().getYear(), selectedCourse.getEndDate().getMonthValue()-1, selectedCourse.getEndDate().getDayOfMonth());

        return cal.getTimeInMillis();
    }

    private void setTermSpinner() {

       List<Term> termList = dbRepository.getAllTerms();

        ArrayAdapter<Term> dataAdapter = new ArrayAdapter<Term>(this,
                android.R.layout.simple_spinner_item, termList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(dataAdapter);
    }

    private void setCourseSpinner() {

        List<Course> courseList = dbRepository.getAllCourses();
        List<Course> tempList = new ArrayList<>();


        if (termSpinner.getSelectedItem() != null) {
            Term selectedTerm = (Term) termSpinner.getSelectedItem();

            for (Course course : courseList) {
                if (course.getTermID() == selectedTerm.getTermID()) {
                    tempList.add(course);
                }
            }

            ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(this,
                    android.R.layout.simple_spinner_item, tempList);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseSpinner.setAdapter(dataAdapter);

        } else {
            ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(this,
                    android.R.layout.simple_spinner_item, courseList);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseSpinner.setAdapter(dataAdapter);
        }

    }

    private void setInstructorSpinner() {

        List<Instructor> instructorList = dbRepository.getAllInstructors();

        ArrayAdapter<Instructor> dataAdapter = new ArrayAdapter<Instructor>(this,
                android.R.layout.simple_spinner_item, instructorList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorSpinner.setAdapter(dataAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(AddAssessment.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void onClickSave(View view) {

        EditText eTitle = findViewById(R.id.addAssessmentTitle);
        RadioButton obj = findViewById(R.id.addObjectiveRB);

        Term selectedTerm = (Term) termSpinner.getSelectedItem();
        Course selectedCourse = (Course) courseSpinner.getSelectedItem();
        Instructor selectedInstructor = (Instructor) instructorSpinner.getSelectedItem();


        Date sDate = new Date(startCal.getDate());
        Date eDate = new Date(endCal.getDate());

        startDate = sDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDate = eDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String aTitle = eTitle.getText().toString();

        Boolean type;

        type = obj.isChecked();

        int termID = selectedTerm.getTermID();
        int courseID = selectedCourse.getCourseID();
        int instructorID = selectedInstructor.getInstructorID();

        Assessment assess = new Assessment(0, aTitle, startDate, endDate, courseID, instructorID, termID, type);

        if (aTitle.isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(AddAssessment.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Assessment. Please ensure all fields have valid values.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else if (endDate.isBefore(startDate)){

            AlertDialog alertDialog = new AlertDialog.Builder(AddAssessment.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Assessment. Please ensure start date is before end date.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else if (instructorID == -1 || termID == -1 || courseID == -1) {

            AlertDialog alertDialog = new AlertDialog.Builder(AddAssessment.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Course. Please ensure you have selected an instructor, term, and course.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else{
            dbRepository.addAssessment(assess);

            AlertDialog alertDialog = new AlertDialog.Builder(AddAssessment.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(assess.getAssessTitle() + " has been added successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(AddAssessment.this, SelectAssessment.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }
    }

}