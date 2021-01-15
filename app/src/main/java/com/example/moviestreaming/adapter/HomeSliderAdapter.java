package com.example.moviestreaming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.HomeSlider;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class HomeSliderAdapter extends PagerAdapter {

    Context context;
    List<HomeSlider> sliders;
    TextView name_txt , publish_txt, time_txt;
    ImageView image_home_slider;


    public HomeSliderAdapter(Context context , List<HomeSlider> sliders){
        this.sliders = sliders;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_slider_item , container , false);

        name_txt = view.findViewById(R.id.homeSliderNameTV);
        publish_txt = view.findViewById(R.id.homeSliderPublishTV);
        time_txt = view.findViewById(R.id.homeSliderTimeTV);

        image_home_slider = view.findViewById(R.id.HomeSliderIV);

        name_txt.setText(sliders.get(position).getName());
        publish_txt.setText(sliders.get(position).getPublish());
        time_txt.setText(sliders.get(position).getTime());

        Picasso.get().load(sliders.get(position).getImage_link()).into(image_home_slider);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return sliders.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
