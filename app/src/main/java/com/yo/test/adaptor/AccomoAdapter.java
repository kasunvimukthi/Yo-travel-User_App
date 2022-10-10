package com.yo.test.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.Accommodation.Accommodation;
import com.yo.test.AdapterData.AccomoAdapterData;
import com.yo.test.R;

import java.util.ArrayList;

public class AccomoAdapter extends RecyclerView.Adapter<AccomoAdapter.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<AccomoAdapterData> model;
    Context context;

    private View.OnClickListener listener;

    public AccomoAdapter(Context context, ArrayList<AccomoAdapterData> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_accomo_adapter, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }
    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = model.get(position).getName();
        String loc = model.get(position).getLocation();
        String summ = model.get(position).getSummary();

        Bitmap image = model.get(position).getImg();

        holder.name.setText(name);
        holder.loc.setText(loc);
        holder.summ.setText(summ);

        holder.image.setImageBitmap(image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Accommodation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, loc, summ;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView11);
            loc = itemView.findViewById(R.id.textView22);
            summ = itemView.findViewById(R.id.textView23);

            image = itemView.findViewById(R.id.imageView4);
        }
    }
}
