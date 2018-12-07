package com.example.kyle.huemongous;

import android.os.Parcel;
import android.os.Parcelable;

public class Palette{

    private String uidOwner;
    private String paletteId;
    public String name;
    private ColorDict.ColorName[] colors;

    public Palette(String uidOwner, String paletteId, String n, ColorDict.ColorName[] data)
    {
        this.uidOwner = uidOwner;
        this.paletteId = paletteId;
        name = n;
        colors = new ColorDict.ColorName[data.length];
        for(int i = 0; i < colors.length; ++i)
        {
            colors[i] = data[i];
        }
    }

    public String getUidOwner()
    {
        return uidOwner;
    }

    public String getPaletteId()
    {
        return paletteId;
    }
}
