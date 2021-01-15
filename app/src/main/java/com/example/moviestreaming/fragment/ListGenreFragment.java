package com.example.moviestreaming.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GlobalHomeActivity;


public class ListGenreFragment extends Fragment {

    String keyGenre;

    public ListGenreFragment(String keyGenre) {
        // Required empty public constructor
        this.keyGenre = keyGenre;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_genre, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.listGenre);
        GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getContext() , recyclerView);
        globalHomeActivity.setListGenreRecyclerview(keyGenre);
        return view;
    }

}