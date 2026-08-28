package TestApp.StudentSchedulingApp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDate;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class AddTerm extends AppCompatActivity {

    private LocalDate startDate;
    private LocalDate endDate;

    DBRepository dbRepository = new DBRepository(getApplication());

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        CalendarView startCal = findViewById(R.id.addTermStartDate);
        CalendarView endCal = findViewById(R.id.addTermEndDate);

        startDate = LocalDate.now();
        endDate = LocalDate.now();


        startCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                startDate = LocalDate.of( year, month, dayOfMonth );
            }
        });

        endCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                endDate = LocalDate.of( year, month, dayOfMonth );
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(AddTerm.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {

        EditText eTitle = findViewById(R.id.addTermTitle);

        String tTitle = eTitle.getText().toString();

        Term term = new Term(0, tTitle, startDate, endDate);

        if (tTitle.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(AddTerm.this).create();
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

            AlertDialog alertDialog = new AlertDialog.Builder(AddTerm.this).create();
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
            dbRepository.addTerm(term);

            AlertDialog alertDialog = new AlertDialog.Builder(AddTerm.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(term.getTermTitle() + " has been added successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(AddTerm.this, SelectTerm.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }


    }
}