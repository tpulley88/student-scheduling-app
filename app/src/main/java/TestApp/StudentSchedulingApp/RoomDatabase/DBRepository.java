package TestApp.StudentSchedulingApp.RoomDatabase;

import android.app.Application;

import java.util.List;

import TestApp.StudentSchedulingApp.DAO.AssessmentDAO;
import TestApp.StudentSchedulingApp.DAO.CourseDAO;
import TestApp.StudentSchedulingApp.DAO.InstructorDAO;
import TestApp.StudentSchedulingApp.DAO.TermDAO;
import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.Entities.Term;

public class DBRepository {

    //Access Assessment DAO and create Assessment list
    private final AssessmentDAO accessAssessmentDAO;
    private List<Assessment> allAssessments;

    //Access Course DAO and create Course list
    private final CourseDAO accessCourseDAO;
    private List<Course> allCourses;

    //Access Instructor DAO and create Instructor list
    private final InstructorDAO accessInstructorDAO;
    private List<Instructor> allInstructors;

    //Access Term DAO and create Term list
    private final TermDAO accessTermDAO;
    private List<Term> allTerms;

    public DBRepository(Application app) {
        StudentScheduleDB db = StudentScheduleDB.getDatabase(app);
        accessAssessmentDAO = db.assessmentDAO();
        accessCourseDAO = db.courseDAO();
        accessTermDAO = db.termDAO();
        accessInstructorDAO = db.instructorDAO();
    }

    //ASSESSMENT COMMANDS

    //Returns a list of all Assessments
    public List<Assessment> getAllAssessments() {

        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            allAssessments = accessAssessmentDAO.listAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allAssessments;
    }

    //Adds Assessment to database
    public void addAssessment(Assessment assessmentToAdd) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessAssessmentDAO.insertAssess(assessmentToAdd);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Updates Assessment
    public void updateAssessment(Assessment assessmentToUpdate) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessAssessmentDAO.updateAssess(assessmentToUpdate);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes selected Assessment
    public void deleteAssessment(Assessment assessmentToDelete) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessAssessmentDAO.deleteAssess(assessmentToDelete);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes all Assessments
    public void deleteAllAssessments() {
        StudentScheduleDB.databaseWriteExecutor.execute(accessAssessmentDAO::deleteAllAssessments);

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //COURSE COMMANDS

    //Returns a list of all Courses
    public List<Course> getAllCourses() {
        StudentScheduleDB.databaseWriteExecutor.execute(()-> {
            allCourses = accessCourseDAO.listAllCourses();
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allCourses;


    }

    //Adds Course to database
    public void addCourse(Course courseToAdd) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessCourseDAO.insertCourse(courseToAdd);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Updates Course
    public void updateCourse(Course courseToUpdate) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessCourseDAO.updateCourse(courseToUpdate);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes selected Course
    public void deleteCourse(Course courseToDelete) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessCourseDAO.deleteCourse(courseToDelete);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes all Courses
    public void deleteAllCourses() {
        StudentScheduleDB.databaseWriteExecutor.execute(accessCourseDAO::deleteAllCourses);

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //TERM COMMANDS

    //Returns a list of all Terms
    public List<Term> getAllTerms() {
        StudentScheduleDB.databaseWriteExecutor.execute(()-> {
            allTerms = accessTermDAO.listAllTerms();
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allTerms;
    }

    //Adds Term to database
    public void addTerm(Term termToAdd) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessTermDAO.insertTerm(termToAdd);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Updates Term
    public void updateTerm(Term termToUpdate) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessTermDAO.updateTerm(termToUpdate);
        });
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes selected Term
    public void deleteTerm(Term termToDelete) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessTermDAO.deleteTerm(termToDelete);
        });
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes all Terms
    public void deleteAllTerms() {
        StudentScheduleDB.databaseWriteExecutor.execute(accessTermDAO::deleteAllTerms);
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //INSTRUCTOR COMMANDS

    //Returns a list of all Instructors
    public List<Instructor> getAllInstructors() {

        StudentScheduleDB.databaseWriteExecutor.execute(()-> {
            allInstructors = accessInstructorDAO.listAllInstructors();
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allInstructors;
        }

    //Adds Instructor to database
    public void addInstructor(Instructor instructorToAdd) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessInstructorDAO.insertInstructor(instructorToAdd);
        });
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Updates Instructor
    public void updateInstructor(Instructor instructorToUpdate) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessInstructorDAO.updateInstructor(instructorToUpdate);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes selected Instructor
    public void deleteInstructor(Instructor instructorToDelete) {
        StudentScheduleDB.databaseWriteExecutor.execute(() -> {
            accessInstructorDAO.deleteInstructor(instructorToDelete);
        });

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes all Instructors
    public void deleteAllInstructors() {
        StudentScheduleDB.databaseWriteExecutor.execute(accessInstructorDAO::deleteAllInstructors);

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
