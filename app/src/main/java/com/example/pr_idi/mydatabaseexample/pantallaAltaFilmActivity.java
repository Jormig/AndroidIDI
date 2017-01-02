package com.example.pr_idi.mydatabaseexample;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.id.edit;

/**
 * Created by Haloman on 25/12/2016.
 */

public class PantallaAltaFilmActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pantallaaltafilm);
        super.onCreateDraweron(R.layout.pantallaaltafilm);
        EditText et = (EditText) findViewById(R.id.editNotaCritica);
        et.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10)});
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucrearpeli, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_item:
                MySQLiteHelper  dbHelper = new MySQLiteHelper(this);
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                Film film = new Film();
                FilmData filmData = new FilmData(this);
                EditText edit =  (EditText) findViewById(R.id.editTitulo);
                film.setTitle(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editPais);
                film.setCountry(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editAny);
                if(edit.getText().toString().equals(""))
                    film.setYear(0);
                else
                    film.setYear(Integer.valueOf(edit.getText().toString()));
                edit =  (EditText) findViewById(R.id.editProt);
                film.setProtagonist(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editDirector);
                film.setDirector(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editNotaCritica);
                if(edit.getText().toString().equals(""))
                    film.setCritics_rate(0);
                else
                    film.setCritics_rate(Integer.valueOf(edit.getText().toString()));
                filmData.open();
                filmData.createFilm(film);
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Pelicula "+ film.getTitle() +" creada", Toast.LENGTH_SHORT);

                toast1.show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
