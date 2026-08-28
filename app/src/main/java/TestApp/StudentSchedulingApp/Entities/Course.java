package TestApp.StudentSchedulingApp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.time.LocalDate;

@Entity(tableName = "Course_Table")
public class Course {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Course_ID") private final int courseID;

    @ColumnInfo(name = "Course_Title") @NonNull private String courseTitle;

    @ColumnInfo(name = "Start_Date") @NonNull private LocalDate startDate;

    @ColumnInfo(name = "End_Date") @NonNull private LocalDate endDate;

    @ColumnInfo(name = "Status") @NonNull private String courseStatus;

    @ColumnInfo(name = "Instructor_ID") private int instrID;

    @ColumnInfo(name = "Term_ID") private int termID;

    @ColumnInfo(name = "Course_Notes") private String courseNotes;

    public Course(int courseID, @NonNull String courseTitle, @NonNull LocalDate startDate, @NonNull LocalDate endDate, String courseStatus, int instrID, int termID, String courseNotes) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.instrID = instrID;
        this.termID = termID;
        this.courseNotes = courseNotes;
    }

    public int getCourseID() {
        return courseID;
    }

    @NonNull
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(@NonNull String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @NonNull
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull LocalDate startDate) {
        this.startDate = startDate;
    }

    @NonNull
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NonNull LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getInstrID() {
        return instrID;
    }

    public void setInstrID(int instrID) {
        this.instrID = instrID;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    @Override
    public String toString(){
        return("#" + courseID + " " + courseTitle);
    }

}
