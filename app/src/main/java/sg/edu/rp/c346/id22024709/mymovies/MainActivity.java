package sg.edu.rp.c346.id22024709.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView teTitle, teGenre, teYear;
    Spinner ratingSpin;
    Button btnInsert, btnShow;
    String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teTitle = findViewById(R.id.teTitle);
        teGenre = findViewById(R.id.teGenre);
        teYear = findViewById(R.id.teYear);
        ratingSpin = findViewById(R.id.ratingSpin);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);


        DBHelper db = new DBHelper(MainActivity.this);
        ratingSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper db = new DBHelper(MainActivity.this);
                switch (position) {
                    case 0:
                        rating = "G";
                        break;
                    case 1:
                        rating = "PG";
                        break;
                    case 2:
                        rating = "PG13";
                        break;
                    case 3:
                        rating = "NC16";
                        break;
                    case 4:
                        rating = "M18";
                        break;
                    case 5:
                        rating = "R21";
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rating = "G";
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                String data_title = teTitle.getText().toString();
                String data_genre = teGenre.getText().toString();
                int data_year = Integer.parseInt(teYear.getText().toString());
                String data_rating = rating;
                long inserted_id = db.insertMovie(data_title, data_genre, data_year, data_rating);
                if (inserted_id != -1){

                }
                teTitle.setText("");
                teGenre.setText("");
                teYear.setText(null);
            }

        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                Intent display = new Intent(MainActivity.this, Showlist.class);
                startActivity(display);
            }
        });


    }
}