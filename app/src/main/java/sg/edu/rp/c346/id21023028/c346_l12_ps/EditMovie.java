package sg.edu.rp.c346.id21023028.c346_l12_ps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditMovie extends AppCompatActivity {

    Movie data;

    Button btnUpdate, btnDelete, btnBack;
    EditText etTitle, etGenre, etYear;
    Spinner sp;
    String spinner_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        //link variable to UI
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        etTitle = findViewById(R.id.etTitle1);
        etGenre = findViewById(R.id.etGenre1);
        etYear = findViewById(R.id.etYear1);
        sp = findViewById(R.id.spinnerRating1);


        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("movie");

        //get rating value and set it
        int rating = getRating(data.getRating());
        sp.setSelection(rating);

        //set the fields
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));

        //btn delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditMovie.this);
                myBuilder.setTitle("⚠️ Delete");
                myBuilder.setMessage("Are you sure you want to delete? This action cannot be undone.");
                myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(EditMovie.this);
                        dbh.deleteMovie(data.get_id());
                        finish();
                    }
                });
                myBuilder.setNegativeButton("No",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkChange()){ //if changes detected
                    Toast.makeText(EditMovie.this,"Changes not saved",
                            Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditMovie.this);
                    myBuilder.setTitle("⚠️ Unsaved Changes");
                    myBuilder.setMessage("Are you sure you want to go back?");
                    myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    myBuilder.setNegativeButton("No",null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                }
                else{
                    finish();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()){

                    //get value
                    String title = etTitle.getText().toString();
                    String genre = etGenre.getText().toString();
                    int year = Integer.parseInt(etYear.getText().toString());
                    spinner_value = sp.getSelectedItem().toString();

                    //set value
                    data.setRating(spinner_value);
                    data.setYear(year);
                    data.setGenre(genre);
                    data.setTitle(title);

                    DBHelper dbh = new DBHelper(EditMovie.this);
                    dbh.updateMovie(data);
                    dbh.close();
                    finish();
                }

            }
        });



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
            Toast.makeText(EditMovie.this,"Update failed, check fields.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkChange(){
        boolean title = !etTitle.getText().toString().trim().equals(data.getTitle());
        boolean genre = !etGenre.getText().toString().trim().equals(data.getGenre());
        boolean year = !etYear.getText().toString().equals(String.valueOf(data.getYear()));
        boolean rating = !sp.getSelectedItem().toString().equals(data.getRating());

        if (title || genre || year || rating){ //if there is a change
            return true;
        }
        else{
            return false;
        }
    }

    private int getRating(String rating){
        switch(rating){
            case "G":
                return 0;
            case "PG":
                return 1;
            case "PG13":
                return 2;
            case "NC16":
                return 3;
            case "M18":
                return 4;
            default:
                return 5; // R21 or any other unknown rating
        }
    }
}