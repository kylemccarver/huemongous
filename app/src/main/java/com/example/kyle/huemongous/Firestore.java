package com.example.kyle.huemongous;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class Firestore {

    protected FirebaseFirestore db;
    protected Auth auth;

    private static class Holder {
        public static Firestore helper = new Firestore();
    }
    // Every time you need the Net object, you must get it via this helper function
    public static Firestore getInstance() {
        return Holder.helper;
    }
    // Call init before using instance
    public static synchronized void init(Auth auth) {
        Holder.helper.db = FirebaseFirestore.getInstance();
        if( Holder.helper.db == null ) {
            Log.d(MainActivity.TAG, "XXX FirebaseFirestore is null!");
        }
        Holder.helper.auth = auth;
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                //.setTimestampsInSnapshotsEnabled(true)
                .build();
        Holder.helper.db.setFirestoreSettings(settings);
    }

    void savePalette(Palette palette) {
        Log.d(MainActivity.TAG,
                String.format("savePalette uid: %s %s",
                        palette.getUidOwner(),
                        palette.getName()));
        // XXX Write me
        getInstance().db.collection("users").document(palette.getUidOwner()).collection("palettes").document(palette.getPaletteId()).set(palette);
    }

    void saveColor(ColorFirestore color) {
        Log.d(MainActivity.TAG,
                String.format("savePalette uid: %s %s",
                        color.getUidOwner(),
                        color.getPaletteId()));
        // XXX Write me
        getInstance().db.collection("users").document(color.getUidOwner()).collection("recipes").document(color.getColorId()).set(color);
    }

    void deletePalette(Palette palette) {
        Log.d(MainActivity.TAG,
                String.format("deletePalette uid: %s %s",
                        palette.getUidOwner(),
                        palette.getName()));

        getInstance().db.collection("users").document(palette.getUidOwner()).collection("palettes").document(palette.getPaletteId()).delete();
    }

    void deleteColor(ColorFirestore color) {
        Log.d(MainActivity.TAG,
                String.format("savePalette uid: %s %s",
                        color.getUidOwner(),
                        color.getPaletteId()));
        // XXX Write me
        getInstance().db.collection("users").document(color.getUidOwner()).collection("recipes").document(color.getColorId()).delete();
    }

    Query getPaletteQuery() {
        Query query = null;
        // XXX Write me and the query shouldn't stay null
        CollectionReference colRef = db.collection("users").document(getInstance().auth.getUid()).collection("palettes");
        query = colRef.whereEqualTo("uidOwner", getInstance().auth.getUid());
        return query;
    }

    Query getColorQuery() {
        Query query = null;
        // XXX Write me and the query shouldn't stay null
        CollectionReference colRef = db.collection("users").document(getInstance().auth.getUid()).collection("recipes");
        //query = colRef.whereEqualTo("uidOwner", getInstance().auth.getUid());
        return query;
    }
}

