package com.example.pr_idi.mydatabaseexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
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
 */
public class FilmHolder extends SwappingHolder
        implements View.OnClickListener, View.OnLongClickListener {
    private Film mFilm;
    private Context ctxt;
    private TextView mTituloTextView, mPaisTextView,mAnyTextView,mDirectorTextView;
    private TextView mProtagonistaTextView, mNotaCriticaTextView;
    ActionMode mActionMode;
    public FilmHolder( View itemView, MultiSelector multiSelector,Context ctxt) {
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
        mPaisTextView.setText(String.valueOf(ctxt.getText(R.string.pais)).concat(" "+mFilm.getCountry()));
        mAnyTextView.setText(String.valueOf(ctxt.getText(R.string.any)).concat(" "+String.valueOf(mFilm.getYear())));
        mDirectorTextView.setText(String.valueOf(ctxt.getText(R.string.director)).concat(" "+mFilm.getDirector()));
        mProtagonistaTextView.setText(String.valueOf(ctxt.getText(R.string.protagonista)).concat(" "+mFilm.getProtagonist()));
        mNotaCriticaTextView.setText(String.valueOf(ctxt.getText(R.string.nota_de_la_critica)).concat(" "+String.valueOf(film.getCritics_rate()).concat("/10")));
    }
    @Override
    public void onClick(View v) {
        if(ListFilmActivity.getDeleteMode()) {
            setSelected(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(!ListFilmActivity.getDeleteMode()) {
            ListFilmActivity.setDeleteMode();
            ((ListFilmActivity)ctxt).startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = ((ListFilmActivity)ctxt).getMenuInflater();
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
                            for (Film model : ((ListFilmActivity)ctxt).getMyDataset()) {
                                if (model.isSelected()) {
                                    count ++;
                                }
                            }
                            builder.setMessage("Esta a punto de borrar "+ String.valueOf(count)+ " elementos, ¿seguro que desea borrarlos?")
                                    .setTitle("Atención");

                            builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    for (Film model : ((ListFilmActivity)ctxt).getMyDataset()) {
                                        if (model.isSelected()) {
                                            ((ListFilmActivity)ctxt).getMyFilmData().deleteFilm(model);
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
                    ListFilmActivity.setDeleteMode();
                    ((ListFilmActivity)ctxt).onResume();
                }
            });
            setSelected(v);
        }
        return true;
    }
    private void setSelected(View v){
        mFilm.setSelected(!mFilm.isSelected());
        v.setBackgroundColor(mFilm.isSelected() ? Color.CYAN : Color.WHITE);
    }
}