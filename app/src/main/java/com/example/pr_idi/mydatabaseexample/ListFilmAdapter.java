package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.io.Console;
import java.util.List;


/**
 * Created by Haloman on 20/11/2016.
 */

public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.ViewHolder> {
    private List<Film> mDataset;
    private Context ctxt;
    private View v;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListFilmAdapter(List<Film> myDataset, Context ctxt) {
        mDataset = myDataset;
        this.ctxt = ctxt;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListFilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
         // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Film film = mDataset.get(position);

        TextView v = (TextView)holder.mView.findViewById(R.id.textTitulo);
        v.setText(film.getTitle());

        v = (TextView)holder.mView.findViewById(R.id.textPais);
        v.setText(film.getCountry());

        v = (TextView)holder.mView.findViewById(R.id.textAny);
        v.setText(String.valueOf(film.getYear()));

        v = (TextView)holder.mView.findViewById(R.id.textDirector);
        v.setText(film.getDirector());

        v = (TextView)holder.mView.findViewById(R.id.textProtagonista);
        v.setText(film.getProtagonist());

        v = (TextView)holder.mView.findViewById(R.id.textNotaCritica);
        v.setText(String.valueOf(film.getCritics_rate()).concat("/10"));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListFilmActivity.getDeleteMode()) {
                    film.setSelected(!film.isSelected());
                    v.setBackgroundColor(film.isSelected() ? Color.CYAN : Color.WHITE);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                ((ListFilmActivity)ctxt).invalidateOptionsMenu();
                Log.d("TAG",String.valueOf(ListFilmActivity.getDeleteMode()));
                ListFilmActivity.setDeleteMode();
                return false;
            }
        });

      }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
