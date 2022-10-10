package com.yo.test.adaptor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.DA1adapterData2;
import com.yo.test.Category;
import com.yo.test.Details.CategoryDetails;
import com.yo.test.Details.upComingItemDetails;
import com.yo.test.R;

import java.util.ArrayList;

public class DAactivity1Adaptor2 extends RecyclerView.Adapter<DAactivity1Adaptor2.ViewHolder> implements View.OnClickListener{


    LayoutInflater inflater;
    ArrayList<DA1adapterData2> model;
    Context context;



    private View.OnClickListener listener;


    public DAactivity1Adaptor2(Context context, ArrayList<DA1adapterData2> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_d_category_item_card, parent, false);
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
        Bitmap image = model.get(position).getImageID();
        String c_id = model.get(position).getC_ID();


        holder.name.setText(name);
        holder.image.setImageBitmap(image);

//        When Select recycale view itme and opern new activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,c_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Category.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("C_ID",c_id);
                intent.putExtra("C_Name",name);
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
            name = itemView.findViewById(R.id.category_txt);
            image = itemView.findViewById(R.id.category_img);
        }
    }
}
