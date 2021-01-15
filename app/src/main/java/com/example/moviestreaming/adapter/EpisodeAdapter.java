package com.example.moviestreaming.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moviestreaming.R;
import com.example.moviestreaming.model.Episode;
import com.example.moviestreaming.view.BuyAccountActivity;
import com.example.moviestreaming.view.PlayActivity;
import com.example.moviestreaming.view.SeriesEpisodesActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyViewHolder> {

    Context context;
    List<Episode> episodes;
    ViewGroup myParent;


    public EpisodeAdapter(Context context , List<Episode> episodes){
        this.context = context;
        this.episodes = episodes;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.episode_item , parent , false);
        myParent = parent;
        return new EpisodeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.episode_txt_number.setText("EP " + episodes.get(position).getEpisode_number());
        holder.episode_txt_name.setText(episodes.get(position).getEpisode_name());
        Picasso.get().load(episodes.get(position).getLink_img()).into(holder.episode_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(context, PlayActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("video_link", episodes.get(position).getLink_video());
                    intent.putExtras(bundle);
                    context.startActivity(intent);


            }
        });

        holder.download_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(context).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if(multiplePermissionsReport.areAllPermissionsGranted()){

                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(episodes.get(position).getLink_video()));
                                    request.setDestinationInExternalFilesDir(context , Environment.getExternalStorageState() + "TopMovies" , "");
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    request.allowScanningByMediaScanner();

                                    DownloadManager manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                                    manager.enqueue(request);

                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }
                        }).check();


            }
        });
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView episode_img, download_ic;
        TextView episode_txt_number , episode_txt_name;

        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            episode_img = itemView.findViewById(R.id.episode_img);
            episode_txt_number = itemView.findViewById(R.id.episode_number);
            episode_txt_name = itemView.findViewById(R.id.episode_name);
            relativeLayout = itemView.findViewById(R.id.root_item_episode);
            download_ic = itemView.findViewById(R.id.episode_download);
        }
    }
}
