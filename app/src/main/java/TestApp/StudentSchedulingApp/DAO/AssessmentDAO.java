package TestApp.StudentSchedulingApp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import TestApp.StudentSchedulingApp.Entities.Assessment;

@Dao
public interface AssessmentDAO {

    //Adds new Assessment to Database
    @Insert
    void insertAssess(Assessment assesToInsert);

    //Updates Assessment
    @Update
    void updateAssess(Assessment assessToUpdate);

    //Deletes selected Assessment
    @Delete
    void deleteAssess(Assessment assessToDelete);

    //Deletes all Assessments
    @Query("DELETE FROM Assessment_Table") void deleteAllAssessments();

    //Returns a list of Assessments
    @Query("SELECT * FROM Assessment_Table")
    List<Assessment> listAllAssessments();



}
