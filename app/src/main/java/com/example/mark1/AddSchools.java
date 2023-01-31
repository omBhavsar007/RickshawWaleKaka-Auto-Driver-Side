package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSchools extends AppCompatActivity {

    EditText edtSchoolName;
    EditText edtSchoolAddress;
    EditText edtSchoolNoOfTrips;
    EditText edtSchoolTripTime;
    Button btnSchool;
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTxt;
    String schools[] = {"Jay Hind High School","Maharana Pratap High School", "Agrasen High School","Chavra English Medium School"};
    String schoolname;

    DatabaseReference db,db1;
    String mobno;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddSchools.this,HomePage.class).putExtra("mobno",mobno));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schools);
        mobno = getIntent().getStringExtra("mobno");
        autoCompleteTxt = findViewById(R.id.school_name);
        edtSchoolAddress = (EditText) findViewById(R.id.inputAdress);
        edtSchoolNoOfTrips = (EditText) findViewById(R.id.inputNo_of_trips);
        edtSchoolTripTime = (EditText) findViewById(R.id.inputTime);

        btnSchool = (Button) findViewById(R.id.btnAddschool);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_main, schools);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                schoolname = adapterView.getItemAtPosition(i).toString();
            }
        });

        db = FirebaseDatabase.getInstance().getReference().child("Demo1").child(mobno);
        db1 = FirebaseDatabase.getInstance().getReference();

        btnSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSchool();
            }
        });
    }

    private void addSchool()
    {
        String schoolAddress = edtSchoolAddress.getText().toString();
        String noOftrips = edtSchoolNoOfTrips.getText().toString();
        String tripTime = edtSchoolTripTime.getText().toString();

        if(TextUtils.isEmpty(schoolname))
        {
            autoCompleteTxt.setError("This Field Can't Be Empty");
            return;
        }

        if(TextUtils.isEmpty(schoolAddress))
        {
            edtSchoolAddress.setError("This Field Can't Be Empty");
            return;
        }

        if(TextUtils.isEmpty(noOftrips))
        {
            edtSchoolNoOfTrips.setError("This Field Can't Be Empty");
            return;
        }

        if(TextUtils.isEmpty(tripTime))
        {
            edtSchoolTripTime.setError("This Field Can't Be Empty");
            return;
        }


        Schools schools = new Schools(schoolname,schoolAddress,noOftrips,tripTime);
        db.child("Schools").child(schoolname).setValue(schools)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            db1.child("SchoolsDemo").child(schoolname).child(mobno).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AddSchools.this, "School Added Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
    }
}