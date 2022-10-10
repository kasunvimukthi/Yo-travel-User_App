package com.yo.test.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.IncludesAdapterData;
import com.yo.test.AdapterData.TCAdapterData;
import com.yo.test.R;

import java.util.ArrayList;

public class TCAdapter extends RecyclerView.Adapter<TCAdapter.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<TCAdapterData> model;
    Context context;

    private View.OnClickListener listener;

    public TCAdapter(Context context, ArrayList<TCAdapterData> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_tc_adapter, parent, false);
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
            data = itemView.findViewById(R.id.tc);
        }
    }
}