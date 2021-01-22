package com.example.telemarketingapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class deleteCategory_fragment extends Fragment {
    private Button HulkButton;
    private Spinner spong;
    private ProgressDialog loading;
    private String Cat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.deletecategory_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HulkButton=(Button)view.findViewById(R.id.deletehulk);
        spong=(Spinner)view.findViewById(R.id.spiiiii);
        loading=new ProgressDialog(getContext());
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


                }
            }//task is succ
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,s);
        spong.setAdapter(adapter);

        //-----------------------------------------------------------------------------------------------------------
        spong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cat =spong.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        HulkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                loading.setCanceledOnTouchOutside(false);
                DeleteCAT();
            }
        });


    }
    public void DeleteCAT()
    {
        FirebaseFirestore db =FirebaseFirestore.getInstance();
        DocumentReference ref =db.collection("Category").document(ManagerPrevalent.currentOnlineManagers.getCompany_email());
        Map<String,Object> mm =new HashMap<>();
        mm.put(Cat, FieldValue.delete());
        ref.update(mm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(),"Deleted Category ",Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
