package com.example.kyle.huemongous;

public class Palette {

    private String uidOwner;
    public String name;
    private ColorDict.ColorName[] colors;

    public Palette(String uidOwner, String n, ColorDict.ColorName[] data)
    {
        this.uidOwner = uidOwner;
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
}
