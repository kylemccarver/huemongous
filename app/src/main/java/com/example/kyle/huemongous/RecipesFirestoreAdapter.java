package com.example.kyle.huemongous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecipesFirestoreAdapter extends FirestoreRecyclerAdapter<ColorFirestore, RecipesFirestoreAdapter.ViewHolder>
{
    private SwipeDetector swipeDetector;

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
                public void onClick(View v) {
                    if(swipeDetector.swipeDetected())
                    {
                        Firestore.getInstance().deleteColor(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }

    RecipesFirestoreAdapter(@NonNull FirestoreRecyclerOptions<ColorFirestore> options, SwipeDetector swipeDetector) {
        super(options);
        this.swipeDetector = swipeDetector;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ColorFirestore model) {
        holder.textView.setText(model.getRecipe());
        int color = model.getColor();
        holder.textView.setBackgroundColor(color);

        float luminance = ColorsAdapter.getLuminance(color);

        if(luminance < 0.3)
        {
            holder.textView.setTextColor(Color.WHITE);
        }
        else
        {
            holder.textView.setTextColor(Color.BLACK);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.palettelist_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
}
