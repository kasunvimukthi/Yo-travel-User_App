package com.yo.test.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.InvoiceAdapterData;
import com.yo.test.Invoice.InvoiceCancel1;
import com.yo.test.Invoice.InvoiceView;
import com.yo.test.Invoice.UploadRecipt;
import com.yo.test.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InvoiceActivity extends RecyclerView.Adapter<InvoiceActivity.ViewHolder> implements View.OnClickListener{


    LayoutInflater inflater;
    ArrayList<InvoiceAdapterData> model;
    Context context;

    private View.OnClickListener listener;


    public InvoiceActivity(Context context, ArrayList<InvoiceAdapterData> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_d_2_invoice_card, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = model.get(position).getName();
        String i_id = model.get(position).getId();
        String status = model.get(position).getStatus();
        String i_date = model.get(position).getI_date();
        String i_s_date = model.get(position).getI_S_date();

        boolean cancel = true;
        boolean upload = true;
        boolean date = true;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ToDay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Date date4 = null;
        try {
            date4 = simpleDateFormat.parse(i_s_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date4);
        cal.add(Calendar.DATE,- 7);
        String I_Date = simpleDateFormat.format(cal.getTime());

        try {
            Date date1 = simpleDateFormat.parse(ToDay);
            Date date2 = simpleDateFormat.parse(I_Date);

            long toDay = date1.getTime();
            long newI = date2.getTime();

            if (toDay <= newI){
                date = true;
            }else{
                date = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.name.setText(name);
        holder.status.setText(status);
        holder.id.setText(i_id);
        holder.date.setText(i_date);
        holder.s_date.setText(i_s_date);

        if (status.equals("Active")){
            cancel = true;
            upload = true;
        }else
        if (status.equals("Canceled")){
            cancel = false;
            upload = false;
        }else
        if (status.equals("Expired")) {
            cancel = false;
            upload = false;
        }else
        if (status.equals("Not Paid")) {
            cancel = true;
            upload = true;
        }else
        if (status.equals("Proccessing")) {
            cancel = true;
            upload = true;
        }else
        if (status.equals("Full Paid")) {
            cancel = true;
            upload = false;
        }else{
            cancel = false;
            upload = false;
        }

        if (cancel == true && date == true){
            holder.cancel.setColorFilter(ContextCompat.getColor(context,R.color.primary));
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InvoiceCancel1.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("I_ID",i_id);
                    context.startActivity(intent);
                }
            });

        }else{
            holder.cancel.setColorFilter(ContextCompat.getColor(context,R.color.gray100));
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You Can't Cancel", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (upload == false){
            holder.upload.setColorFilter(ContextCompat.getColor(context,R.color.gray100));
            holder.upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You Can't Upload", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            holder.upload.setColorFilter(ContextCompat.getColor(context,R.color.primary));
            holder.upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UploadRecipt.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("I_ID",i_id);
                    context.startActivity(intent);
                }
            });
        }

        holder.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InvoiceView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("I_ID",i_id);
                context.startActivity(intent);
            }
        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(context, i_id, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, InvoiceView.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("I_ID",i_id);
//                context.startActivity(intent);
//            }
//        });

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

        TextView name, status, id, date, s_date;
        ImageView upload, print, cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView25);
            status = itemView.findViewById(R.id.textView30);
            id = itemView.findViewById(R.id.textView79);
            date = itemView.findViewById(R.id.textView78);
            s_date = itemView.findViewById(R.id.textView80);
            upload = itemView.findViewById(R.id.SlipUpload);
            print = itemView.findViewById(R.id.InvoicePrint);
            cancel = itemView.findViewById(R.id.Close);
        }
    }
}
