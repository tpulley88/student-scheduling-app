package TestApp.StudentSchedulingApp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "Assessment_Table")
public class Assessment {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Assessment_ID") private final int assessID;

    @ColumnInfo(name = "Assessment_Title") @NonNull private String assessTitle;

    @ColumnInfo(name = "Start_Date") @NonNull private LocalDate startDate;

    @ColumnInfo(name = "End_Date") @NonNull private LocalDate endDate;

    @ColumnInfo(name = "Course_ID") @NonNull private int courseID;

    @ColumnInfo(name = "Instructor_ID") @NonNull private int instructorID;

    @ColumnInfo(name = "Term_ID") @NonNull private int termID;

    @ColumnInfo(name = "Objective") @NonNull private boolean typeObjective;

    public Assessment(int assessID, @NonNull String assessTitle, @NonNull LocalDate startDate, @NonNull LocalDate endDate, int courseID, int instructorID, int termID, boolean typeObjective) {
        this.assessID = assessID;
        this.assessTitle = assessTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
        this.instructorID = instructorID;
        this.termID = termID;
        this.typeObjective = typeObjective;
    }

    public int getAssessID() {
        return assessID;
    }

    @NonNull
    public String getAssessTitle() {
        return assessTitle;
    }

    public void setAssessTitle(@NonNull String assessTitle) {
        this.assessTitle = assessTitle;
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public boolean isTypeObjective() {
        return typeObjective;
    }

    public void setTypeObjective(boolean typeObjective) {
        this.typeObjective = typeObjective;
    }

    @Override
    public String toString(){
        return("#" + assessID + " " + assessTitle);
    }
}
