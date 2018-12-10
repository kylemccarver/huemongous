package com.example.kyle.huemongous;

import android.graphics.Color;

import java.io.Serializable;

public class ColorDict {
    static class ColorName implements Serializable
    {
        int color;
        int r;
        int g;
        int b;
        String name;

        ColorName() {}

        ColorName(int i, String s)
        {
            r = (i>>16)&0xFF;
            g = (i>>8)&0xFF;
            b = (i&0xFF);
            this.color = Color.rgb(r,g, b);
            this.name = s;
        }

        ColorName(int r, int g, int b, String s)
        {
            this.color = Color.rgb(r, g, b);
        }
    }

    static ColorName[] dict = {
            new ColorName(0x4166f5, "Ultramarine Blue"),
            new ColorName(0xe30022, "Cadmium Red"),
            new ColorName(0xfff600, "Cadmium Yellow"),
            new ColorName(0xE9D66B, "Hansa Yellow Light"),
            new ColorName(0xfff7ff, "Titanium White"),
            new ColorName(0x28272a, "Ivory Black"),
            new ColorName(0x463836, "Burnt Umber")
    };
}
