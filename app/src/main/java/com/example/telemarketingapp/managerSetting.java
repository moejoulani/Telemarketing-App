package com.example.telemarketingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class managerSetting extends AppCompatActivity {
    private EditText CHusername, CHpassword, CHrepassword;
    private Button update_btn;
    private TextView update_btn2, close,CHIMAGEUPDATE;
    private CircleImageView img;
    private Uri imageUri;
    private StorageTask uploadTask;
    private String myUrl = "";

    private StorageReference storageProfilePicture;
    private ProgressDialog loading;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_setting);
        CHusername = (EditText) findViewById(R.id.CHusername);
        CHpassword = (EditText) findViewById(R.id.CHpassword);
        CHrepassword = (EditText) findViewById(R.id.CHRePasswordiii);
        update_btn = (Button) findViewById(R.id.CHbutton);
        update_btn2 = (TextView) findViewById(R.id.CHbutton2);
        close = (TextView) findViewById(R.id.close_btn_update_manager);
        CHIMAGEUPDATE=(TextView)findViewById(R.id.CHimg);
        loading = new ProgressDialog(this);
        img = (CircleImageView) findViewById(R.id.setting_profile_image);
        userInfoDisplay(img, CHusername, CHpassword);
        storageProfilePicture= FirebaseStorage.getInstance().getReference().child("profile pictures");
      close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });



            CHIMAGEUPDATE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checker = "clicked";

                    CropImage.activity(imageUri)
                            .setAspectRatio(1, 1)
                            .start(managerSetting.this);

                }
            });
        }


    private void updateOnlyUserInfo() {
        //check for input must here ............
        String username = CHusername.getText().toString();
        String password = CHpassword.getText().toString();
        String re_pass = CHrepassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter The Username", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter The New Password", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (password.length() < 8) {
            Toast.makeText(this, "The Password Must At Least 8 Characters", Toast.LENGTH_SHORT).show();
            loading.dismiss();

        } else if (!(password.equals(re_pass))) {
            Toast.makeText(this, "The Passwords Is Not Equivlent ! ", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else {
            loading.setMessage("Please Wait");
            loading.setTitle("Updating Your Information");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference reference = db.collection("Managers").document(ManagerPrevalent.currentOnlineManagers.getUsername());
            Map<String, Object> usermap = new HashMap<>();
            usermap.put("username", CHusername.getText().toString());
            usermap.put("password", CHpassword.getText().toString());
            reference.update(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.dismiss();
                    Toast.makeText(managerSetting.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            img.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();
            loading.dismiss();
            startActivity(new Intent(managerSetting.this, managerSetting.class));
            finish();
        }
    }

    private void userInfoSaved() {

        String username = CHusername.getText().toString();
        String password = CHpassword.getText().toString();
        String re_pass = CHrepassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter The Username", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter The New Password", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (password.length() < 8) {
            Toast.makeText(this, "The Password Must At Least 8 Characters", Toast.LENGTH_SHORT).show();
            loading.dismiss();

        } else if (!(password.equals(re_pass))) {
            Toast.makeText(this, "The Passwords Is Not Equivlent ! ", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else if(checker.equals("clicked"))
        {
            UploadImage();

        }
    }

    private void UploadImage() {
        if (imageUri != null) {

            final StorageReference fileRef = storageProfilePicture.child(ManagerPrevalent.currentOnlineManagers.getUsername() + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            Toast.makeText(managerSetting.this,"imageUri != null",Toast.LENGTH_SHORT).show();
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then( Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();

                    }
                    Toast.makeText(managerSetting.this,"here we go",Toast.LENGTH_SHORT).show();
                    return fileRef.getDownloadUrl();

                    }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(managerSetting.this,"task is success",Toast.LENGTH_SHORT).show();
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference reference = db.collection("Managers").document(ManagerPrevalent.currentOnlineManagers.getUsername());
                        HashMap<String, Object> usermap = new HashMap<>();
                        usermap.put("username", CHusername.getText().toString());
                        usermap.put("password", CHpassword.getText().toString());
                        usermap.put("image",myUrl);
                        reference.update(usermap);
                        finish();

                    }
                }
            });
        }
    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText) {
        Toast.makeText(this,"displayyyyyyyy",Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reference = db.collection("Managers").document(ManagerPrevalent.currentOnlineManagers.getUsername());
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(managerSetting.this,"tsk success",Toast.LENGTH_SHORT).show();
                    DocumentSnapshot snap = task.getResult();
                    if (snap.exists()) {
                        Toast.makeText(managerSetting.this,"snap exist",Toast.LENGTH_SHORT).show();
                        String image = (String) snap.get("image");
                        String name = (String) snap.get("username");
                        String password = (String) snap.get("password");
                        Toast.makeText(managerSetting.this,name,Toast.LENGTH_SHORT).show();
                        Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(password);



                        /*
                        String username = (String) docuemnt.get("username");  //if the field is String
                             Boolean b = (Boolean) document.get("isPublic");       //if the field is Boolean
                           Integer i = (Integer) document.get("age")

                       */
                    }
                }
            }
        });

    }
}