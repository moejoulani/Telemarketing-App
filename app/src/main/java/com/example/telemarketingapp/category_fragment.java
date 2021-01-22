package com.example.telemarketingapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class category_fragment extends Fragment {
    EditText categoryName;
    Button addCategory;
    ProgressDialog loading;
    String saveCurrentDate,saveCurrentTime,productRandomKey;
    private Button HulkButton;
    private Spinner spong;
    private ProgressDialog loading2;
    private String Cat;
    private final static String TAG="THE MESSAGE IS ";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_fragment,null);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        categoryName =(EditText)view.findViewById(R.id.category_name);
        addCategory  =(Button)view.findViewById(R.id.add_category_btn);
        loading=new ProgressDialog(getContext());
        final String cat_name =categoryName.getText().toString();
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkin();
            }
        });

        Toast.makeText(getActivity(),ManagerPrevalent.currentOnlineManagers.getCompany_email(),Toast.LENGTH_SHORT).show();

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
                DeleteCatEmp();
            }
        });

    }
    public void checkin()
    {
        String cat_name=categoryName.getText().toString();
        if(TextUtils.isEmpty(cat_name))
        {
            Toast.makeText(getActivity(),"Please Enter The Category Name !!",Toast.LENGTH_SHORT).show();
        }
        else{
            loading.setTitle("Adding New Category ....");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            UploadCategory(cat_name);
        }
    }
    public void UploadCategory(final String cat_name)
    {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate =new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calendar.getTime());

        productRandomKey =saveCurrentDate + saveCurrentTime;

           final FirebaseFirestore db =FirebaseFirestore.getInstance();
           DocumentReference reference =db.collection("Category").document(ManagerPrevalent.currentOnlineManagers.getCompany_email());
           reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot snapshot =task.getResult();


                        Map<String,Object> cat=new HashMap<>();
                        cat.put(cat_name,cat_name);

                        db.collection("Category").document(ManagerPrevalent.currentOnlineManagers.getCompany_email()).set(cat,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        loading.dismiss();
                                        Toast.makeText(getActivity(),"Added Category Successfully",Toast.LENGTH_SHORT).show();

                                    }
                            }
                        });

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
                Toast.makeText(getActivity(),"Deleted Category Successfully",Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
    public void DeleteCatEmp()
    {

        FirebaseFirestore db =FirebaseFirestore.getInstance();
        db.collection("Employee").whereEqualTo("company_email",ManagerPrevalent.currentOnlineManagers.getCompany_email())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       // Log.d(TAG, document.getId() + " => " + document.get("username"));
                        deleteIO(document.get("username").toString());
                    }
                }

            }
        });









    }


public void deleteIO(String name)
{


    final FirebaseFirestore db= FirebaseFirestore.getInstance();
    DocumentReference reference= db.collection("EmpCat").document(name);
    Map<String,Object> ma =new HashMap<>();
    ma.put(Cat,FieldValue.delete());
    reference.update(ma).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {

        }
    });
}
}
