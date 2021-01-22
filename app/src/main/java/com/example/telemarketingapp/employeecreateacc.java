package com.example.telemarketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class employeecreateacc extends AppCompatActivity {
        private EditText Em_username,Comp_email,Em_password,Em_repassword;
        private Button create_btn;
        private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeecreateacc);

        Em_username=(EditText)findViewById(R.id.Emusername);
        Comp_email=(EditText)findViewById(R.id.EmcompanyName);
        Em_password=(EditText)findViewById(R.id.Empassword);
        Em_repassword=(EditText)findViewById(R.id.Emprepassword);
        create_btn=(Button)findViewById(R.id.Emcreatebtn);

        loading =new ProgressDialog(this);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }
    public void createAccount()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String username =Em_username.getText().toString();
            String company_emial=Comp_email.getText().toString();
            String password =Em_password.getText().toString();
            String repassword=Em_repassword.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this,"Please Enter The Email !!",Toast.LENGTH_SHORT).show();
        }
        else if(!(username.matches(emailPattern)))
        {
            Toast.makeText(this,"Invalid Email Address !!!",Toast.LENGTH_SHORT).show();
        }
     /*
       else if(TextUtils.isEmpty(company_emial))
        {
            Toast.makeText(this,"Please Enter The Company Email !!",Toast.LENGTH_SHORT).show();
        }
        else if(!(company_emial.matches(emailPattern)))
        {
            Toast.makeText(this,"Invalid Email Address !!!",Toast.LENGTH_SHORT).show();
        }

     */
        else if(TextUtils.isEmpty(password) && TextUtils.isEmpty(repassword))
        {
            Toast.makeText(this,"Please Enter The Your Password !!",Toast.LENGTH_SHORT).show();

        }
        else if(!password.equals(repassword))
        {
            Toast.makeText(this,"Password is not equivalent !!",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8){

            Toast.makeText(this,"The Password Must At Least 8 Characters !!",Toast.LENGTH_SHORT).show();
        }
        else{
            loading.setTitle("Creating Account");
            loading.setMessage("Creating Employee Account Please Wait ....");
            loading.setCanceledOnTouchOutside(true);
            loading.show();
            ValidateUsername(username,company_emial,password);
        }

    }
   public void ValidateUsername(final String username, final String email, final String password)
    {
        final FirebaseFirestore db =FirebaseFirestore.getInstance();
        DocumentReference reference=db.collection("Employee").document(username);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot snap =task.getResult();
                if(snap.exists())
                {
                    loading.dismiss();
                    Toast.makeText(employeecreateacc.this,"The Email Is Already Exists ! ",Toast.LENGTH_SHORT).show();


                }
                else{
                    Map<String,Object> map =new HashMap<>();
                    map.put("username",username);
                    map.put("company_email", ManagerPrevalent.currentOnlineManagers.getCompany_email());
                    map.put("password",password);
                    db.collection("Employee").document(username).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(employeecreateacc.this,"Created Employee Account Successfully",Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            finish();
                            Intent intent=new Intent(employeecreateacc.this,signin.class);
                          //  startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loading.dismiss();
                            Toast.makeText(employeecreateacc.this,"NetWork Error ...Please Try Again Later .",Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }



    }
