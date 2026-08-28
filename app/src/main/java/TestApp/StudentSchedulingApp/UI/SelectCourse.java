package TestApp.StudentSchedulingApp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;
import TestApp.StudentSchedulingApp.ViewHolder.CourseAdapter;

public class SelectCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);


        DBRepository dbRepository = new DBRepository(getApplication());
        List<Course> allCourses = dbRepository.getAllCourses();

        RecyclerView courseRV = findViewById(R.id.selectCourseRV);

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseRV.setAdapter(courseAdapter);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(SelectCourse.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAddCourse(View view) {

        Intent intent = new Intent(SelectCourse.this, AddCourse.class);
        startActivity(intent);
    }
}
