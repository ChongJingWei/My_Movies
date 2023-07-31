package sg.edu.rp.c346.id22024709.mymovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Showlist extends AppCompatActivity {
    private static final int EDIT_REQUEST_CODE = 1;
    ListView movieList;
    Button btnShowRate;
    Spinner rateSpin;
    CustomAdapter customAdapter;
    ArrayList<Movie> movieal;
    String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlist);

        movieList = findViewById(R.id.movieLv);
        btnShowRate = findViewById(R.id.btnShowRate);
        rateSpin = findViewById(R.id.rateSpinShow);

        DBHelper db = new DBHelper(Showlist.this);
        movieal = db.getMovie();

        customAdapter = new CustomAdapter(this, R.layout.row, movieal);
        movieList.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
        rating = "All";
        rateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper db = new DBHelper(Showlist.this);
                switch (position) {
                    case 0:
                        rating = "All";
                        break;
                    case 1:
                        rating = "G";
                        break;
                    case 2:
                        rating = "PG";
                        break;
                    case 3:
                        rating = "PG13";
                        break;
                    case 4:
                        rating = "NC16";
                        break;
                    case 5:
                        rating = "M18";
                        break;
                    case 6:
                        rating = "R21";
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        btnShowRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(Showlist.this);
                if (rating == "All"){
                    customAdapter.clear();
                    customAdapter.addAll(db.getMovie());
                } else {
                    customAdapter.clear();
                    customAdapter.addAll(db.getMovie(rating));
                }
                customAdapter.notifyDataSetChanged();



            }
        });

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DBHelper db = new DBHelper(Showlist.this);
                movieal = db.getMovie();
                Intent passSong = new Intent(Showlist.this,EditActivity.class);
                passSong.putExtra("position",position);
                passSong.putExtra("alPass",movieal.get(position));
                startActivityForResult(passSong, EDIT_REQUEST_CODE);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        DBHelper db = new DBHelper(Showlist.this);
        ArrayList<Movie> updatedSongList = db.getMovie();
        Log.d("Showlist", "Updated song list size: " + updatedSongList.size());
        movieal.clear();
        movieal.addAll(updatedSongList);
        customAdapter.notifyDataSetChanged();
        db.close();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            DBHelper db = new DBHelper(Showlist.this);
            movieal.clear();
            movieal.addAll(db.getMovie());
            customAdapter.notifyDataSetChanged();
            db.close();
        }
    }
}
