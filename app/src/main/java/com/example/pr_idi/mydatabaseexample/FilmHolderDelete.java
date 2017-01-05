package com.example.pr_idi.mydatabaseexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;


/**
 * Created by Haloman on 31/12/2016.
 * and Taos
 */
public class FilmHolderDelete extends SwappingHolder
        implements View.OnClickListener, View.OnLongClickListener {
    private Film mFilm;
    private Context ctxt;
    private TextView mTituloTextView, mPaisTextView,mAnyTextView,mDirectorTextView;
    private TextView mProtagonistaTextView, mNotaCriticaTextView;
    ActionMode mActionMode;
    public FilmHolderDelete(View itemView, MultiSelector multiSelector, Context ctxt) {
        super(itemView, multiSelector);
        mTituloTextView = (TextView)itemView.findViewById(R.id.textTitulo);
        mPaisTextView = (TextView)itemView.findViewById(R.id.textPais);
        mAnyTextView = (TextView)itemView.findViewById(R.id.textAny);
        mDirectorTextView = (TextView)itemView.findViewById(R.id.textDirector);
        mProtagonistaTextView = (TextView)itemView.findViewById(R.id.textProtagonista);
        mNotaCriticaTextView = (TextView)itemView.findViewById(R.id.textNotaCritica);
        this.ctxt = ctxt;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }
    public void bindFilm(Film film){
        mFilm = film;
        mTituloTextView.setText(mFilm.getTitle());
        mPaisTextView.setText(mFilm.getCountry());
        mAnyTextView.setText(String.valueOf(mFilm.getYear()));
        mDirectorTextView.setText(mFilm.getDirector());
        mProtagonistaTextView.setText(mFilm.getProtagonist());
        mNotaCriticaTextView.setText(String.valueOf(film.getCritics_rate()).concat("/10"));
    }
    @Override
    public boolean onLongClick(View v) {
        if(ListFilmDeleteActivity.getDeleteMode()) {
            setSelected(v);
        }
        return true;
    }

    @Override
    public void onClick(View v) {

            ListFilmDeleteActivity.setDeleteMode();
            ((ListFilmDeleteActivity)ctxt).startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = ((ListFilmDeleteActivity)ctxt).getMenuInflater();
                    inflater.inflate(R.menu.menu_delete_items, menu);
                    mode.setTitle("Modo borrar");
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_delete_item:

                            AlertDialog.Builder builder = new AlertDialog.Builder(ctxt);
                            int count = 0;
                            for (Film model : ((ListFilmDeleteActivity)ctxt).getMyDataset()) {
                                if (model.isSelected()) {
                                    count ++;
                                }
                            }
                            builder.setMessage("Esta a punto de borrar "+ String.valueOf(count)+ " elementos, ¿seguro que desea borrarlos?")
                                    .setTitle("Atención");

                            builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    for (Film model : ((ListFilmDeleteActivity)ctxt).getMyDataset()) {
                                        if (model.isSelected()) {
                                            ((ListFilmDeleteActivity)ctxt).getMyFilmData().deleteFilm(model);
                                        }
                                    }
                                    mode.finish();
                                }
                            });
                            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                            return true;
                        default:
                            return true;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    ListFilmDeleteActivity.setDeleteMode();
                    ((ListFilmDeleteActivity)ctxt).onResume();
                }
            });
            setSelected(v);



    }
    private void setSelected(View v){
        mFilm.setSelected(!mFilm.isSelected());
        v.setBackgroundColor(mFilm.isSelected() ? Color.CYAN : Color.WHITE);
    }
}