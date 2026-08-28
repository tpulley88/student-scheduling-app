package TestApp.StudentSchedulingApp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class ViewTermDetail extends AppCompatActivity {

    int modifyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        modifyID = getIntent().getExtras().getInt("Term ID");
        String tTitle = getIntent().getStringExtra("Term Title");
        String startDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("Start Date")).toString();
        String endDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("End Date")).toString();

        TextView eTitle = findViewById(R.id.viewTermTitle);
        TextView eStart = findViewById(R.id.viewTermStartDate);
        TextView eEnd = findViewById(R.id.viewTermEndDate);

        eTitle.setText(tTitle);
        eStart.setText(startDate);
        eEnd.setText(endDate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ViewTermDetail.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickModify(View view) {

        Intent intent = new Intent(ViewTermDetail.this, ModifyTerm.class);
        intent.putExtra("Term ID", getIntent().getExtras().getInt("Term ID"));
        intent.putExtra("Term Title", getIntent().getStringExtra("Term Title"));
        intent.putExtra("Start Date", (Long) getIntent().getExtras().get("Start Date"));
        intent.putExtra("End Date", (Long) getIntent().getExtras().get("End Date"));
        startActivity(intent);

    }

    public void onClickDelete(View view) {

        DBRepository dbRepository = new DBRepository(getApplication());
        List<Term> allTerms = dbRepository.getAllTerms();
        List<Course> allCourses = dbRepository.getAllCourses();
        boolean delete = true;

        for (Course course: allCourses) {

            if (course.getTermID() == modifyID) {

                delete = false;

                AlertDialog alertDialog = new AlertDialog.Builder(ViewTermDetail.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Unable to delete Term. Please delete all associated courses first.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(ViewTermDetail.this, CoursesByTerm.class);
                                startActivity(intent);

                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }

        if (delete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewTermDetail.this);
            builder.setCancelable(true);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete this Term?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            for (Term term : allTerms) {
                                if (term.getTermID() == modifyID) {
                                    dbRepository.deleteTerm(term);

                                    AlertDialog alertDialog = new AlertDialog.Builder(ViewTermDetail.this).create();
                                    alertDialog.setTitle("Success");
                                    alertDialog.setMessage(term.getTermTitle() + " has been deleted successfully.");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            Intent intent = new Intent(ViewTermDetail.this, SelectTerm.class);
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
}