package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class VehicleDetail1 extends AppCompatActivity {

    EditText edtuser;
    EditText edtuser1;
    EditText edtuser2;
    Button btn,btnS,btnU;
    DatabaseReference db;
    StorageReference sr;
    Uri imageUri;
    ImageView vehi;
    static final int PICK_IMAGE_REQUEST = 1;
    String name;
    String mobno;
    ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VehicleDetail1.this,HomePage.class).putExtra("mobno",mobno));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail1);
        mobno = getIntent().getStringExtra("mobno");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Vehicle Photo...");

        edtuser = findViewById(R.id.name);
        edtuser1 =  findViewById(R.id.no);
        edtuser2=  findViewById(R.id.capacity);
        btn = findViewById(R.id.login);
        btnS = findViewById(R.id.btnSelect);
        btnU = findViewById(R.id.btnUpload);
        db = FirebaseDatabase.getInstance().getReference().child("Demo1").child(mobno);
        sr = FirebaseStorage.getInstance().getReference("DriverProfilePhotos").child(mobno);

        vehi = findViewById(R.id.vehiclePhoto);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicleDetail();
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAImage();
            }
        });
        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVehicleImage();
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
            Picasso.with(this).load(imageUri).into(vehi);
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void addVehicleDetail()
    {
        String vehicleType = edtuser.getText().toString();
        String vehicleNo = edtuser1.getText().toString();
        String capacity = edtuser2.getText().toString();

        if(TextUtils.isEmpty(vehicleType))
        {
            edtuser.setError("This Field Can't Be Empty");
            return;
        }

        if(TextUtils.isEmpty(vehicleNo))
        {
            edtuser1.setError("This Field Can't Be Empty");
            return;
        }
        if(TextUtils.isEmpty(capacity))
        {
            edtuser2.setError("This Field Can't Be Empty");
            return;
        }

        if(imageUri == null || name == null)
        {
            Toast.makeText(this, "Please upload the photo", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println(name);
        Vehicle vehicle = new Vehicle(vehicleType,vehicleNo,capacity,name);
        db.child("Vehicle").setValue(vehicle)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(VehicleDetail1.this, "Vehicle Details Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadVehicleImage()
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
                            Toast.makeText(VehicleDetail1.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                            //Upload u = new Upload("8380885374photo",taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VehicleDetail1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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