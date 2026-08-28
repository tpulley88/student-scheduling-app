package TestApp.StudentSchedulingApp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;
import TestApp.StudentSchedulingApp.ViewHolder.AssessmentAdapter;
import TestApp.StudentSchedulingApp.ViewHolder.SelectCourseAdapter;

public class AssessmentsByCourse extends AppCompatActivity {

    DBRepository dbRepository;
    List<Course> allCourses;
    List<Assessment> assessmentList;
    int courseID;
    String courseTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_by_course);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        dbRepository = new DBRepository(getApplication());

        try {
            courseID = getIntent().getExtras().getInt("Course ID");
            courseTitle = getIntent().getStringExtra("Course Title");
            TextView courseText = findViewById(R.id.selectedCourseText);
            courseText.setText(courseTitle);
        } catch (NullPointerException ignored) { }

        allCourses = dbRepository.getAllCourses();
        assessmentList = getAssessments();

        RecyclerView courseRV = findViewById(R.id.courseABCRV);
        RecyclerView assessmentRV = findViewById(R.id.assessmentABCRV);

        final SelectCourseAdapter selectCourseAdapter = new SelectCourseAdapter(this);
        courseRV.setAdapter(selectCourseAdapter);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        selectCourseAdapter.setTerms(allCourses);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRV.setAdapter(assessmentAdapter);
        assessmentRV.setLayoutManager(new LinearLayoutManager(this));

        try {
            if (assessmentList.size() > 0) {
                assessmentAdapter.setAssessments(assessmentList);
            }
        } catch (NullPointerException ignored) {}

    }

    private List<Assessment> getAssessments() {

        List<Assessment> allAssessments = dbRepository.getAllAssessments();
        List<Assessment> tmpList = new ArrayList<Assessment>();

        for (Assessment assess : allAssessments) {
            if (assess.getCourseID() == courseID) {
                tmpList.add(assess);
            }
        }

        return tmpList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(AssessmentsByCourse.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}