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
import com.example.telemarketingapp.Model.Prospects;
import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.example.telemarketingapp.ViewHolder.EmployeeLeadHolder;
import com.example.telemarketingapp.ViewHolder.ProspectEMHolder;
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

public class employee_prospect_fragment extends Fragment {
    Spinner sp2;
   private String category2;
    ProgressDialog loading;
    RecyclerView VrecyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_prospect_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        loading=new ProgressDialog(getContext());
        sp2=(Spinner)view.findViewById(R.id.spi4);

        layoutManager = new LinearLayoutManager(getContext());
        VrecyclerView = view.findViewById(R.id.hyperx);
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

                }
            }//task is succ
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,s);
        sp2.setAdapter(adapter);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category2=sp2.getSelectedItem().toString();
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
        final Query query=db.collection("Prospects").whereEqualTo("Category",category2).whereEqualTo("company_email",EmployeePrevalent.currentOnlineEmployee.getCompany_email()).whereEqualTo("EmployeeSelected",EmployeePrevalent.currentOnlineEmployee.getUsername());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    loading.dismiss();
                    FirestoreRecyclerOptions<Prospects> options=new FirestoreRecyclerOptions.Builder<Prospects>().setQuery(query,Prospects.class).build();
                    FirestoreRecyclerAdapter<Prospects,ProspectEMHolder> adapter=new FirestoreRecyclerAdapter<Prospects, ProspectEMHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProspectEMHolder holder, int position, @NonNull final Prospects model) {
                            holder.nameP.setText(model.getProsName());
                            holder.phoneP.setText(model.getProsPhone());
                            holder.emailP.setText(model.getProsEmail());
                            holder.addressP.setText(model.getProsAddress());
                            holder.notesP.setText(model.getProsNotes());
                            holder.sourceP.setText(model.getProsSource());

                            //____________ ON CLICK LISTENER __________________________________
                            holder.callP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:"+Long.parseLong(model.getProsPhone())));//change the number
                                    startActivity(callIntent);
                                }
                            });
                            //____________ ON CLICK LISTENER __________________________________
                            holder.deleteP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final ProgressDialog progressDialog=new ProgressDialog(getContext());
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    db.collection("Prospects").document(model.getProsPhone()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(),"Deleted Lead Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });


                            //____________ ON CLICK LISTENER __________________________________

                            holder.toCustomerP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final ProgressDialog loading=new ProgressDialog(getContext());
                                    loading.setCanceledOnTouchOutside(false);
                                    loading.show();

                                    final  FirebaseFirestore db=FirebaseFirestore.getInstance();
                                    final DocumentReference reference=db.collection("Customers").document(model.getProsPhone());
                                    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                Map<String,Object> map=new HashMap<>();
                                                map.put("custName",model.getProsName());
                                                map.put("custPhone",model.getProsPhone());
                                                map.put("custAddress",model.getProsAddress());
                                                map.put("custEmail",model.getProsEmail());
                                                map.put("custSource",model.getProsSource());
                                                map.put("custNotes",model.getProsNotes());
                                                map.put("Category",model.getCategory());
                                                map.put("EmployeeSelected",model.getEmployeeSelected());
                                                map.put("company_email",EmployeePrevalent.currentOnlineEmployee.getCompany_email());
                                                db.collection("Customers").document(model.getProsPhone()).set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        loading.dismiss();
                                                        db.collection("Prospects").document(model.getProsPhone()).delete();

                                                        Toast.makeText(getActivity(),"Lead Moved To Customers List Successfully ",Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                            }
                                        }
                                    });



                                }
                            });

                            //____________ ON CLICK LISTENER __________________________________END
                        }

                        @NonNull
                        @Override
                        public ProspectEMHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.em_pros_item,parent,false);
                            ProspectEMHolder prospectEMHolder=new ProspectEMHolder(view);
                            return prospectEMHolder;
                        }
                    };
                    VrecyclerView.setAdapter(adapter);
                    adapter.startListening();


              }

            }
        });

    }

}
