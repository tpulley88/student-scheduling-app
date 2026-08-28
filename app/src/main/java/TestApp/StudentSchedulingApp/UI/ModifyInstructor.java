package TestApp.StudentSchedulingApp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;

public class ModifyInstructor extends AppCompatActivity {

    int modifyID;
    EditText modifyName;
    EditText modifyEmail;
    EditText modifyPhone;


    DBRepository dbRepository = new DBRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_instructor);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        modifyID = getIntent().getExtras().getInt("Instructor ID");
        String eName = getIntent().getStringExtra("Instructor Name");
        String eEmail = getIntent().getStringExtra("Instructor Email");
        String ePhone = getIntent().getStringExtra("Instructor Phone");

        modifyName = findViewById(R.id.modifyInstructorName);
        modifyPhone = findViewById(R.id.modifyInstructorPhone);
        modifyEmail = findViewById(R.id.modifyInstructorEmail);

        modifyName.setText(eName);
        modifyEmail.setText(eEmail);
        modifyPhone.setText(ePhone);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ModifyInstructor.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {

        String iName = modifyName.getText().toString();
        String iPhone = modifyPhone.getText().toString();
        String iEmail = modifyEmail.getText().toString();

        System.out.println("Instructor ID: " + modifyID);

        Instructor instructor = new Instructor(modifyID, iName, iPhone, iEmail);

        if (iName.isEmpty() || iPhone.isEmpty() || iEmail.isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(ModifyInstructor.this).create();
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
            dbRepository.updateInstructor(instructor);

            AlertDialog alertDialog = new AlertDialog.Builder(ModifyInstructor.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage(instructor.getInstructorName() + " has been updated successfully.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(ModifyInstructor.this, SelectInstructor.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }

    }

    public void onClickDelete(View view) {

        List<Instructor> allInstructors = dbRepository.getAllInstructors();

        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyInstructor.this);
        builder.setCancelable(true);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this Instructor?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Instructor instructor : allInstructors) {
                            if (instructor.getInstructorID() == modifyID) {
                                dbRepository.deleteInstructor(instructor);

                                AlertDialog alertDialog = new AlertDialog.Builder(ModifyInstructor.this).create();
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage(instructor.getInstructorName() + " has been deleted successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        Intent intent = new Intent(ModifyInstructor.this, SelectInstructor.class);
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
