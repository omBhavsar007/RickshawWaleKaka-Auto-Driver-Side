package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ViewDetails extends AppCompatActivity {


    ImageView photo;
    TextView name1, name2, phone1, phone2, email, address, relation, age, gender, name3, time, std, div;
    DatabaseReference db,db1,db2;
    StorageReference sr;
    String sname;
    String flag;
    String mobno;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(flag.equals("1"))
        {
            Intent intent = new Intent(ViewDetails.this,ShowChildren.class);
            intent.putExtra("sname",sname);
            intent.putExtra("mobno",mobno);
            startActivity(intent);
            finish();
        }
        else if(flag.equals("2"))
        {
            Intent intent = new Intent(ViewDetails.this,ShowRequests.class);
            intent.putExtra("mobno",mobno);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);


        photo = findViewById(R.id.Child_photo);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.parent_name);
        phone1 = findViewById(R.id.phone_no1);
        phone2 = findViewById(R.id.phone_no2);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        relation = findViewById(R.id.relation);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        name3 = findViewById(R.id.school_name);
        time = findViewById(R.id.time1);
        std = findViewById(R.id.Standard);
        div = findViewById(R.id.div);

        sname = getIntent().getStringExtra("sname");
        String id = getIntent().getStringExtra("id");
        String pno = getIntent().getStringExtra("pno");
        String cphoto = getIntent().getStringExtra("cphoto");
        flag = getIntent().getStringExtra("flag1");
        mobno = getIntent().getStringExtra("mobno");
        System.out.println(mobno);
        System.out.println(sname);

        String m = "8380885474";

        sr = FirebaseStorage.getInstance().getReference("Childphotos/").child(pno).child(cphoto);

        db = FirebaseDatabase.getInstance().getReference().child("Child").child(id);
        db1 = FirebaseDatabase.getInstance().getReference().child("Child").child(id).child("address");
        db2 = FirebaseDatabase.getInstance().getReference().child("Check2").child(pno);

        try {

            File localfile = File.createTempFile("tempfile",".jpg");

            sr.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    photo.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }




        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cname = (String) snapshot.child("name").getValue();
                String pname = (String) snapshot.child("ParentName").getValue();
                String pno = (String) snapshot.child("parentnumber").getValue();
                String re = (String) snapshot.child("relation").getValue();
                String cage = (String) snapshot.child("age").getValue();
                String cdiv = (String) snapshot.child("division").getValue();
                String cgender = (String) snapshot.child("gender").getValue();
                String sname = (String) snapshot.child("schoolname").getValue();
                String stime = (String) snapshot.child("schooltime").getValue();
                String cstd = (String) snapshot.child("standard").getValue();

                name1.setText(cname);

                phone1.setText(pno);
                relation.setText(re);
                age.setText(cage);
                gender.setText(cgender);
                name3.setText(sname);
                time.setText(stime);
                div.setText(cdiv);
                std.setText(cstd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String city =  (String) snapshot.child("city").getValue();
                String district =  (String) snapshot.child("district").getValue();
                String houseno =  (String) snapshot.child("houseno").getValue();
                String pincode =  (String) snapshot.child("pincode").getValue();
                String society =  (String) snapshot.child("society").getValue();
                String state =  (String) snapshot.child("state").getValue();
                String street =  (String) snapshot.child("street").getValue();
                String taluka = "Dhule";

                String address1 = houseno+", "+society+","+street+", "+city+", "+taluka+", "+district+", "+state+", "+pincode;

                address.setText(address1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String palno = (String) snapshot.child("alternateno").getValue();
                String pemail = (String) snapshot.child("email").getValue();
                String pname = (String) snapshot.child("name").getValue();

                phone2.setText(palno);
                email.setText(pemail);
                name2.setText(pname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}