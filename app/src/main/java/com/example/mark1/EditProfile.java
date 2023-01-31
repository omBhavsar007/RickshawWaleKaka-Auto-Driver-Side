package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class EditProfile extends AppCompatActivity {

    RadioButton male;
    RadioButton female;
    RadioButton other;
    String a;
    EditText Name;
    EditText e_mail;
    EditText phone;
    EditText age;
    Button btn1;
    Button btn2;
    DatabaseReference db;
    StorageReference sr;
    ImageView autoD;
    TextView uploadPhoto;
    Uri imageUri;
    static final int PICK_IMAGE_REQUEST = 1;
    String mobno;
    ProgressDialog progressDialog;
    String name;
    ArrayAdapter<String> adapterItems;

    AutoCompleteTextView autoState,autoDistrict,autoTaluka;
    String[] state = {"Maharashtra","Gujarat","Karnataka"};


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditProfile.this,HomePage.class).putExtra("mobno",mobno));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Photo...");

        Name = (EditText) findViewById(R.id.Name);
        e_mail = (EditText) findViewById(R.id.Email);
        phone = (EditText) findViewById(R.id.Phone);
        age = (EditText) findViewById(R.id.Age);
        btn2 = (Button) findViewById(R.id.button2);
        btn1 = (Button) findViewById(R.id.button);
        uploadPhoto = (TextView) findViewById(R.id.uploadphoto);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        other = (RadioButton) findViewById(R.id.other);
        autoD = (ImageView) findViewById(R.id.imageView2);
        mobno = getIntent().getStringExtra("mobno");

        db = FirebaseDatabase.getInstance().getReference().child("Demo1");
        sr = FirebaseStorage.getInstance().getReference("DriverProfilePhotos").child(mobno);

        db.child(mobno).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    DataSnapshot dataSnapshot = task.getResult();

                    String name = String.valueOf(dataSnapshot.child("name").getValue());
                    String mail = String.valueOf(dataSnapshot.child("email").getValue());
                    String no = String.valueOf(dataSnapshot.child("mobileNo").getValue());

                    Name.setText(name);
                    e_mail.setText(mail);
                    phone.setText(no);
                }
            }
        });



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAImage();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfilePhoto();
            }
        });
    }

    private void editProfile()
    {
        String age1 = age.getText().toString();

        if (male.isChecked()) {
            a = male.getText().toString();
        } else if (female.isChecked()) {
            a = female.getText().toString();
        } else if (other.isChecked()) {
            a = other.getText().toString();
        }
        String gender = a;

        if(TextUtils.isEmpty(age1))
        {
            age.setError("This Field Can't Be Empty");
            return;
        }

        if(TextUtils.isEmpty(gender))
        {
            Toast.makeText(this, "Please Enter your Gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if(imageUri == null && name == null)
        {
            Toast.makeText(this, "Please Upload Profile Photo", Toast.LENGTH_SHORT).show();
            return;
        }


        db.child(mobno).child("age").setValue(age1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            db.child(mobno).child("gender").setValue(gender)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void selectAImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST &&  resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(autoD);
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadProfilePhoto()
    {
        progressDialog.show();
        if(imageUri != null)
        {
            name = System.currentTimeMillis()+"."+getFileExtension(imageUri);
            StorageReference fileReference = sr.child(name);
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(EditProfile.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                            //Upload u = new Upload("8380885374photo",taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            db.child(mobno).child("ProfilePhoto").setValue(name);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
        }
    }
}