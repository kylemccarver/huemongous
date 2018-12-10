package com.example.kyle.huemongous;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MixingUtility {

    public static ColorDict.ColorName blend(float alpha, ColorDict.ColorName color1, ColorDict.ColorName color2)
    {
        int r = (int)((1-alpha) * color1.r + alpha * color2.r);
        int g = (int)((1-alpha) * color1.g + alpha * color2.g);
        int b = (int)((1-alpha) * color1.b + alpha * color2.b);
        Log.d("MIX", "r: " + r + " g:" + g + " b: " + b);

        return new ColorDict.ColorName(r, g, b, "mix");
    }

    public static ColorDict.ColorName blendN(HashMap<ColorDict.ColorName, Integer> colors)
    {
        Log.d("MIXUTIL", colors.toString());
        ArrayList<ColorDict.ColorName> nameList = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();
        for (Map.Entry<ColorDict.ColorName, Integer> entry : colors.entrySet())
        {
            nameList.add(entry.getKey());
            countList.add(entry.getValue());
        }

        int numColors = 0;
        int total = 0;
        for(Integer count : countList)
        {
            if(count != 0)
                ++numColors;
            total += count;
        }

        ColorDict.ColorName mixed = nameList.get(0);

        for(int i = 1; i < nameList.size(); ++i)
        {
            if(countList.get(i) > 0)
            {
                float ratio = (float) countList.get(i) / total;
                Log.d("RATIO count", Integer.toString(countList.get(i)));
                Log.d("RATIO total", Integer.toString(total));
                Log.d("RATIO", Float.toString(ratio));
                if (mixed != null && nameList.get(i) != null && countList.get(i) != 0)
                    mixed = blend(ratio, mixed, nameList.get(i));
            }
        }

        return mixed;
    }
}
