package com.example.kyle.huemongous;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ColorPickerActivity extends AppCompatActivity {

    private RecyclerView paletteColors;
    private RecyclerView.Adapter adapter;
    private Palette palette;
    private Button cancel;
    private Button done;
    private Button selected;
    private Button mixed;
    private ColorDict.ColorName mixedColor;
    private ColorDict.ColorName currentColor;
    private ArrayList<ColorDict.ColorName> colors;
    private Map<ColorDict.ColorName, Integer> state;
    private SeekBar seekBar;
    private int seekBarMax;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker);
        Toolbar toolbar = findViewById(R.id.colorMixingActivityToolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        //TODO: on click listener, return with instructions
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipe = createRecipe();
                Intent returnIntent = new Intent(getApplicationContext(), MixingActivity.class);
                returnIntent.putExtra("recipe", recipe);
                returnIntent.putExtra("color", mixedColor);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        selected = (Button) findViewById(R.id.current_color_view);
        mixed = (Button) findViewById(R.id.mixed_color_view);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int max = seekBar.getMax();
                if(currentColor != null && fromUser)
                    state.put(currentColor, progress);
                if(currentColor != null && mixedColor != null)
                {
                    mixedColor = MixingUtility.blendN((HashMap)state);
                    //mixedColor = MixingUtility.blend(progress / (float)seekBarMax, mixedColor, currentColor);
                    mixed.setBackgroundColor(mixedColor.color);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarMax = seekBar.getMax();

        state = new HashMap<>();

        paletteColors = (RecyclerView) findViewById(R.id.horizontalColorList);

        if(b != null)
        {
            mixedColor = new ColorDict.ColorName(0xffffff, "mix");
            mixed.setBackgroundColor(mixedColor.color);
            currentColor = null;

            palette = (Palette) b.getSerializable("palette");
            colors = (ArrayList<ColorDict.ColorName>)palette.getColors();

            for(ColorDict.ColorName color : colors)
            {
                state.put(color, 0);
            }

            adapter = new MixingColorsAdapter(this, colors);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            paletteColors.setLayoutManager(layoutManager);
            paletteColors.setAdapter(adapter);
        }
    }

    public void updateSelectedColor(int position)
    {
        int color = colors.get(position).color;
        String name = colors.get(position).name;

        selected.setText(name);
        selected.setBackgroundColor(color);

        currentColor = colors.get(position);
        seekBar.setProgress(0);
        seekBar.setMax(10);
        seekBar.setProgress(state.get(colors.get(position)));
    }

    public String createRecipe()
    {
        String recipe = "";

        int min = 100;
        for (Map.Entry<ColorDict.ColorName, Integer> entry : state.entrySet())
        {
            int count = entry.getValue();
            if(count < min && count > 0)
                min = count;
        }

        for (Map.Entry<ColorDict.ColorName, Integer> entry : state.entrySet())
        {
            float part = entry.getValue() / min;

            if(part > 0.0f)
                recipe += part + " part(s) " + entry.getKey().name + "\n";
        }

        return recipe;
    }
}
