package TestApp.StudentSchedulingApp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Ignore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;
import TestApp.StudentSchedulingApp.ViewHolder.CourseAdapter;
import TestApp.StudentSchedulingApp.ViewHolder.SelectTermAdapter;
import TestApp.StudentSchedulingApp.ViewHolder.TermAdapter;

public class CoursesByTerm extends AppCompatActivity {

    DBRepository dbRepository;
    List<Term> allTerms;
    List<Course> courseList;
    int termID;
    String termTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_by_term);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        dbRepository = new DBRepository(getApplication());

        try {
            termID = getIntent().getExtras().getInt("Term ID");
            termTitle = getIntent().getStringExtra("Term Title");
            TextView termText = findViewById(R.id.selectedTermText);
            termText.setText(termTitle);
        } catch (NullPointerException ignored) { }

        allTerms = dbRepository.getAllTerms();
        courseList = getCourses();

        RecyclerView termRV = findViewById(R.id.termCBTRV);
        RecyclerView courseRV = findViewById(R.id.courseCBTRV);

        final SelectTermAdapter selectTermAdapter = new SelectTermAdapter(this);
        termRV.setAdapter(selectTermAdapter);
        termRV.setLayoutManager(new LinearLayoutManager(this));
        selectTermAdapter.setTerms(allTerms);

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseRV.setAdapter(courseAdapter);
        courseRV.setLayoutManager(new LinearLayoutManager(this));

        try {
            if (courseList.size() > 0) {
                courseAdapter.setCourses(courseList);
            }
        } catch (NullPointerException ignored) {}

    }

    private List<Course> getCourses() {

        List<Course> allCourses = dbRepository.getAllCourses();
        List<Course> tmpList = new ArrayList<Course>();

        for (Course tCourse : allCourses) {
            if (tCourse.getTermID() == termID) {
                 tmpList.add(tCourse);
            }
        }

        return tmpList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(CoursesByTerm.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}