package TestApp.StudentSchedulingApp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Instructor;

@Dao
public interface InstructorDAO {

    //Adds new Instructor to Database
    @Insert
    void insertInstructor(Instructor instructorToInsert);

    //Updates Instructor
    @Update
    void updateInstructor(Instructor instructorToUpdate);

    //Deletes selected Instructor
    @Delete
    void deleteInstructor(Instructor instructorToDelete);

    //Deletes all Instructors
    @Query("DELETE FROM Instructor_Table") void deleteAllInstructors();

    //Returns a list of Instructors
    @Query("SELECT * FROM Instructor_Table")
    List<Instructor> listAllInstructors();
}
