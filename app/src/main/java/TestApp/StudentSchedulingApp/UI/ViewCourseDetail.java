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

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.Receivers.CourseEndReceiver;
import TestApp.StudentSchedulingApp.Receivers.CourseStartReceiver;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class ViewCourseDetail extends AppCompatActivity {

    int modifyID;
    LocalDate startDate;
    LocalDate endDate;

    DBRepository dbRepository = new DBRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        modifyID = getIntent().getExtras().getInt("Course ID");
        String cTitle = getIntent().getStringExtra("Course Title");
        startDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("Start Date"));
        endDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("End Date"));
        String cStatus = getIntent().getStringExtra("Course Status");
        int iID = getIntent().getExtras().getInt("Instructor ID");
        int tID = getIntent().getExtras().getInt("Term ID");
        String notes = getIntent().getStringExtra("Course Notes");

        TextView title = findViewById(R.id.viewCourseTitle);
        TextView start = findViewById(R.id.viewCourseStartDate);
        TextView end = findViewById(R.id.viewCourseEndDate);
        TextView courseStatus = findViewById(R.id.viewCourseStatus);
        TextView instructor = findViewById(R.id.viewCourseInstructor);
        TextView term = findViewById(R.id.viewCourseTerm);
        TextView courseNotes = findViewById(R.id.viewCourseNotes);

        title.setText(cTitle);
        start.setText(startDate.toString());
        end.setText(endDate.toString());
        courseStatus.setText(cStatus);
        courseNotes.setText(notes);
        instructor.setText(getInstructor(iID));
        term.setText(getTerm(tID));
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

    private String getInstructor(int iID) {

        List<Instructor> allInstructors = dbRepository.getAllInstructors();

        for (Instructor instructor : allInstructors) {
            if (instructor.getInstructorID() == iID){
                return instructor.toString();
            }
        }
        return "None";

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ViewCourseDetail.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickModify(View view) {

        Intent intent = new Intent(ViewCourseDetail.this, ModifyCourse.class);
        intent.putExtra("Course ID", getIntent().getExtras().getInt("Course ID"));
        intent.putExtra("Course Title", getIntent().getStringExtra("Course Title"));
        intent.putExtra("Start Date", (Long) getIntent().getExtras().get("Start Date"));
        intent.putExtra("End Date", (Long) getIntent().getExtras().get("End Date"));
        intent.putExtra("Course Status", getIntent().getStringExtra("Course Status"));
        intent.putExtra("Instructor ID", getIntent().getExtras().getInt("Instructor ID"));
        intent.putExtra("Term ID", getIntent().getExtras().getInt("Term ID"));
        intent.putExtra("Course Notes", getIntent().getStringExtra("Course Notes"));
        startActivity(intent);

    }

    public void onClickDelete(View view) {

        List<Course> allCourses = dbRepository.getAllCourses();

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCourseDetail.this);
        builder.setCancelable(true);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this Course?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Course course: allCourses) {

                            System.out.println("Course List ID: " + course.getCourseID());
                            System.out.println("Modify ID: " + modifyID);

                            if (course.getCourseID() == modifyID) {
                                dbRepository.deleteCourse(course);

                                AlertDialog alertDialog = new AlertDialog.Builder(ViewCourseDetail.this).create();
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage(course.getCourseTitle() + " has been deleted successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(ViewCourseDetail.this, SelectCourse.class);
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

    public void shareNotes(View view) {

        Intent sIntent = new Intent();
        sIntent.setAction(Intent.ACTION_SEND);
        sIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("Course Notes"));
        sIntent.putExtra(Intent.EXTRA_TITLE, (getIntent().getStringExtra("Course Title") + "Notes"));
        sIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sIntent, null);
        startActivity(shareIntent);
    }

    public void onClickNotifyStart(View view) throws ParseException {

        String dateStart = startDate.toString();
        String ssdf = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(ssdf, Locale.US);
        Date sDate = sdf.parse(dateStart);

        Long triggerStartDate = sDate.getTime();

        Intent intent = new Intent(ViewCourseDetail.this, CourseStartReceiver.class);
        intent.putExtra("Course ID", getIntent().getExtras().getInt("Course ID"));
        intent.putExtra("Course Title", getIntent().getStringExtra("Course Title"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewCourseDetail.this, ++Home.pendingStartCourseIntent, intent, 0);

        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartDate, pendingIntent);

        AlertDialog alertDialog = new AlertDialog.Builder(ViewCourseDetail.this).create();
        alertDialog.setTitle("Success");
        alertDialog.setMessage("You will receive a notification for " + (getIntent().getStringExtra("Course Title")) +
                "'s start date on " + startDate);
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

        Intent intent = new Intent(ViewCourseDetail.this, CourseEndReceiver.class);
        intent.putExtra("Course ID", getIntent().getExtras().getInt("Course ID"));
        intent.putExtra("Course Title", getIntent().getStringExtra("Course Title"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewCourseDetail.this, ++Home.pendingEndCourseIntent, intent, 0);

        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerEndDate, pendingIntent);

        AlertDialog alertDialog = new AlertDialog.Builder(ViewCourseDetail.this).create();
        alertDialog.setTitle("Success");
        alertDialog.setMessage("You will receive a notification for " + (getIntent().getStringExtra("Course Title")) +
                "'s end date on " + endDate);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
}