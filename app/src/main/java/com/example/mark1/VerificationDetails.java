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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class VerificationDetails extends AppCompatActivity {


    EditText Aedt,Ledt;
    Button btnSA,btnUA,btnSL,btnUL,btn;
    ImageView IA,IL;
    Uri ImageUri;
    DatabaseReference db;
    StorageReference sr;
    static final int PICK_IMAGE_REQUEST = 1;
    String nameA,nameL,aadharNo,LicenseNo;
    Boolean flagL = false,flagA = false;
    String mobno;
    ProgressDialog progressDialog;

    String which = "";
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VerificationDetails.this,HomePage.class).putExtra("mobno",mobno));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_details);
        mobno = getIntent().getStringExtra("mobno");
        Ledt = findViewById(R.id.etLicense);
        Aedt = findViewById(R.id.etAadhar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Photo...");

        btn = findViewById(R.id.btnSave);

        btnSA = findViewById(R.id.btnSelectA);
        btnUA = findViewById(R.id.btnUploadA);


        btnSL = findViewById(R.id.btnSelectL);
        btnUL = findViewById(R.id.btnUploadL);

        IA = findViewById(R.id.ivAadhar);
        IL = findViewById(R.id.ivLicense);

        db = FirebaseDatabase.getInstance().getReference().child("Demo1").child(mobno);
        sr = FirebaseStorage.getInstance().getReference("DriverProfilePhotos").child(mobno);

        btnSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAImageA();
            }
        });

        btnSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAImageL();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVerificationDetails();
            }
        });

        btnUA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAImage();
            }
        });

        btnUL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadlImage();
            }
        });
    }

    private void selectAImageA()
    {
        which = "Aadhar";
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private void selectAImageL()
    {
        which = "License";
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
            ImageUri = data.getData();

            if(which.equals("Aadhar"))
                Picasso.with(this).load(ImageUri).into(IA);
            else if(which.equals("License"))
                Picasso.with(this).load(ImageUri).into(IL);
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadAImage()
    {
        progressDialog.show();
        if(ImageUri != null)
        {
            nameA = System.currentTimeMillis()+"."+getFileExtension(ImageUri);
            StorageReference fileReference = sr.child(nameA);
            fileReference.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(VerificationDetails.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                            //Upload u = new Upload("8380885374photo",taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            flagA = true;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VerificationDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void uploadlImage()
    {
        progressDialog.show();
        if(ImageUri != null)
        {
            nameL = System.currentTimeMillis()+"."+getFileExtension(ImageUri);
            StorageReference fileReference = sr.child(nameL);
            fileReference.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(VerificationDetails.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                            //Upload u = new Upload("8380885374photo",taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            flagL = true;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VerificationDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void saveVerificationDetails()
    {

            LicenseNo = Ledt.getText().toString();
            aadharNo = Aedt.getText().toString();

            if(TextUtils.isEmpty(LicenseNo))
            {
                Ledt.setError("This Field Can't Be Empty");
                return;
            }

            if(TextUtils.isEmpty(aadharNo))
            {
                Aedt.setError("This Field Can't Be Empty");
                return;
            }

            if(ImageUri == null || nameL == null )
            {
                Toast.makeText(this, "Please upload License Card Image", Toast.LENGTH_SHORT).show();
                return;
            }

            if(ImageUri == null || nameA == null )
            {
                Toast.makeText(this, "Please upload Aadhar Card Image", Toast.LENGTH_SHORT).show();
                return;
            }

            Verification verification = new Verification(LicenseNo,nameL,aadharNo,nameA);

            db.child("VerificationDetails").setValue(verification)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(VerificationDetails.this, "Verification Details added successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
    }
}