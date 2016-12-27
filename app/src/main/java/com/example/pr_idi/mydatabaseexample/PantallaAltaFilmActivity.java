
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

/**
 * Created by Haloman on 25/12/2016.
 */

public class PantallaAltaFilmActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantallaaltafilm);

        EditText et = (EditText) findViewById(R.id.editNotaCritica);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 10)});
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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

                EditText edit =  (EditText) findViewById(R.id.editTitulo);
                film.setTitle(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editPais);
                film.setCountry(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editAny);
                film.setYear(Integer.valueOf(edit.getText().toString()));

                edit =  (EditText) findViewById(R.id.editProt);
                film.setProtagonist(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editDirector);
                film.setDirector(edit.getText().toString());

                edit =  (EditText) findViewById(R.id.editNotaCritica);
                film.setCritics_rate(Integer.valueOf(edit.getText().toString()));

                return dbHelper.insertData(database,film);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
