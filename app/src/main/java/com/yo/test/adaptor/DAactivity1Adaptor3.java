package com.yo.test.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.DA1adapterData3;
import com.yo.test.Details.upComingItemDetails;
import com.yo.test.MainActivity;
import com.yo.test.R;
import com.yo.test.Slider;

import java.util.ArrayList;

public class DAactivity1Adaptor3 extends RecyclerView.Adapter<DAactivity1Adaptor3.ViewHolder> implements View.OnClickListener{


    LayoutInflater inflater;
    ArrayList<DA1adapterData3> model;
    Context context;

    private View.OnClickListener listener;


    public DAactivity1Adaptor3(Context context, ArrayList<DA1adapterData3> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_d_upcoming_item_card, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String packagename = model.get(position).getPackagename();
        String id = model.get(position).getId();
        String location = model.get(position).getLocation();
        String status = model.get(position).getStatus();
        String sdate = model.get(position).getSdate();
        String edate = model.get(position).getEdate();
        Bitmap image = model.get(position).getImage();

        holder.packagename.setText(packagename);
        holder.location.setText(location);
        holder.status.setText(status);
        holder.sdate.setText(sdate);
        holder.edate.setText(edate);

        holder.image.setImageBitmap(image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,packagename, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, upComingItemDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("P_ID",id);
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

        TextView packagename, location, status, sdate, edate, count;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            packagename = itemView.findViewById(R.id.packageName);
            location = itemView.findViewById(R.id.packageLocation);
            status = itemView.findViewById(R.id.packageStatus);
            sdate = itemView.findViewById(R.id.packageStart);
            edate = itemView.findViewById(R.id.packageEnd);
            image = itemView.findViewById(R.id.package_img);
        }
    }
}
