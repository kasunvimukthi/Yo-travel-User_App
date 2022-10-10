package com.yo.test.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.CategoryAdapterData;
import com.yo.test.AdapterData.DA1adapterData1;
import com.yo.test.Details.CategoryDetails;
import com.yo.test.Details.upComingItemDetails;
import com.yo.test.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<CategoryAdapterData> model;
    Context context;

    private View.OnClickListener listener;

    public CategoryAdapter(Context context, ArrayList<CategoryAdapterData> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_details_view, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String p_id = model.get(position).getP_id();
        String date1 = model.get(position).getDate1();
        String date2 = model.get(position).getDate2();
        String packageName = model.get(position).getPackageName();
        String packageLocation = model.get(position).getPackageLocation();
        String summery = model.get(position).getSummery();
        Bitmap image = model.get(position).getImgID();

//        holder.name.setText(name);
        holder.date1.setText(date1);
        holder.date2.setText(date2);
        holder.packageName.setText(packageName);
        holder.packageLocation.setText(packageLocation);
        holder.summery.setText(summery);
        holder.image.setImageBitmap(image);
        holder.button.setId(Integer.parseInt(p_id));

//        When user select item - new activity will open
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, p_id, Toast.LENGTH_SHORT).show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date1, date2, packageName, packageLocation, summery;
        ImageView image;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            name = itemView.findViewById(R.id.textView10);
            date1 = itemView.findViewById(R.id.textView20);
            date2 = itemView.findViewById(R.id.textView21);
            packageName = itemView.findViewById(R.id.textView10);
            packageLocation = itemView.findViewById(R.id.textView12);
            summery = itemView.findViewById(R.id.textView14);
            image = itemView.findViewById(R.id.imageView3);
            button = itemView.findViewById(R.id.button2);
        }
    }
}
