package TestApp.StudentSchedulingApp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;
import TestApp.StudentSchedulingApp.ViewHolder.AssessmentAdapter;

public class SelectAssessment extends AppCompatActivity {

    DBRepository dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_assessment);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        dbRepository = new DBRepository(getApplication());

        List<Assessment> allAssessments = dbRepository.getAllAssessments();

        RecyclerView assessmentRV = findViewById(R.id.selectAssessmentRV);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRV.setAdapter(assessmentAdapter);
        assessmentRV.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(SelectAssessment.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAddAssessment(View view) {

        if (dbRepository.getAllTerms().size() < 1 ||
                dbRepository.getAllCourses().size() < 1 ||
                dbRepository.getAllInstructors().size() < 1 ) {

            AlertDialog alertDialog = new AlertDialog.Builder(SelectAssessment.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please add Terms, Courses, and Instructors before adding Assessments.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            Intent intent = new Intent(SelectAssessment.this, AddAssessment.class);
            startActivity(intent);
        }
    }
}