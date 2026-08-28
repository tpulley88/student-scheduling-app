package TestApp.StudentSchedulingApp.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import TestApp.StudentSchedulingApp.DAO.AssessmentDAO;
import TestApp.StudentSchedulingApp.DAO.CourseDAO;
import TestApp.StudentSchedulingApp.DAO.InstructorDAO;
import TestApp.StudentSchedulingApp.DAO.TermDAO;
import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class},  version = 6, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class StudentScheduleDB extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract InstructorDAO instructorDAO();

    public static volatile StudentScheduleDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static StudentScheduleDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudentScheduleDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StudentScheduleDB.class, "Student Schedule Database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
