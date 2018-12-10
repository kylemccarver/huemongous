package com.example.kyle.huemongous;

import android.app.Activity;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class ViewRecipesActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private Firestore firestore;
    private Auth auth;
    private FirestoreRecyclerAdapter adapter;
    private SwipeDetector swipeDetector;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipes_activity);
        Toolbar toolbar = findViewById(R.id.viewRecipesToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = Auth.getInstance();

        firestore = Firestore.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recipesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Query query = firestore.db.collection("users").document(auth.getUid()).collection("recipes");

        FirestoreRecyclerOptions<ColorFirestore> options = new FirestoreRecyclerOptions.Builder<ColorFirestore>()
                .setQuery(query, ColorFirestore.class)
                .build();

        swipeDetector = new SwipeDetector();
        adapter = new RecipesFirestoreAdapter(options, swipeDetector);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(swipeDetector);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null)
            adapter.stopListening();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Elements of each cell listed here:
        TextView textView;

        public ViewHolder(View view)
        {
            super(view);

            // TODO: init elements of cell
            textView = view.findViewById(R.id.paletteTextView);

        }
    }
}
