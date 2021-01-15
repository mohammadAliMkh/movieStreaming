package com.example.moviestreaming.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GlobalMoreFragment;


public class GenreFragment extends Fragment {

    RecyclerView recyclerView;

    public GenreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        recyclerView = view.findViewById(R.id.moreFragmentRV);
        setGenreRecyclerView();

        return view;

    }

    private void setGenreRecyclerView() {
        GlobalMoreFragment globalMoreFragment = new GlobalMoreFragment(getContext() , recyclerView);
        globalMoreFragment.setGenreRecyclerView();
    }
}