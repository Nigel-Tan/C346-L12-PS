package sg.edu.rp.c346.id21023028.c346_l12_ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnView;
    EditText etTitle, etGenre, etYear;
    Spinner sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link view and controls
        btnInsert = findViewById(R.id.btnInsert);
        btnView = findViewById(R.id.btnView);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        sp = findViewById(R.id.spinnerRating);

        //clear all fields
        reset();

        //when insert button clicked
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()){

                    //get values from fields
                    String title = etTitle.getText().toString();
                    String genre = etGenre.getText().toString();
                    int year = Integer.parseInt(etYear.getText().toString());
                    String spinner_value = sp.getSelectedItem().toString();


                    //intialize DB connection
                    DBHelper db = new DBHelper(MainActivity.this);

                    //insert value
                    db.insertMovie(title,year,genre,spinner_value);
                    reset();
                    Toast.makeText(MainActivity.this,"Insert Complete",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //when switch view button is clicked
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDataPresent()){
                    Toast.makeText(MainActivity.this,"No records to view",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //switch to view activity if there is records
                Intent intent = new Intent(MainActivity.this, ViewMovie.class);
                Log.i("hi","hi");
                startActivity(intent);
            }
        });
    }

    private void reset(){
        sp.setSelection(0);
        etTitle.setText("");
        etGenre.setText("");
        etYear.setText("");
    }

    private boolean validateFields(){
        boolean title = !etTitle.getText().toString().trim().isEmpty();
        boolean genre = !etGenre.getText().toString().trim().isEmpty();
        boolean year = !etYear.getText().toString().isEmpty();
        boolean yearValue = false;

        if (year){ //verify if the year is 4 digits (valid year)
            yearValue = etYear.getText().toString().length() ==4;
        }

        if (title && genre && year && yearValue){
            return true;
        }
        else{
            Toast.makeText(MainActivity.this,"Insert failed, check fields.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean checkDataPresent(){ //check if there is any records in the database
        DBHelper db = new DBHelper(MainActivity.this);
        ArrayList<Movie> objectList = db.getMovie(" ASC"); //order has no meaning, just to fill the argument
        db.close();
        return objectList.size() != 0; //if there is record, return true, else false
    }

}