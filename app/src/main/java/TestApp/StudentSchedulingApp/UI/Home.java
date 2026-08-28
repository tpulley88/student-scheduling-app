package TestApp.StudentSchedulingApp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import TestApp.StudentSchedulingApp.R;

public class Home extends AppCompatActivity {

    public static int pendingStartCourseIntent;
    public static int pendingEndCourseIntent;
    public static int pendingStartAssessmentIntent;
    public static int pendingEndAssessmentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void onClickDashboard(View view) {
        Intent intent = new Intent(Home.this, Overview.class);
        startActivity(intent);
    }

    public void onClickCBT(View view) {
        Intent intent = new Intent(Home.this, CoursesByTerm.class);
        startActivity(intent);
    }

    public void onClickABC(View view) {
        Intent intent = new Intent(Home.this, AssessmentsByCourse.class);
        startActivity(intent);
    }

    public void onClickSelectTerm(View view) {
        Intent intent = new Intent(Home.this, SelectTerm.class);
        startActivity(intent);
    }

    public void onClickSelectCourse(View view) {
        Intent intent = new Intent(Home.this, SelectCourse.class);
        startActivity(intent);
    }

    public void onClickSelectAssessment(View view) {
        Intent intent = new Intent(Home.this, SelectAssessment.class);
        startActivity(intent);
    }

    public void onClickSelectInstructor(View view) {
        Intent intent = new Intent(Home.this, SelectInstructor.class);
        startActivity(intent);
    }

    public void onClickExit(View view) {
        finishAndRemoveTask();
    }

}