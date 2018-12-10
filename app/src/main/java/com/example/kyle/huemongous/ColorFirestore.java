package com.example.kyle.huemongous;

import java.io.Serializable;

public class ColorFirestore implements Serializable {

    private int color;
    private String recipe;
    private String uidOwner;
    private String colorId;
    private String paletteId;

    public ColorFirestore() {}

    public ColorFirestore(int color, String recipe, String uidOwner, String colorId, String paletteId)
    {
        this.color = color;
        this.recipe = recipe;
        this.uidOwner = uidOwner;
        this.colorId = colorId;
        this.paletteId = paletteId;
    }

    public int getColor() {
        return color;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getPaletteId() {
        return paletteId;
    }

    public String getColorId() {
        return colorId;
    }

    public String getUidOwner() {
        return uidOwner;
    }
}
