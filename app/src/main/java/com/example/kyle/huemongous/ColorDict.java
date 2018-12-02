package com.example.kyle.huemongous;

import android.graphics.Color;

public class ColorDict {
    static class ColorName
    {
        int color;
        String name;
        ColorName(int i, String s)
        {
            this.color = Color.rgb(((i>>16)&0xFF),((i>>8)&0xFF), (i&0xFF));
            this.name = s;
        }
    }

    static ColorName[] dict = {
            new ColorName(0x4166f5, "Ultramarine Blue"),
            new ColorName(0xe30022, "Cadmium Red"),
            new ColorName(0xfff600, "Cadmium Yellow"),
            new ColorName(0xE9D66B, "Hansa Yellow Light"),
            new ColorName(0xfff7ff, "Titanium White"),
            new ColorName(0x28272a, "Ivory Black")
    };
}
