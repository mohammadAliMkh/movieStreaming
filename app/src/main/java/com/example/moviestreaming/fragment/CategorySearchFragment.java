package com.example.moviestreaming.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreaming.R;

public class CategorySearchFragment extends Fragment implements View.OnClickListener {

    CardView actionCV , dramaCV, crimeCV , warCV , animationCV , seriesCV;

    public CategorySearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_search, container, false);
        actionCV = view.findViewById(R.id.action_cardView);
        actionCV.setOnClickListener(this);
        dramaCV = view.findViewById(R.id.drama_cardView);
        dramaCV.setOnClickListener(this);
        crimeCV = view.findViewById(R.id.crime_cardView);
        crimeCV.setOnClickListener(this);
        warCV = view.findViewById(R.id.war_cardView);
        warCV.setOnClickListener(this);
        animationCV = view.findViewById(R.id.animation_cardView);
        animationCV.setOnClickListener(this);
        seriesCV = view.findViewById(R.id.series_cardView);
        seriesCV.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.action_cardView:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new FullSearchFragment("action"))
                        .commit();
                break;
            case R.id.drama_cardView:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new FullSearchFragment("drama"))
                        .commit();
                break;
            case R.id.crime_cardView:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new FullSearchFragment("crime"))
                        .commit();
                break;
            case R.id.war_cardView:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new FullSearchFragment("war"))
                        .commit();
                break;
            case R.id.animateToEnd:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new FullSearchFragment("animation"))
                        .commit();
                break;
            case R.id.series_cardView:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.searchFL , new FullSearchFragment("comedy"))
                        .commit();
                break;
        }

    }
}