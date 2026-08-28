package TestApp.StudentSchedulingApp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "Term_Table")
public class Term {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Term_ID") private final int termID;

    @ColumnInfo(name = "Term_Title") @NonNull private String termTitle;

    @ColumnInfo(name = "Start_Date") @NonNull private LocalDate startDate;

    @ColumnInfo(name = "End_Date") @NonNull private LocalDate endDate;

    public Term(int termID, @NonNull String termTitle, @NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTermID() {
        return termID;
    }

    @NonNull
    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(@NonNull String termTitle) {
        this.termTitle = termTitle;
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

    @Override
    public String toString(){
        return("#" + termID + " " + termTitle);
    }

}
