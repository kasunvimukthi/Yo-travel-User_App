package com.yo.test.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.Accommodation.Accommodation;
import com.yo.test.R;

import java.util.ArrayList;

public class ImageAdapter1 extends RecyclerView.Adapter<ImageAdapter1.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<ImageAdapterData1> model;
    Context context;

    private View.OnClickListener listener;

    public ImageAdapter1(Context context, ArrayList<ImageAdapterData1> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }


    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activties_image_adaptor, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bitmap image = model.get(position).getImg();


        holder.image.setImageBitmap(image);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageViewImage);
        }
    }
}
