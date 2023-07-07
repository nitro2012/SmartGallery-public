package com.google.mlkit.vision.smartGallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewAdapter> {
    private final Context context;
    private List<File> images;
    private List<String> images2;

    public GalleryAdapter(Context context, List<File> images) {
        this.context = context;
        this.images = images;
    }
    public GalleryAdapter(Context context, boolean isUri,List<String> images) {
        this.context = context;
        this.images2 = images;
    }

    @NonNull
    @NotNull
    @Override
    public GalleryViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.gal,parent,false);

        return new GalleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GalleryViewAdapter holder, int position) {
        if(images!=null)
        Glide.with(context).load(images.get(position)).into(holder.imageView);
        else if(images2!=null)
            Glide.with(context).load(images2.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {
            Intent intent=new Intent(context,FullPage.class);

          /*  Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(images.get(position)), "image/*");
            context.startActivity(intent);*/
            if(images!=null)
            intent.putExtra("image",images.get(position).toString());
            else if (images2!=null)
                intent.putExtra("image",images2.get(position));
            v.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {


        if(images!=null)
        return images.size();
        else if(images2!=null)
            return  images2.size();

        return 0;
    }

    public class GalleryViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        public GalleryViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
