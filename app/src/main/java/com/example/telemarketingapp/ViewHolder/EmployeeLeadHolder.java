package com.example.telemarketingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Interface.ItemClickListener;
import com.example.telemarketingapp.R;

public class EmployeeLeadHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView nameLE,phoneLE,addressLE,emailLE,sourceLE,notesLE;
        public Button callLE,toProsptoects,toCustomer,deleteLE;
        ItemClickListener listener;
    public EmployeeLeadHolder(@NonNull View itemView) {
        super(itemView);
        nameLE=(TextView)itemView.findViewById(R.id.nameLE);
        phoneLE=(TextView)itemView.findViewById(R.id.phoneLE);
        addressLE=(TextView)itemView.findViewById(R.id.addressLE);
        emailLE=(TextView)itemView.findViewById(R.id.emailLE);
        sourceLE=(TextView)itemView.findViewById(R.id.sourceLE);
        notesLE=(TextView)itemView.findViewById(R.id.notesLE);
        callLE=(Button)itemView.findViewById(R.id.callLead);
        toProsptoects=(Button)itemView.findViewById(R.id.ToProspect);
        toCustomer=(Button)itemView.findViewById(R.id.ToCustomer);
        deleteLE=(Button)itemView.findViewById(R.id.deleteLead);
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
