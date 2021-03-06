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
    private ColorsAdapter other;
    private boolean isAvailableList;

    public ColorsAdapter(Context context)
    {
        mContext = context;
        mData = new ArrayList<>();
        other = null;
        isAvailableList = false;
    }

    public ColorsAdapter(Context context, ColorDict.ColorName[] data, boolean isAvailableList, RecyclerView.Adapter other)
    {
        mContext = context;
        mData = new ArrayList<>(data.length);
        this.isAvailableList = isAvailableList;
        for(ColorDict.ColorName s : data)
        {
            mData.add(s);
        }
        this.other = (ColorsAdapter) other;
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
                    int position = getAdapterPosition();
                    if(isAvailableList && other != null)
                    {
                        if(!other.hasColor(mData.get(position).color))
                        {
                            other.addColor(mData.get(position));
                        }
                    }
                    else
                    {
                        removeColor(mData.get(position));
                    }
                }
            });
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
        notifyDataSetChanged();
    }

    public ColorDict.ColorName getColor(int position)
    {
        return mData.get(position);
    }

    public ColorDict.ColorName[] getColorsArray()
    {
        ColorDict.ColorName[] colors = new ColorDict.ColorName[mData.size()];
        for(int i = 0; i < colors.length; ++i)
        {
            colors[i] = mData.get(i);
        }

        return colors;
    }

    public boolean hasColor(int color)
    {
        for(int i = 0; i < mData.size(); ++i)
        {
            if(mData.get(i).color == color)
            {
                return true;
            }
        }
        return false;
    }

    public void removeColor(ColorDict.ColorName color)
    {
        for(int i = 0; i < mData.size(); ++i)
        {
            if(mData.get(i).color == color.color)
            {
                mData.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public void addToOther(ColorDict.ColorName color)
    {
        if(other != null)
        {
            other.addColor(color);
        }
    }

    public void setOther(RecyclerView.Adapter o)
    {
        other = (ColorsAdapter) o;
    }
}
