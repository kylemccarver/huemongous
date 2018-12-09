package com.example.kyle.huemongous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class PaletteAdapter extends FirestoreRecyclerAdapter<Palette, PaletteAdapter.ViewHolder>
{
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Elements of each cell listed here:
        TextView textView;

        public ViewHolder(View view)
        {
            super(view);

            // TODO: init elements of cell
            textView = view.findViewById(R.id.paletteTextView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Perform transition to color mixing activity
                    Palette thisPalette = getItem(getAdapterPosition());

                    Intent intent = new Intent(view.getContext(), MixingActivity.class);
                    intent.putExtra("palette", thisPalette);
                    ((Activity)view.getContext()).startActivityForResult(intent, 2);
                }
            });
        }
    }

    PaletteAdapter(@NonNull FirestoreRecyclerOptions<Palette> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Palette model) {
        holder.textView.setText(model.getName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.palettelist_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
}
