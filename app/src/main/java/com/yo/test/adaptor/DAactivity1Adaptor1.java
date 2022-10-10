package com.yo.test.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.DA1adapterData1;
import com.yo.test.Details.upComingItemDetails;
import com.yo.test.R;

import java.sql.Blob;
import java.util.ArrayList;

public class DAactivity1Adaptor1 extends RecyclerView.Adapter<DAactivity1Adaptor1.ViewHolder> implements View.OnClickListener{


    LayoutInflater inflater;
    ArrayList<DA1adapterData1> model;
    Context context;

    private View.OnClickListener listener;


    public DAactivity1Adaptor1(Context context, ArrayList<DA1adapterData1> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_d_item_card, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = model.get(position).getName();
        String p_id = model.get(position).getId();
        Bitmap image = model.get(position).getImageID();

        holder.name.setText(name);

        holder.image.setImageBitmap(image);

//        When user select item - new activity will open
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, upComingItemDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("P_ID",p_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }
}
