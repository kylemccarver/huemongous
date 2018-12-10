package com.example.kyle.huemongous;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MixingFragment extends Fragment {

    RecyclerView paletteColors;
    RecyclerView.Adapter adapter;
    Palette palette;

    static MixingFragment newInstance(Palette palette)
    {
        MixingFragment fragment = new MixingFragment();
        Bundle b = new Bundle();
        b.putSerializable("palette", palette);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        paletteColors = getActivity().findViewById(R.id.horizontalColorList);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            palette = (Palette) bundle.getSerializable("palette");
            adapter = new MixingColorsAdapter(v.getContext(), (ArrayList<ColorDict.ColorName>)palette.getColors());
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
            paletteColors.setLayoutManager(layoutManager);
            paletteColors.setAdapter(adapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.color_picker, container, false);
    }
}
