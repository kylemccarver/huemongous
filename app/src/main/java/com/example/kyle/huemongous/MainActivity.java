package com.example.kyle.huemongous;

import android.app.Activity;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FirebaseAuth.AuthStateListener {

    private FirebaseAuth mAuth;
    private Auth auth;
    private Firestore firestore;
    public static String TAG = "HUE";
    private RecyclerView paletteList;

    // Request codes
    private final int RC_SIGN_IN = 123;
    private final int NEW_PALETTE = 1;
    private final int MIXING = 2;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        auth = Auth.getInstance();
        auth.init(this, this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(auth);

        firestore = Firestore.getInstance();
        firestore.init(auth);

        // Palette List setup
        paletteList = (RecyclerView) findViewById(R.id.paletteList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        paletteList.setLayoutManager(layoutManager);

        //RecyclerView.Adapter adapter = new PaletteAdapter(this);
        //paletteList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewPalette.class);
                ((Activity)mContext).startActivityForResult(intent, NEW_PALETTE);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

        else if(requestCode == NEW_PALETTE)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, "returned from new palette", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == MIXING)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, "returned from mixing", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.signout:
                AuthUI.getInstance()
                        .signOut(this);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if ( auth.signedIn() ) {
            // User is signed in
            //updateDisplayUserProfile();
            Log.d(MainActivity.TAG, String.format("%s/%s/%s is signed in",
                    auth.getDisplayName(),
                    auth.getEmail(),
                    auth.getUid()));
            FirestoreRecyclerOptions<Palette> options = new FirestoreRecyclerOptions.Builder<Palette>()
                    .setQuery(firestore.getPaletteQuery(), Palette.class)
                    .setLifecycleOwner(this)
                    .build();
            paletteList.setAdapter(new PaletteAdapter(options));
        }
    }
}
