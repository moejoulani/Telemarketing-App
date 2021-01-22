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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class companycreateaccount extends AppCompatActivity {
        private   TextView employeeacc;
        private EditText manager_username,manager_comEmail,manager_password,manager_repassword;
        private Button createAccMan;
        private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companycreateaccount);
        employeeacc=(TextView)findViewById(R.id.goemployeeaccount);
        employeeacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(companycreateaccount.this,employeecreateacc.class);
                startActivity(intent);
            }
        });
        loading=new ProgressDialog(this);
        manager_username = (EditText)findViewById(R.id.manager_username);
        manager_comEmail = (EditText)findViewById(R.id.manager_compemail);
        manager_password = (EditText)findViewById(R.id.manager_password);
        manager_repassword=(EditText)findViewById(R.id.manager_repassword);
        createAccMan      =(Button)findViewById(R.id.manager_createbtn);


        createAccMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    createAccount();
            }
        });




    }
    public void createAccount()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String username =manager_username.getText().toString();
            String comp_email=manager_comEmail.getText().toString();
            String password=manager_password.getText().toString();
            String re_password=manager_repassword.getText().toString();

            if(TextUtils.isEmpty(username))
            {
                Toast.makeText(this,"Please Enter Your Email Address  !!",Toast.LENGTH_SHORT).show();
            }
            else if(!(username.matches(emailPattern)))
            {
                Toast.makeText(this,"Invalid Email Address !!!",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(comp_email))
            {
                Toast.makeText(this,"Please Enter The Company Email !!",Toast.LENGTH_SHORT).show();
            }
            else if(!(comp_email.matches(emailPattern)))
            {
                Toast.makeText(this,"Invalid Email Address !!!",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(password) && TextUtils.isEmpty(re_password))
            {
                Toast.makeText(this,"Please Enter The Your Password !!",Toast.LENGTH_SHORT).show();

            }
            else if(!password.equals(re_password))
            {
                Toast.makeText(this,"Password is not equivalent !!",Toast.LENGTH_SHORT).show();
            }
            else if(password.length()<8){

                Toast.makeText(this,"The Password Must At Least 8 Characters !!",Toast.LENGTH_SHORT).show();
            }
            else{
                loading.setTitle("Creating Account");
                loading.setMessage("Creating Manager Account Please Wait ....");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                ValidateUsername(username,comp_email,password);

            }

    }
    public void ValidateUsername(final String username, final String email, final String password)
    {
        final FirebaseFirestore db =FirebaseFirestore.getInstance();
        DocumentReference reference=db.collection("Managers").document(username);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    DocumentSnapshot snap =task.getResult();
                    if(snap.exists())
                    {
                                loading.dismiss();
                                Toast.makeText(companycreateaccount.this,"This Email Is Already Exists !! ",Toast.LENGTH_SHORT).show();


                    }
                    else{
                        Map<String,Object> map =new HashMap<>();
                        map.put("username",username);
                        map.put("company_email",email);
                        map.put("password",password);
                        db.collection("Managers").document(username).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(companycreateaccount.this,"Created Manager Account Successfully",Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                    Intent intent=new Intent(companycreateaccount.this,signin.class);
                                    startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading.dismiss();
                                Toast.makeText(companycreateaccount.this,"NetWork Error ...Please Try Again Later .",Toast.LENGTH_SHORT).show();

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
