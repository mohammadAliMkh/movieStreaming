package com.example.moviestreaming.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GlobalMoreFragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AnimationFragment extends Fragment {

    RecyclerView recyclerView;
    public AnimationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_more, container, false);
        recyclerView = view.findViewById(R.id.moreFragmentRV);
        setAnimationRecyclerview();
        return  view;
    }

    private void setAnimationRecyclerview() {
        GlobalMoreFragment globalMoreFragment = new GlobalMoreFragment(getContext() , recyclerView);
        globalMoreFragment.setAnimationRecyclerView();
    }
}