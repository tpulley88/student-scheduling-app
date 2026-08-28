package TestApp.StudentSchedulingApp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Instructor_Table")
public class Instructor {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Instructor_ID") private final int instructorID;

    @ColumnInfo(name = "Instructor_Name") @NonNull private String instructorName;

    @ColumnInfo(name = "Email") @NonNull private String instructorEmail;

    @ColumnInfo(name = "Phone") @NonNull private String instructorPhone;

    public Instructor(int instructorID, @NonNull String instructorName, @NonNull String instructorEmail, @NonNull String instructorPhone) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
    }

    public int getInstructorID() {
        return instructorID;
    }

    @NonNull
    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(@NonNull String instructorName) {
        this.instructorName = instructorName;
    }

    @NonNull
    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(@NonNull String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    @NonNull
    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(@NonNull String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    @Override
    public String toString(){
        return(instructorName + ", " + instructorPhone + ", " + instructorEmail);
    }
}
