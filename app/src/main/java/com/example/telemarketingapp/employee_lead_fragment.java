package com.example.telemarketingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.example.telemarketingapp.ViewHolder.EmployeeLeadHolder;
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
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class employee_lead_fragment extends Fragment {
    Spinner sp2;
    String category;
    ProgressDialog loading,lol;
    RecyclerView VrecyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_lead_fragment,null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        loading=new ProgressDialog(getContext());
        lol=new ProgressDialog(getContext());
        sp2=(Spinner)view.findViewById(R.id.spi3);
        lol.show();
        layoutManager = new LinearLayoutManager(getContext());
        VrecyclerView = view.findViewById(R.id.recolead2);
        VrecyclerView.setHasFixedSize(true);
        VrecyclerView.setLayoutManager(layoutManager);
        final ArrayList<String> s=new ArrayList<>();
        s.add("-----");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference reference=db.collection("Category").document(EmployeePrevalent.currentOnlineEmployee.getCompany_email());
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
                    else {
                        Toast.makeText(getContext(),"___________________",Toast.LENGTH_SHORT).show();
                    }
                lol.dismiss();
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
    public void onStart2()
    {
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        final Query query=db.collection("Leads").whereEqualTo("leadCategory",category).whereEqualTo("company_email",EmployeePrevalent.currentOnlineEmployee.getCompany_email()).whereEqualTo("EmployeeSelected",EmployeePrevalent.currentOnlineEmployee.getUsername());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    loading.dismiss();
                    FirestoreRecyclerOptions<Leads> options=new FirestoreRecyclerOptions.Builder<Leads>().setQuery(query,Leads.class).build();
                     FirestoreRecyclerAdapter<Leads, EmployeeLeadHolder> adapter=new FirestoreRecyclerAdapter<Leads, EmployeeLeadHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull EmployeeLeadHolder holder, int position, @NonNull final Leads model) {
                                holder.nameLE.setText(model.getLeadName());
                                holder.phoneLE.setText(model.getLeadPhone());
                                holder.addressLE.setText(model.getLeadAddress());
                                holder.notesLE.setText(model.getLeadNotes());
                                holder.sourceLE.setText(model.getLeadSource());
                                holder.emailLE.setText(model.getLeadEmail());

                                //____________ ON CLICK LISTENER __________________________________

                            holder.deleteLE.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final ProgressDialog progressDialog=new ProgressDialog(getContext());
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    db.collection("Leads").document(model.getLeadPhone()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(),"Deleted Lead Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            //____________ ON CLICK LISTENER __________________________________


                            holder.callLE.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:"+Long.parseLong(model.getLeadPhone())));//change the number
                                    startActivity(callIntent);
                                }
                            });


                            //____________ ON CLICK LISTENER __________________________________

                          holder.toProsptoects.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final ProgressDialog loading=new ProgressDialog(getContext());
                                    loading.setCanceledOnTouchOutside(false);
                                    loading.show();

                                    final  FirebaseFirestore db=FirebaseFirestore.getInstance();
                                    final DocumentReference reference=db.collection("Prospects").document(model.getLeadPhone());
                                    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Map<String,Object> map=new HashMap<>();
                                                    map.put("prosName",model.getLeadName());
                                                    map.put("prosPhone",model.getLeadPhone());
                                                    map.put("prosAddress",model.getLeadAddress());
                                                    map.put("prosEmail",model.getLeadEmail());
                                                    map.put("prosSource",model.getLeadSource());
                                                    map.put("prosNotes",model.getLeadNotes());
                                                    map.put("Category",model.getLeadCategory());
                                                    map.put("EmployeeSelected",model.getEmployee_selected());
                                                    map.put("company_email",EmployeePrevalent.currentOnlineEmployee.getCompany_email());
                                                    db.collection("Prospects").document(model.getLeadPhone()).set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            loading.dismiss();
                                                            db.collection("Leads").document(model.getLeadPhone()).delete();

                                                            Toast.makeText(getActivity(),"Lead Moved To Prospect List Successfully ",Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                                }
                                        }
                                    });

                                }
                            });


                            //____________ ON CLICK LISTENER __________________________________

                           holder.toCustomer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final ProgressDialog loading=new ProgressDialog(getContext());
                                    loading.setCanceledOnTouchOutside(false);
                                    loading.show();

                                    final  FirebaseFirestore db=FirebaseFirestore.getInstance();
                                    final DocumentReference reference=db.collection("Customers").document(model.getLeadPhone());
                                    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                Map<String,Object> map=new HashMap<>();
                                                map.put("custName",model.getLeadName());
                                                map.put("custPhone",model.getLeadPhone());
                                                map.put("custAddress",model.getLeadAddress());
                                                map.put("custEmail",model.getLeadEmail());
                                                map.put("custSource",model.getLeadSource());
                                                map.put("custNotes",model.getLeadNotes());
                                                map.put("Category",model.getLeadCategory());
                                                map.put("company_email",EmployeePrevalent.currentOnlineEmployee.getCompany_email());
                                                db.collection("Customers").document(model.getLeadPhone()).set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        loading.dismiss();
                                                        db.collection("Leads").document(model.getLeadPhone()).delete();

                                                        Toast.makeText(getActivity(),"Lead Moved To Customers List Successfully ",Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                            }
                                        }
                                    });




                                }
                            });


                            //____________ ON CLICK LISTENER ______________END___________________________END




                        }
                        @NonNull
                        @Override
                        public EmployeeLeadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_lead_items,parent,false);
                            EmployeeLeadHolder vendorViewHolder =new EmployeeLeadHolder(view);
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
