package com.example.pr_idi.mydatabaseexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Haloman on 20/11/2016.
 * and Taos
 */

public class ListFilmDeleteActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Film> myDataset;
    private FilmData myFilmData;
    private static boolean deleteMode = false;
    private  String from = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.listfilmview);
        super.onCreateDraweron(R.layout.listfilmview);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        setTitle("Modo Borrar");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //para snackbar consejos
        firstHelp();
        //comprueba intent desde NavDrag
        try {
            from = getIntent().getExtras().getString("from");
        } catch (Exception e) {
        }
        //setDeleteMode();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // specify an adapter (see also next example)
        myFilmData = new FilmData(this);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        myFilmData.open();
        String order = MySQLiteHelper.COLUMN_YEAR_RELEASE;
        myDataset = myFilmData.getAllFilms(order.concat(" DESC"));
        mAdapter = new ListFilmDeleteAdapter(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete_items, menu);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add_item:
                intent = new Intent(this, PantallaAltaFilmActivity.class);
                startActivity(intent);
            return true;
            case R.id.action_delete_item:
                String text = "";
                for (Film model : myDataset) {
                    if (model.isSelected()) {
                        text += model.getTitle();
                    }
                }
                Log.d("TAG","Output : " + text);
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Seleccione para eliminar.", Toast.LENGTH_SHORT);

                toast1.show();
            return true;
            case R.id.action_about:
                intent = new Intent(this, About.class);
                startActivity(intent);
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void firstHelp() {
        final SharedPreferences settings  = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);

        if (settings.getBoolean("habilitarConsejoListFilmDeleteActivity", true)) {

            Snackbar.make(findViewById(R.id.my_recycler_view), R.string.consejo_lisFilmDeleteAct, Snackbar.LENGTH_INDEFINITE)
                    //.setActionTextColor(Color.CYAN)
                    .setActionTextColor(getResources().getColor(R.color.snackbar_action))
                    .setAction("[X]", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("Snackbar", "Pulsada acción snackbar!");
                            SharedPreferences.Editor editor = settings .edit();
                            editor.putBoolean("habilitarConsejoListFilmDeleteActivity", false);
                            editor.commit();
                        }
                    })
                    .show();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        //setDeleteMode();
        finish();
    }




    public FilmData getMyFilmData(){
        return myFilmData;
    }
    public List<Film> getMyDataset(){
        return myDataset;
    }
    public static void setDeleteMode(){
        deleteMode = !deleteMode;
    }
    public static boolean getDeleteMode(){
        return deleteMode;
    }
}
