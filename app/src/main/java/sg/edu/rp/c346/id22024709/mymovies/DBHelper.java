package sg.edu.rp.c346.id22024709.mymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME="movies.db";
    private static final String TABLE_MOVIES = "Movies";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_GENRE = "Genre";
    private static final String COLUMN_YEAR = "Year";
    private static final String COLUMN_RATING = "Rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_MOVIES+  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " TEXT,"
                + COLUMN_RATING + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES );

        onCreate(db);
    }

    public long insertMovie(String title, String genre, int year, String rating){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, rating);
        long result = db.insert(TABLE_MOVIES, null, values);

        db.close();
        Log.d("SQL Insert","ID:"+ result);//id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<String> getMovieContent() {
        // Create an ArrayList that holds String objects
        ArrayList<String> tasks = new ArrayList<String>();
        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        // Run the query and get back the Cursor object
        Cursor cursor = db.query(TABLE_MOVIES, columns, null, null, null, null, null, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                // Add the task content to the ArrayList object
                //  getString(0) retrieves first column data
                //  getString(1) return second column data
                //  getInt(0) if data is an integer value
                tasks.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return tasks;
    }

    public ArrayList<Movie> getMovie() {
        ArrayList<Movie> tasks = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};

        Cursor cursor = db.query(TABLE_MOVIES, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = Integer.parseInt(cursor.getString(3));
                String rating = cursor.getString(4);
                Movie obj = new Movie(_id, title, genre, year, rating);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public ArrayList<Movie> getMovie(String rating) {
        ArrayList<Movie> tasks = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String selection = COLUMN_RATING + " = ?";
        String[] selectionArgs = {rating};
        Cursor cursor = db.query(TABLE_MOVIES, columns, selection, selectionArgs, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = Integer.parseInt(cursor.getString(3));
                rating = cursor.getString(4);
                Movie obj = new Movie(_id, title, genre, year, rating);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

        public ArrayList<Movie> getMovieGenre(String genre) {
        ArrayList<Movie> tasks = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String selection = COLUMN_GENRE + " = ?";
        String[] selectionArgs = {String.valueOf(genre)};
        Cursor cursor = db.query(TABLE_MOVIES, columns,  selection, selectionArgs,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genrez = cursor.getString(2);
                int year = Integer.parseInt(cursor.getString(3));
                String rating = cursor.getString(4);
                Movie obj = new Movie(_id, title, genrez, year, rating);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public int updateMovie(Movie data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_GENRE, data.getGenre());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_RATING, data.getRating());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_MOVIES, values, condition, args);
        db.close();
        return result;
    }
    public int deleteMovie (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_MOVIES, condition, args);
        db.close();
        return result;
    }

}
