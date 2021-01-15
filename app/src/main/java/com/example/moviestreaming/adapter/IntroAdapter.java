package com.example.moviestreaming.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.Intro;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class IntroAdapter extends PagerAdapter {

    Context context;
    List<Intro> introList;
    ImageView imageView;
    TextView description;


    public IntroAdapter(Context context , List<Intro> introList){
        this.context = context;
        this.introList = introList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.intro_item , container , false);

        imageView = view.findViewById(R.id.intro_iv);
        description = view.findViewById(R.id.intro_txt_description);

        InputStream imageStream;

        if(position == 0){
             imageStream = context.getResources().openRawResource(R.raw.watch);
        }else if(position == 1){
             imageStream = context.getResources().openRawResource(R.raw.download);
        }else if(position == 2){
             imageStream = context.getResources().openRawResource(R.raw.opinion);
        }else {
            imageStream = context.getResources().openRawResource(R.raw.category);
        }

        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
        imageView.setImageBitmap(bitmap);
        description.setText(introList.get(position).getDescription());

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return introList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

}
