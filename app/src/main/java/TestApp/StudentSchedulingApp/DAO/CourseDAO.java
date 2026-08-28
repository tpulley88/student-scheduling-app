package TestApp.StudentSchedulingApp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import TestApp.StudentSchedulingApp.Entities.Course;

@Dao
public interface CourseDAO {

    //Adds new Course to Database
    @Insert
    void insertCourse(Course courseToInsert);

    //Updates Course
    @Update
    void updateCourse(Course courseToUpdate);

    //Deletes selected Course
    @Delete
    void deleteCourse(Course courseToDelete);

    //Deletes all Courses
    @Query("DELETE FROM Course_Table") void deleteAllCourses();

    //Returns a list of Courses
    @Query("SELECT * FROM Course_Table")
    List<Course> listAllCourses();
}
