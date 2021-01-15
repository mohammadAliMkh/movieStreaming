package com.example.moviestreaming.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GlobalHomeActivity;

public class FullSearchFragment extends Fragment {

    SearchView fullSV;
    RecyclerView searchRV;
    String key;

    public FullSearchFragment(String key) {
        // Required empty public constructor
        this.key = key;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_full_search, container, false);

        searchRV = view.findViewById(R.id.fullSearchRV);

        fullSV = view.findViewById(R.id.fullSV);
        fullSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                GlobalHomeActivity globalHomeActivity = new GlobalHomeActivity(getContext() , searchRV);

                if(key == ""){
                    globalHomeActivity.fullSearch(s);
                }else{
                    globalHomeActivity.genreSearch(s , key);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return  view;
    }
}