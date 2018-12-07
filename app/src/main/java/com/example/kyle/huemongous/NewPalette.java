package com.example.kyle.huemongous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class NewPalette extends AppCompatActivity {

    private RecyclerView selectedColors;
    private RecyclerView availableColors;
    private EditText paletteNameEdit;
    private RecyclerView.Adapter selectedAdapter;
    private RecyclerView.Adapter availableAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_palette);

        Toolbar toolbar = (Toolbar) findViewById(R.id.newPaletteToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager selectedLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager availableLayoutManager = new LinearLayoutManager(this);

        selectedColors = (RecyclerView) findViewById(R.id.selectedColors);
        selectedColors.setLayoutManager(selectedLayoutManager);

        selectedAdapter = new ColorsAdapter(this);
        selectedColors.setAdapter(selectedAdapter);

        availableColors = (RecyclerView) findViewById(R.id.availableColors);
        availableColors.setLayoutManager(availableLayoutManager);

        availableAdapter = new ColorsAdapter(this, ColorDict.dict, true, selectedAdapter);
        availableColors.setAdapter(availableAdapter);

        paletteNameEdit = (EditText) findViewById(R.id.paletteNameEdit);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_palette_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.palette_save:
                // Save palette
                if(paletteNameEdit.getText().toString() == null || paletteNameEdit.getText().toString().equals("") || selectedAdapter.getItemCount() == 0)
                {
                    Toast.makeText(this, "Missing information!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Save
                    String paletteId = UUID.randomUUID().toString();
                    ColorDict.ColorName[] colors = ((ColorsAdapter) selectedAdapter).getColorsArray();
                    Palette palette = new Palette(Auth.getInstance().getUid(), paletteId, paletteNameEdit.getText().toString(), colors);
                    Firestore.getInstance().savePalette(palette);
                    setResult(RESULT_OK);
                    finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
