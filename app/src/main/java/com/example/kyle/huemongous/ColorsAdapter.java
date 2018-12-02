package com.example.kyle.huemongous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ColorDict.ColorName> mData;

    public ColorsAdapter(Context context)
    {
        mContext = context;
        mData = new ArrayList<>();
        mData.add(ColorDict.dict[0]);
    }

    public ColorsAdapter(Context context, ColorDict.ColorName[] data)
    {
        mContext = context;
        mData = new ArrayList<>(data.length);
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
            textView = view.findViewById(R.id.paletteTextView);
        }
    }

    @NonNull
    @Override
    public ColorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO: make layout for cell
        View v = LayoutInflater.from(mContext).inflate(R.layout.palettelist_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static float getLuminance(int color) {
        int red   = Color.red(color);
        int green = Color.green(color);
        int blue  = Color.blue(color);

        float hsl[] = new float[3];
        ColorUtils.RGBToHSL(red, green, blue, hsl);
        return hsl[2];
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        float luminance = getLuminance(mData.get(position).color);
        //TODO: set data
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

    public void addColor(ColorDict.ColorName color)
    {
        mData.add(color);
    }
}
