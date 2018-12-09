package com.example.kyle.huemongous;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Palette implements Serializable {

    private String uidOwner;
    private String paletteId;
    private String name;
    private List<ColorDict.ColorName> colors;

    public Palette() {}

    public Palette(String uidOwner, String paletteId, String n, ColorDict.ColorName[] data)
    {
        this.uidOwner = uidOwner;
        this.paletteId = paletteId;
        name = n;
        colors = Arrays.asList(data);
    }

    public String getUidOwner()
    {
        return uidOwner;
    }

    public String getPaletteId()
    {
        return paletteId;
    }

    public String getName() {
        return name;
    }

    public List<ColorDict.ColorName> getColors() {
        return colors;
    }
}
