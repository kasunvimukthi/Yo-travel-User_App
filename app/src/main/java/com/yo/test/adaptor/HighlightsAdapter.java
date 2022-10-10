package com.yo.test.adaptor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yo.test.AdapterData.DA1adapterData2;
import com.yo.test.AdapterData.HighlightsAdapterData;
import com.yo.test.R;

import java.util.ArrayList;

public class HighlightsAdapter extends RecyclerView.Adapter<HighlightsAdapter.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<HighlightsAdapterData> model;
    Context context;

    private View.OnClickListener listener;

    public HighlightsAdapter(Context context, ArrayList<HighlightsAdapterData> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_highlights_adapter, parent, false);
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
        String data = model.get(position).getData();

        holder.data.setText(data);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.higlights);
        }
    }
}