package TestApp.StudentSchedulingApp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;
import TestApp.StudentSchedulingApp.ViewHolder.AssessmentAdapter;
import TestApp.StudentSchedulingApp.ViewHolder.CourseAdapter;
import TestApp.StudentSchedulingApp.ViewHolder.InstructorAdapter;
import TestApp.StudentSchedulingApp.ViewHolder.TermAdapter;

public class Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        DBRepository dbRepository = new DBRepository(getApplication());
        List<Term> allTerms = dbRepository.getAllTerms();
        List<Course> allCourses = dbRepository.getAllCourses();
        List<Assessment> allAssessments = dbRepository.getAllAssessments();
        List<Instructor> allInstructors = dbRepository.getAllInstructors();

        RecyclerView termRV = findViewById(R.id.overviewTermRV);
        RecyclerView courseRV = findViewById(R.id.overviewCourseRV);
        RecyclerView assessmentRV = findViewById(R.id.overviewAssessmentRV);
        RecyclerView instructorRV = findViewById(R.id.overviewInstructorRV);

        final TermAdapter termAdapter = new TermAdapter(this);
        termRV.setAdapter(termAdapter);
        termRV.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseRV.setAdapter(courseAdapter);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRV.setAdapter(assessmentAdapter);
        assessmentRV.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);

        final InstructorAdapter instructorAdapter = new InstructorAdapter(this);
        instructorRV.setAdapter(instructorAdapter);
        instructorRV.setLayoutManager(new LinearLayoutManager(this));
        instructorAdapter.setInstructors(allInstructors);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(Overview.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}