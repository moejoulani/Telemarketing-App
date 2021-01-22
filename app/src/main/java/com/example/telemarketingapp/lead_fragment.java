package com.example.telemarketingapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Model.Leads;
import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.example.telemarketingapp.ViewHolder.ManagerLeadHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class lead_fragment extends Fragment {
    Spinner sp2;
    String category;
    ProgressDialog loading;
    RecyclerView VrecyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lead_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loading=new ProgressDialog(getContext());
        sp2=(Spinner)view.findViewById(R.id.spi2);

        layoutManager = new LinearLayoutManager(getContext());
        VrecyclerView = view.findViewById(R.id.recoLead);
        VrecyclerView.setHasFixedSize(true);
        VrecyclerView.setLayoutManager(layoutManager);
        final ArrayList<String> s=new ArrayList<>();
        s.add("-----");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference reference=db.collection("Category").document(ManagerPrevalent.currentOnlineManagers.getCompany_email());
       reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        for (Object item : task.getResult().getData().values()) {
                            String[] values = String.valueOf(item).replace("[", "").replace("]", "").split(",");

                            for (String value : values) {
                                s.add(value);
                            }

                        }
                    }
                    else{
                        Toast.makeText(getContext(),"You must Add Category ..",Toast.LENGTH_SHORT).show();
                    }

                }
            }//task is succ
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,s);
        sp2.setAdapter(adapter);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=sp2.getSelectedItem().toString();
                onStart2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







    }


    public void onStart2() {
        super.onStart();
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        final Query query=db.collection("Leads").whereEqualTo("leadCategory",category).whereEqualTo("company_email",ManagerPrevalent.currentOnlineManagers.getCompany_email());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    loading.dismiss();
                    FirestoreRecyclerOptions<Leads>options=new FirestoreRecyclerOptions.Builder<Leads>().setQuery(query,Leads.class).build();
                    FirestoreRecyclerAdapter<Leads, ManagerLeadHolder> adapter=new FirestoreRecyclerAdapter<Leads, ManagerLeadHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ManagerLeadHolder holder, int position, @NonNull final Leads model) {
                                    holder.Lname.setText(model.getLeadName());
                                    holder.Lphone.setText(model.getLeadPhone());
                                    holder.Laddress.setText(model.getLeadAddress());
                                    holder.Lemail.setText(model.getLeadEmail());
                                    holder.Lnote.setText(model.getLeadNotes());
                                    holder.LEMname.setText(model.getEmployee_selected());
                                    holder.Lsource.setText(model.getLeadSource());
                                    holder.delete_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                                db.collection("Leads").document(model.getLeadPhone()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getActivity(),"Deleted Lead Successfully",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        }
                                    });

                        }

                        @NonNull
                        @Override
                        public ManagerLeadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.leaditem,parent,false);
                            ManagerLeadHolder vendorViewHolder =new ManagerLeadHolder(view);
                            return vendorViewHolder;
                        }
                    };
                    VrecyclerView.setAdapter(adapter);
                    adapter.startListening();
                }





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }
}
