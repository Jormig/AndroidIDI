package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
        firstHelp();//para snackbar consejo
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
                boolean isEmpty = false;
                MySQLiteHelper  dbHelper = new MySQLiteHelper(this);
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                Film film = new Film();
                FilmData filmData = new FilmData(this);
                EditText edit =  (EditText) findViewById(R.id.editTitulo);
                isEmpty = edit.getText().toString().equals("");
                film.setTitle(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editPais);
                isEmpty = edit.getText().toString().equals("");
                film.setCountry(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editAny);
                isEmpty = edit.getText().toString().equals("");
                if(!isEmpty)
                    film.setYear(Integer.valueOf(edit.getText().toString()));

                edit =  (EditText) findViewById(R.id.editProt);
                isEmpty = edit.getText().toString().equals("");
                film.setProtagonist(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editDirector);
                isEmpty = edit.getText().toString().equals("");
                film.setDirector(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editNotaCritica);
                isEmpty = edit.getText().toString().equals("");
                if(!isEmpty)
                    film.setCritics_rate(Integer.valueOf(edit.getText().toString()));
                if(isEmpty){
                            Toast.makeText(getApplicationContext(),
                                    "Por favor, introduzca todos los campos", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    filmData.open();
                    filmData.createFilm(film);
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Pelicula " + film.getTitle() + " creada", Toast.LENGTH_SHORT);

                    toast1.show();
                    finish();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void firstHelp() {
        final SharedPreferences settings  = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);

        if (settings.getBoolean("habilitarConsejoPantallaAltaFilmActivity", true)) {

            Snackbar.make(findViewById(R.id.linearlayout_pantallaalta), R.string.consejo_PantallaAltaAct, Snackbar.LENGTH_INDEFINITE)
                    //.setActionTextColor(Color.CYAN)
                    .setActionTextColor(getResources().getColor(R.color.snackbar_action))
                    .setAction("[X]", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("Snackbar", "Pulsada acción snackbar!");
                            SharedPreferences.Editor editor = settings .edit();
                            editor.putBoolean("habilitarConsejoPantallaAltaFilmActivity", false);
                            editor.commit();
                        }
                    })
                    .show();
        }
    }
}
