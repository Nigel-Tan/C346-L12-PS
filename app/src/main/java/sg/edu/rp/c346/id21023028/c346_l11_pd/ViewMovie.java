package sg.edu.rp.c346.id21023028.c346_l11_pd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ViewMovie extends AppCompatActivity {

    ListView lv;
    CustomAdapter adapter;
    ArrayAdapter<String> ratingAdapter;
    Button btnReturn;
    String order = " ASC";
    ArrayList<Movie> al;
    ArrayList<String> ratingAl;
    ArrayList<String> finalSpinnerList;
    Spinner spinnerActivity;

    @Override
    protected void onResume() {
        super.onResume();
        populateSpinner();
        dataPopulate();
        spinnerActivity.setSelection(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
//population for spinner
        DBHelper db = new DBHelper(ViewMovie.this);
        ratingAl = db.populateSpinner();
        finalSpinnerList = new ArrayList<>();
        finalSpinnerList.add("Select rating to filter");
        for (String rating : ratingAl) {
            finalSpinnerList.add(rating);
        }
        ratingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, finalSpinnerList);
        ratingAdapter.notifyDataSetChanged();
        db.close();

        //link view to variables
        spinnerActivity = findViewById(R.id.spinnerRating1);
        btnReturn = findViewById(R.id.btnReturn);
        lv = findViewById(R.id.lv);

        //create adapter and needed arraylist
        al = new ArrayList<>();
        adapter = new CustomAdapter(this, R.layout.row, al);

        //set adapter
        spinnerActivity.setAdapter(ratingAdapter);
        lv.setAdapter(adapter);

        dataPopulate();

        //manage spinner value
        spinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ViewMovie.this);
                al.clear();
                if (position==0){ //default value selected
                    al.addAll(dbh.getMovie(order));
                }
                else{
                    String selectedValue = parent.getItemAtPosition(position).toString();
                    al.addAll(dbh.getMoviesByRating(order, selectedValue));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //return back button
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Movie data = al.get(position);
                Intent i = new Intent(ViewMovie.this,
                        EditMovie.class);
                i.putExtra("movie", data);
                startActivity(i);
            }
        });

    }

    //method to populate data for listview
    private void dataPopulate(){

        //clear data
        al.clear();

        // Create the DBHelper object, passing in the
        // activity's Context (get instance of DB)
        DBHelper db = new DBHelper(ViewMovie.this);

        //populate arraylist of Movie objects
        ArrayList<Movie> objectList = db.getMovie(order);

        db.close();
        al.addAll(objectList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.Ascending) {
            order = " ASC";
            return true;
        } else if (id == R.id.Descending) {
            order = " DESC";
            return true;
        } else {
            order = " ASC";
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateSpinner(){
        Log.i("spinner","populated");
        DBHelper db = new DBHelper(ViewMovie.this);
        ratingAl = db.populateSpinner();
        finalSpinnerList.clear();
        finalSpinnerList.add("Select rating value to filter");
        for (String rating : ratingAl) {
            finalSpinnerList.add(rating);
        }

        ratingAdapter.notifyDataSetChanged();
        db.close();
    }
}