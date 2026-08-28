package TestApp.StudentSchedulingApp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import TestApp.StudentSchedulingApp.Entities.Term;

@Dao
public interface TermDAO {

    //Adds new Term to Database
    @Insert
    void insertTerm(Term termToInsert);

    //Updates Term
    @Update
    void updateTerm(Term termToUpdate);

    //Deletes selected Term
    @Delete
    void deleteTerm(Term termToDelete);

    //Deletes all Terms
    @Query("DELETE FROM Term_Table") void deleteAllTerms();

    //Returns a list of Terms
    @Query("SELECT * FROM Term_Table")
    List<Term> listAllTerms();

}
