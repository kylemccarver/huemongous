package com.example.kyle.huemongous;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MixingActivity extends AppCompatActivity {

    Palette palette;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixing);

        Toolbar toolbar = findViewById(R.id.mixingActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        palette = (Palette) intent.getSerializableExtra("palette");

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ColorDict.ColorName> list = (ArrayList<ColorDict.ColorName>)palette.getColors();

                for(ColorDict.ColorName color : list)
                {
                    Log.d("MIX", color.name);
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
}
