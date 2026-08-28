package TestApp.StudentSchedulingApp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;
import TestApp.StudentSchedulingApp.ViewHolder.CourseAdapter;
import TestApp.StudentSchedulingApp.ViewHolder.InstructorAdapter;

public class SelectInstructor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_instructor);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        DBRepository dbRepository = new DBRepository(getApplication());
        List<Instructor> allInstructors = dbRepository.getAllInstructors();

        RecyclerView instructorRV = findViewById(R.id.selectInstructorRV);

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
                Intent intent = new Intent(SelectInstructor.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAddInstructor(View view) {
        Intent intent = new Intent(SelectInstructor.this, AddInstructor.class);
        startActivity(intent);
    }
}