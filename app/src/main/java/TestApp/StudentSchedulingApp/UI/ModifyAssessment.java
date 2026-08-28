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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class ModifyAssessment extends AppCompatActivity {

    DBRepository dbRepository = new DBRepository(getApplication());

    private int modifyID;
    private Spinner modifyTerm;
    private Spinner modifyCourse;
    private Spinner modifyInstructor;
    private String aTitle;
    private boolean aType;
    private int tID;
    private int cID;
    private int iID;

    private LocalDate aSDate;
    private LocalDate aEDate;

    Assessment assessment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_assessment);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        modifyID = getIntent().getExtras().getInt("Assessment ID");
        aTitle = getIntent().getStringExtra("Assessment Title");
        aSDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("Start Date"));
        aEDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("End Date"));
        aType = getIntent().getExtras().getBoolean("Type");
        tID = getIntent().getExtras().getInt("Term ID");
        cID = getIntent().getExtras().getInt("Course ID");
        iID = getIntent().getExtras().getInt("Instructor ID");


        EditText modifyTitle = findViewById(R.id.modifyAssessmentTitle);
        CalendarView modifyStartDate = findViewById(R.id.modifyAssessmentStartDate);
        CalendarView modifyEndDate = findViewById(R.id.modifyAssessmentEndDate);
        RadioButton obj = findViewById(R.id.modifyObjectiveRB);
        RadioButton per = findViewById(R.id.modifyPerformanceRB);
        modifyTerm = findViewById(R.id.modifyAssessmentTermSpinner);
        modifyCourse = findViewById(R.id.modifyAssessmentCourseSpinner);
        modifyInstructor = findViewById(R.id.modifyAssessmentInstructorSpinner);

        modifyTitle.setText(aTitle);

        modifyStartDate.setDate(aSDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());
        modifyEndDate.setDate(aEDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());

        if (aType) {
            obj.toggle();
        } else {
            per.toggle();
        }

        setTermSpinner();
        setCourseSpinner();
        setInstructorSpinner();

        modifyTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setCourseSpinnerbyTerm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        modifyCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                modifyStartDate.setMinDate(0);
                modifyEndDate.setMinDate(0);
                modifyStartDate.setMinDate(getCalendarMinRange());
                modifyStartDate.setMaxDate(getCalendarMaxRange());
                modifyEndDate.setMinDate(getCalendarMinRange());
                modifyEndDate.setMaxDate(getCalendarMaxRange());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        modifyStartDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                aSDate = LocalDate.of( year, month, dayOfMonth );
            }
        });

        modifyEndDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                aEDate = LocalDate.of( year, month, dayOfMonth );
            }
        });

    }

    private void setCourseSpinnerbyTerm() {

        List<Course> courseList = dbRepository.getAllCourses();
        List<Course> tempList = new ArrayList<>();


        if (modifyTerm.getSelectedItem() != null) {
            Term selectedTerm = (Term) modifyTerm.getSelectedItem();

            for (Course course : courseList) {
                if (course.getTermID() == selectedTerm.getTermID()) {
                    tempList.add(course);
                }
            }

            ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(this,
                    android.R.layout.simple_spinner_item, tempList);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            modifyCourse.setAdapter(dataAdapter);

        } else {
            ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(this,
                    android.R.layout.simple_spinner_item, courseList);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            modifyCourse.setAdapter(dataAdapter);
        }

    }

    private long getCalendarMinRange() {

        Course selectedCourse = (Course) modifyCourse.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(selectedCourse.getStartDate().getYear(), selectedCourse.getStartDate().getMonthValue()-1, selectedCourse.getStartDate().getDayOfMonth());

        return cal.getTimeInMillis();
    }

    private long getCalendarMaxRange(){
        Course selectedCourse = (Course) modifyCourse.getSelectedItem();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(selectedCourse.getEndDate().getYear(), selectedCourse.getEndDate().getMonthValue()-1, selectedCourse.getEndDate().getDayOfMonth());

        return cal.getTimeInMillis();
    }

    private void setTermSpinner() {

        List<Term> termList = dbRepository.getAllTerms();

        int position = -1;

        ArrayAdapter<Term> dataAdapter = new ArrayAdapter<Term>(this,
                android.R.layout.simple_spinner_item, termList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modifyTerm.setAdapter(dataAdapter);

        for (Term term : termList) {
            position = position + 1;
            if (tID == term.getTermID()) {
                break;
            }
        }

        modifyTerm.setSelection(position);


    }

    private void setCourseSpinner() {

        List<Course> courseList = dbRepository.getAllCourses();

        int position = -1;

        ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(this,
                android.R.layout.simple_spinner_item, courseList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modifyCourse.setAdapter(dataAdapter);

        for (Course course: courseList) {
            position = position + 1;
            if (cID == course.getCourseID()) {
                break;
            }
        }
        modifyCourse.setSelection(position);

    }

    private void setInstructorSpinner() {

        List<Instructor> instructorList = dbRepository.getAllInstructors();

        int position = -1;

        ArrayAdapter<Instructor> dataAdapter = new ArrayAdapter<Instructor>(this,
                android.R.layout.simple_spinner_item, instructorList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modifyInstructor.setAdapter(dataAdapter);

        for (Instructor inst : instructorList) {
            position = position + 1;
            if (iID == inst.getInstructorID()) {
                break;
            }
        }

        modifyInstructor.setSelection(position);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ModifyAssessment.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {
        EditText eTitle = findViewById(R.id.modifyAssessmentTitle);
        RadioButton obj = findViewById(R.id.modifyObjectiveRB);

        Term selectedTerm = (Term) modifyTerm.getSelectedItem();
        Course selectedCourse = (Course) modifyCourse.getSelectedItem();
        Instructor selectedInstructor = (Instructor) modifyInstructor.getSelectedItem();


        String aTitle = eTitle.getText().toString();
        int termID = -1;
        int courseID = -1;
        int instructorID = -1;

        aType = obj.isChecked();

        try {
            termID = selectedTerm.getTermID();
            courseID = selectedCourse.getCourseID();
            instructorID = selectedInstructor.getInstructorID();
        } catch (NullPointerException e){
            AlertDialog alertDialog = new AlertDialog.Builder(ModifyAssessment.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Assessment. Please ensure all fields have valid values.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        assessment = new Assessment(modifyID, aTitle, aSDate, aEDate, courseID, instructorID, termID, aType);

        if (aTitle.isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(ModifyAssessment.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Assessment. Please ensure all fields have valid values.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else if (aEDate.isBefore(aSDate)){

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyAssessment.this).create();
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

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyAssessment.this).create();
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
            dbRepository.updateAssessment(assessment);

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyAssessment.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(assessment.getAssessTitle() + " has been modified successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(ModifyAssessment.this, SelectAssessment.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }

    }

    public void onClickDelete(View view) {

        List<Assessment> allAssessments = dbRepository.getAllAssessments();

        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyAssessment.this);
        builder.setCancelable(true);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this Assessment?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Assessment assessment : allAssessments) {
                            if (assessment.getAssessID() == modifyID) {
                                dbRepository.deleteAssessment(assessment);

                                AlertDialog alertDialog = new AlertDialog.Builder(ModifyAssessment.this).create();
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage(assessment.getAssessTitle() + " has been deleted successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        Intent intent = new Intent(ModifyAssessment.this, SelectAssessment.class);
                                        startActivity(intent);
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