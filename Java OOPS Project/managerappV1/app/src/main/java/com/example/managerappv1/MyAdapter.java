package com.example.managerappv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<OrderItem> orders;

    public MyAdapter(List<OrderItem> orders){
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        String tablenumber = orders.get(position).getTitle();
        String name = orders.get(position).getName();
        String phno = orders.get(position).getPhno();
        String desc = orders.get(position).getDesc();
        String datetime = orders.get(position).getDatetime();
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),desc,Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle(tablenumber + " : " + name + ", " + phno);
                builder.setMessage("Order Palaced: " + datetime + "\n" + desc.replace(" ","\n").replace("_","     *  ").replace("+"," ")).setCancelable(true);
                builder.setPositiveButton("Prepared Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"Order Done", Toast.LENGTH_LONG).show();
                        orders.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });
                builder.setNegativeButton("Cancel Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"Order Cancelled", Toast.LENGTH_LONG).show();
                        orders.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
        holder.setData(tablenumber, name, phno);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titletext;
        private TextView nametext;
        private TextView phnotext;
        private RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titletext = itemView.findViewById(R.id.titleText);
            nametext = itemView.findViewById(R.id.nameText);
            phnotext = itemView.findViewById(R.id.phnoText);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }

        public void setData(String tablenumber, String name, String phno) {
            titletext.setText(tablenumber);
            nametext.setText(name);
            phnotext.setText(phno);
        }
    }
}
