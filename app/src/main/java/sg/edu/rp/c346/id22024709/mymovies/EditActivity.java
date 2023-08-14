package sg.edu.rp.c346.id22024709.mymovies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    TextView teTitle2, teGenre2, teYear2;
    Spinner ratingSpin2;
    Button btnUpdate, btnDelete,  btnReturn;
    String rating;
    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        teTitle2 = findViewById(R.id.teTitle2);
        teGenre2 = findViewById(R.id.teGenre2);
        teYear2 = findViewById(R.id.teYear2);
        ratingSpin2 = findViewById(R.id.ratingSpin2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnReturn = findViewById(R.id.btnReturn);

        Intent intentrec2 = getIntent();
        int pos = intentrec2.getIntExtra("position",0);
        data = (Movie) intentrec2.getSerializableExtra("alPass");

        teTitle2.setText(data.getTitle());
        teGenre2.setText(data.getGenre());
        teYear2.setText(String.valueOf(data.getYear()));
        rating = data.getRating();
        if (rating.equals("G")){
            ratingSpin2.setSelection(0);
        } else if (rating.equals("PG")){
            ratingSpin2.setSelection(1);
        } else if (rating.equals("PG13")){
            ratingSpin2.setSelection(2);
        } else if (rating.equals("NC16")){
            ratingSpin2.setSelection(3);
        } else if (rating.equals("M18")){
            ratingSpin2.setSelection(4);
        } else if (rating.equals("R21")){
            ratingSpin2.setSelection(5);
        }

        DBHelper db = new DBHelper(EditActivity.this);
        ratingSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper db = new DBHelper(EditActivity.this);
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                data.setTitle(teTitle2.getText().toString());
                data.setGenre(teGenre2.getText().toString());
                data.setYear(Integer.parseInt(teYear2.getText().toString()));
                data.setRating(rating);

                int updateResult = db.updateMovie(data);
                Log.d("EditActivity", "Update result: " + updateResult);

                db.close();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you wish to delete movie " + data.getTitle() + " ?");
                myBuilder.setCancelable(true);

                myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteMovie(data.get_id());
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });
                myBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog myDialog = myBuilder.create();
                myDialog.show();



            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if      (!(data.getTitle().equals(teTitle2.getText().toString())) ||
                        !(data.getGenre().equals(teGenre2.getText().toString())) ||
                        (data.getYear() != Integer.parseInt(teYear2.getText().toString())) ||
                        !(data.getRating().equals(rating))) {

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                    myBuilder.setTitle("Danger");
                    myBuilder.setMessage("Unsaved changes detected. Are you sure you wish to return to list page and discard changes?");
                    myBuilder.setCancelable(true);

                    myBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    myBuilder.setNegativeButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();


                } else {
                    finish();
                }



            }
        });
    }


}
