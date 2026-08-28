package TestApp.StudentSchedulingApp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class ModifyTerm extends AppCompatActivity {

    int modifyID;
    EditText modifyTitle;
    private CalendarView startCal;
    private CalendarView endCal;
    private LocalDate startDate;
    private LocalDate endDate;
    private String tTitle;

    DBRepository dbRepository = new DBRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_term);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        modifyID = getIntent().getExtras().getInt("Term ID");
        tTitle = getIntent().getStringExtra("Term Title");
        startDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("Start Date"));
        endDate = LocalDate.ofEpochDay((Long) getIntent().getExtras().get("End Date"));

        modifyTitle = findViewById(R.id.modifyTermTitle);
        startCal = findViewById(R.id.modifyTermStartDate);
        endCal = findViewById(R.id.modifyTermEndDate);

        modifyTitle.setText(tTitle);

        startCal.setDate(startDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());
        endCal.setDate(endDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());

        startCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                startDate = LocalDate.of(year, month, dayOfMonth);
            }
        });

        endCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                endDate = LocalDate.of(year, month, dayOfMonth);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ModifyTerm.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {

        EditText eTitle = findViewById(R.id.modifyTermTitle);

        String tTitle = eTitle.getText().toString();

        Term term = new Term(modifyID, tTitle, startDate, endDate);

        if (tTitle.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(ModifyTerm.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Term. Please ensure all fields have valid values.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else if (endDate.isBefore(startDate)) {

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyTerm.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Term. Please ensure start date is before end date.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else {
            dbRepository.updateTerm(term);

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyTerm.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(term.getTermTitle() + " has been updated successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(ModifyTerm.this, SelectTerm.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }

    }

    public void onClickDelete(View view) {

        List<Term> allTerms = dbRepository.getAllTerms();
        List<Course> allCourses = dbRepository.getAllCourses();
        boolean delete = true;

        for (Course course: allCourses) {

            if (course.getTermID() == modifyID) {

                delete = false;

                AlertDialog alertDialog = new AlertDialog.Builder(ModifyTerm.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Unable to delete Term. Please delete all associated courses first.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(ModifyTerm.this, CoursesByTerm.class);
                                startActivity(intent);

                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }

        if (delete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTerm.this);
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

                                AlertDialog alertDialog = new AlertDialog.Builder(ModifyTerm.this).create();
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage(term.getTermTitle() + " has been deleted successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        Intent intent = new Intent(ModifyTerm.this, SelectTerm.class);
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