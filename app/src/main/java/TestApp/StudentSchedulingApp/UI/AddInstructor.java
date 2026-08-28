package TestApp.StudentSchedulingApp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class AddInstructor extends AppCompatActivity {

    DBRepository dbRepository = new DBRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(AddInstructor.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {

        EditText gName = findViewById(R.id.addInstructorName);
        EditText gPhone = findViewById(R.id.addInstructorPhone);
        EditText gEmail = findViewById(R.id.addInstructorEmail);

        String iName = gName.getText().toString();
        String iPhone = gPhone.getText().toString();
        String iEmail = gEmail.getText().toString();

        Instructor instructor = new Instructor(0, iName, iPhone, iEmail);

        if (iName.isEmpty() || iPhone.isEmpty() || iEmail.isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(AddInstructor.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Unable to save Instructor. Please ensure all fields have valid values.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else{
            dbRepository.addInstructor(instructor);

            AlertDialog alertDialog = new AlertDialog.Builder(AddInstructor.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(instructor.getInstructorName() + " has been added successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(AddInstructor.this, SelectInstructor.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }

    }
}