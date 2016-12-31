package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;


/**
 * Created by Haloman on 20/11/2016.
 */

public class ListFilmAdapter extends RecyclerView.Adapter<FilmHolder> {
    private List<Film> mDataset;
    private Context ctxt;
    private View v;
    private MultiSelector multiSelector;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public ListFilmAdapter(List<Film> myDataset, Context ctxt) {
        mDataset = myDataset;
        this.ctxt = ctxt;
        multiSelector = new MultiSelector();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FilmHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
            // set the view's size, margins, paddings and layout parameters
            FilmHolder vh = new FilmHolder(v,multiSelector,ctxt);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Film film = mDataset.get(position);
        holder.bindFilm(film);
      }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
