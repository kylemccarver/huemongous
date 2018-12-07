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

public class NewPalette extends AppCompatActivity {

    RecyclerView selectedColors;
    RecyclerView availableColors;
    EditText paletteNameEdit;

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

        RecyclerView.Adapter selectedAdapter = new ColorsAdapter(this);
        selectedColors.setAdapter(selectedAdapter);

        availableColors = (RecyclerView) findViewById(R.id.availableColors);
        availableColors.setLayoutManager(availableLayoutManager);

        RecyclerView.Adapter availableAdapter = new ColorsAdapter(this, ColorDict.dict, true, selectedAdapter);
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
                if(paletteNameEdit.getText().toString() == null || paletteNameEdit.getText().toString().equals(""))
                {
                    Toast.makeText(this, "Missing information!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Save
                    setResult(RESULT_OK);
                    finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
