package com.example.telemarketingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Interface.ItemClickListener;
import com.example.telemarketingapp.R;

public class CustomerEMHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView Cname,Cphone,Cemail,Caddress,Cnotes,Csource;
    public Button callC,deleteC;
    ItemClickListener listener;
    public CustomerEMHolder(@NonNull View itemView) {
        super(itemView);
        Cname=(TextView)itemView.findViewById(R.id.nameC);
        Cphone=(TextView)itemView.findViewById(R.id.phoneC);
        Cemail=(TextView)itemView.findViewById(R.id.emailC);
        Caddress=(TextView)itemView.findViewById(R.id.addressC);
        Cnotes=(TextView)itemView.findViewById(R.id.notesC);
        Csource=(TextView)itemView.findViewById(R.id.sourceC);
        callC=(Button)itemView.findViewById(R.id.callC);
        deleteC=(Button)itemView.findViewById(R.id.deleteC);


    }
    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener =listener;
    }
    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
