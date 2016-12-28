package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
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

import static android.R.attr.clickable;
import static android.R.attr.id;
import static android.media.CamcorderProfile.get;

/**
 * Created by Haloman on 20/11/2016.
 */

public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.ViewHolder> {
    private List<Film> mDataset;
    private Context ctxt;
    private View v;
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;
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
    public ListFilmAdapter(List<Film> myDataset) {
        mDataset = myDataset;
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

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }

    private boolean isSelectable() {
        return mIsSelectable;
    }
}
