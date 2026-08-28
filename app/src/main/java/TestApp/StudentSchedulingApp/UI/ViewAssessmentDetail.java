package TestApp.StudentSchedulingApp.UI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.Receivers.AssessmentEndReceiver;
import TestApp.StudentSchedulingApp.Receivers.AssessmentStartReceiver;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class ViewAssessmentDetail extends AppCompatActivity {

    DBRepository dbRepository = new DBRepository(getApplication());

    private int modifyID;
    LocalDate startDate;
    LocalDate endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        modifyID = getIntent().getExtras().getInt("Assessment ID");
        String aTitle = getIntent().getStringExtra("Assessment Title");
        startDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("Start Date"));
        endDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("End Date"));
        boolean aType = getIntent().getExtras().getBoolean("Type");
        int tID = getIntent().getExtras().getInt("Term ID");
        int cID = getIntent().getExtras().getInt("Course ID");
        int iID = getIntent().getExtras().getInt("Instructor ID");

        TextView title = findViewById(R.id.viewAssessmentTitle);
        TextView start = findViewById(R.id.viewAssessmentStartDate);
        TextView end = findViewById(R.id.viewAssessmentEndDate);
        TextView type = findViewById(R.id.viewAssessmentType);
        TextView term = findViewById(R.id.viewAssessmentTerm);
        TextView instructor = findViewById(R.id.viewAssessmentInstructor);
        TextView course = findViewById(R.id.viewAssessmentCourse);

        title.setText(aTitle);
        start.setText(startDate.toString());
        end.setText(endDate.toString());
        type.setText(getType(aType));
        term.setText(getTerm(tID));
        instructor.setText(getInstructor(iID));
        course.setText(getCourse(cID));
    }

    private String getCourse(int cID) {

        List<Course> allCourses = dbRepository.getAllCourses();

        for (Course course : allCourses) {
            if (course.getCourseID() == cID){
                return course.toString();
            }
        }
        return "None";

    }

    private String getInstructor(int iID) {

        List<Instructor> allInstructors = dbRepository.getAllInstructors();

        for (Instructor instructor : allInstructors) {
            if (instructor.getInstructorID() == iID){
                return instructor.toString();
            }
        }
        return "None";

    }

    private String getTerm(int tID) {

        List<Term> allTerms = dbRepository.getAllTerms();

        for (Term term : allTerms) {
            if (term.getTermID() == tID){
                return term.toString();
            }
        }
        return "None";

    }

    private String getType(boolean atype) {

        if (atype) {
            return "Objective";
        } else {
            return "Performance";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ViewAssessmentDetail.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickModify(View view) {

        Intent intent = new Intent(ViewAssessmentDetail.this, ModifyAssessment.class);
        intent.putExtra("Assessment ID", getIntent().getExtras().getInt("Assessment ID"));
        intent.putExtra("Assessment Title", getIntent().getStringExtra("Assessment Title"));
        intent.putExtra("Start Date", (Long) getIntent().getExtras().get("Start Date"));
        intent.putExtra("End Date", (Long) getIntent().getExtras().get("End Date"));
        intent.putExtra("Type", getIntent().getExtras().getBoolean("Type"));
        intent.putExtra("Term ID", getIntent().getExtras().getInt("Term ID"));
        intent.putExtra("Course ID", getIntent().getExtras().getInt("Course ID"));
        intent.putExtra("Instructor ID", getIntent().getExtras().getInt("Instructor ID"));
        startActivity(intent);

    }

    public void onClickDelete(View view) {

        List<Assessment> allAssessments = dbRepository.getAllAssessments();

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewAssessmentDetail.this);
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

                                AlertDialog alertDialog = new AlertDialog.Builder(ViewAssessmentDetail.this).create();
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage(assessment.getAssessTitle() + " has been deleted successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        Intent intent = new Intent(ViewAssessmentDetail.this, SelectAssessment.class);
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

    public void onClickNotifyStart(View view) throws ParseException {

        String dateStart = startDate.toString();
        String ssdf = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(ssdf, Locale.US);
        Date sDate = sdf.parse(dateStart);

        Long triggerStartDate = sDate.getTime();

        Intent intent = new Intent(ViewAssessmentDetail.this, AssessmentStartReceiver.class);
        intent.putExtra("Assessment ID", getIntent().getExtras().getInt("Assessment ID"));
        intent.putExtra("Assessment Title", getIntent().getStringExtra("Assessment Title"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewAssessmentDetail.this, ++Home.pendingStartAssessmentIntent, intent, 0);

        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartDate, pendingIntent);

        AlertDialog alertDialog = new AlertDialog.Builder(ViewAssessmentDetail.this).create();
        alertDialog.setTitle("Success");
        alertDialog.setMessage("You will receive a notification for " + (getIntent().getStringExtra("Assessment Title")) +
                "'s start date on " + sDate);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public void onClickNotifyEnd(View view) throws ParseException {

        String dateEnd = endDate.toString();
        String esdf = "yyyy-MM-dd";
        SimpleDateFormat sdf2 = new SimpleDateFormat(esdf, Locale.US);
        Date eDate = sdf2.parse(dateEnd);

        Long triggerEndDate = eDate.getTime();

        Intent intent = new Intent(ViewAssessmentDetail.this, AssessmentEndReceiver.class);
        intent.putExtra("Assessment ID", getIntent().getExtras().getInt("Assessment ID"));
        intent.putExtra("Assessment Title", getIntent().getStringExtra("Assessment Title"));
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(ViewAssessmentDetail.this, ++Home.pendingEndAssessmentIntent, intent, 0);

        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerEndDate, pendingIntent2);

        AlertDialog alertDialog = new AlertDialog.Builder(ViewAssessmentDetail.this).create();
        alertDialog.setTitle("Success");
        alertDialog.setMessage("You will receive a notification for " + (getIntent().getStringExtra("Assessment Title")) +
                "'s end date on " + eDate);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }


}