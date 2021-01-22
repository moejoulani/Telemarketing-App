package com.example.telemarketingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Interface.ItemClickListener;
import com.example.telemarketingapp.R;

public class ProspectEMHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
   public TextView nameP,phoneP,addressP,emailP,notesP,sourceP;
    public Button   callP,deleteP,toCustomerP;
    ItemClickListener listener;
    public ProspectEMHolder(@NonNull View itemView) {
        super(itemView);

        nameP=(TextView)itemView.findViewById(R.id.namePE);
        phoneP=(TextView)itemView.findViewById(R.id.phonePE);
        addressP=(TextView)itemView.findViewById(R.id.addressPE);
        emailP=(TextView)itemView.findViewById(R.id.emailPE);
        notesP=(TextView)itemView.findViewById(R.id.notesPE);
        sourceP=(TextView)itemView.findViewById(R.id.sourcePE);
        callP=(Button)itemView.findViewById(R.id.callLeadPros);
        deleteP=(Button)itemView.findViewById(R.id.deleteLeadPros);
        toCustomerP=(Button)itemView.findViewById(R.id.ToCustomerPros);









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
