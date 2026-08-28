package TestApp.StudentSchedulingApp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.RoomDatabase.DBRepository;
import TestApp.StudentSchedulingApp.ViewHolder.TermAdapter;

public class SelectTerm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_term);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_24);

        DBRepository dbRepository = new DBRepository(getApplication());
        List<Term> allTerms = dbRepository.getAllTerms();

        RecyclerView termRV = findViewById(R.id.selectTermRV);

        final TermAdapter termAdapter = new TermAdapter(this);
        termRV.setAdapter(termAdapter);
        termRV.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(SelectTerm.this, Home.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAddTerm(View view) {

        Intent intent = new Intent(SelectTerm.this, AddTerm.class);
        startActivity(intent);
    }
}
