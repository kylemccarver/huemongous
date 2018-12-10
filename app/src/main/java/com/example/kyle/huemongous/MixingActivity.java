package com.example.kyle.huemongous;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MixingActivity extends AppCompatActivity {

    private Context mContext;
    private Palette palette;
    private TextView recipeText;
    private Button button;
    private Button save;
    private ColorDict.ColorName mixedColor;
    private String saveRecipe;

    private final int C_PICKER = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixing);
        Toolbar toolbar = findViewById(R.id.mixingActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mixedColor = null;
        saveRecipe = null;

        Intent intent = getIntent();

        palette = (Palette) intent.getSerializableExtra("palette");

        recipeText = (TextView) findViewById(R.id.recipe);

        // Color picker button
        button = (Button) findViewById(R.id.button);

        save = (Button) findViewById(R.id.saveColor);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mixedColor == null)
                {
                    Toast.makeText(mContext, "No mixed color to save", Toast.LENGTH_SHORT).show();
                }
                else if(saveRecipe != null)
                {
                    String colorId = UUID.randomUUID().toString();
                    ColorFirestore saveColor = new ColorFirestore(mixedColor.color, saveRecipe, Auth.getInstance().getUid(), colorId, palette.getPaletteId());
                    Firestore.getInstance().saveColor(saveColor);
                    Toast.makeText(mContext, "Color saved", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mixing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.delete_palette:
                // Delete palette
                if(palette != null)
                {
                    Firestore.getInstance().deletePalette(palette);
                    setResult(RESULT_CANCELED);
                    finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void colorPickerClicked(View view)
    {
        Intent intent = new Intent(this, ColorPickerActivity.class);
        intent.putExtra("palette", palette);
        startActivityForResult(intent, C_PICKER);
    }

    public void clear()
    {
        saveRecipe = null;
        mixedColor = null;
        recipeText.setText("");
        button.setBackgroundColor(Color.GRAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == C_PICKER)
        {
            if(resultCode == RESULT_OK)
            {
                Bundle b = data.getExtras();
                if(b != null)
                {
                    String recipe = b.getString("recipe");
                    saveRecipe = recipe;
                    ColorDict.ColorName color = (ColorDict.ColorName) b.get("color");
                    mixedColor = color;

                    recipeText.setText(recipe);
                    button.setBackgroundColor(color.color);
                }
            }
        }
    }
}
