package com.example.kyle.huemongous;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class NewPalette extends AppCompatActivity {

    RecyclerView selectedColors;
    RecyclerView availableColors;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_palette);

        RecyclerView.LayoutManager selectedLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager availableLayoutManager = new LinearLayoutManager(this);

        selectedColors = (RecyclerView) findViewById(R.id.selectedColors);
        selectedColors.setLayoutManager(selectedLayoutManager);

        RecyclerView.Adapter selectedAdapter = new ColorsAdapter(this);
        selectedColors.setAdapter(selectedAdapter);

        availableColors = (RecyclerView) findViewById(R.id.availableColors);
        availableColors.setLayoutManager(availableLayoutManager);

        RecyclerView.Adapter availableAdapter = new ColorsAdapter(this, ColorDict.dict);
        availableColors.setAdapter(availableAdapter);


    }
}
