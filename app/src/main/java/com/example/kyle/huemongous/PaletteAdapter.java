package com.example.kyle.huemongous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PaletteAdapter extends RecyclerView.Adapter<PaletteAdapter.ViewHolder>
{
    private Context mContext;
    private ArrayList<String> mData;

    public PaletteAdapter(Context context)
    {
        mContext = context;
        mData = new ArrayList<>();
        mData.add("Hi there");
        mData.add("anus");
    }

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
                    Intent intent = new Intent(mContext, MixingActivity.class);
                    ((Activity)mContext).startActivityForResult(intent, 2);
                }
            });
        }
    }

    @NonNull
    @Override
    public PaletteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO: make layout for cell
        View v = LayoutInflater.from(mContext).inflate(R.layout.palettelist_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //TODO: set data
        holder.textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
