package com.example.kyle.huemongous;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MixingColorsAdapter extends RecyclerView.Adapter<MixingColorsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ColorDict.ColorName> mData;

    public MixingColorsAdapter(Context context)
    {
        mContext = context;
        mData = new ArrayList<>();
    }

    public MixingColorsAdapter(Context context, ArrayList<ColorDict.ColorName> data)
    {
        mContext = context;
        mData = new ArrayList<>(data.size());
        for(ColorDict.ColorName s : data)
        {
            mData.add(s);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Elements of each cell listed here:
        TextView textView;

        public ViewHolder(View view)
        {
            super(view);

            // TODO: init elements of cell
            textView = view.findViewById(R.id.colorCardText);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Maybe do this in bindviewholder
                    ((ColorPickerActivity)mContext).updateSelectedColor(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public MixingColorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.color_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MixingColorsAdapter.ViewHolder holder, int position) {
        float luminance = ColorsAdapter.getLuminance(mData.get(position).color);

        holder.textView.setText(mData.get(position).name);
        holder.textView.setBackgroundColor(mData.get(position).color);

        if(luminance < 0.3)
        {
            holder.textView.setTextColor(Color.WHITE);
        }
        else
        {
            holder.textView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
