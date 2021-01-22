package com.example.telemarketingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Interface.ItemClickListener;
import com.example.telemarketingapp.R;

public class ManagerLeadHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Lname,Lphone,Laddress,Lemail,Lnote,Lsource,LEMname;
        public Button delete_btn;
ItemClickListener listner;
    public ManagerLeadHolder(@NonNull View itemView) {
        super(itemView);
            Lname=(TextView)itemView.findViewById(R.id.nameL);
            Lphone=(TextView)itemView.findViewById(R.id.phoneL);
            Laddress=(TextView)itemView.findViewById(R.id.addressL);
            Lemail=(TextView)itemView.findViewById(R.id.emailL);
            Lnote=(TextView)itemView.findViewById(R.id.notesL);
            Lsource=(TextView)itemView.findViewById(R.id.sourceL);
            LEMname=(TextView)itemView.findViewById(R.id.nameEM);
            delete_btn=(Button)itemView.findViewById(R.id.delete_btn);

    }
    public void setItemClickListener(ItemClickListener listener)
    {
        this.listner =listener;
    }


    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);
    }
}
