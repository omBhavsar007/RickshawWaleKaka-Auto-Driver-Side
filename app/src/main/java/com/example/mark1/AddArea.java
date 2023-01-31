package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddArea extends AppCompatActivity {

    Button b1, b2;
    TextInputEditText edt;
    String[] items = {"English", "Hindi", "Marathi"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ArrayList<String> schools = new ArrayList<String>();
    String selectedSchool;
    DatabaseReference db,dbs;
    String mobno;
    NavigationView navigationView;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddArea.this,HomePage.class).putExtra("mobno",mobno));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);
        mobno = getIntent().getStringExtra("mobno");
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        edt = findViewById(R.id.editext);
        navigationView = findViewById(R.id.navigationview);

        db = FirebaseDatabase.getInstance().getReference().child("Demo1");
        dbs = FirebaseDatabase.getInstance().getReference();

        db.child(mobno).child("Schools").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:  snapshot.getChildren())
                {
                    schools.add((String) dataSnapshot.child("schoolName").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput(view);
//                edt.getText().clear();
                addArea();
            }
        });


        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_main, schools);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSchool = adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    private boolean validateeditext(){
        String str1 = edt.getText().toString();
        if(str1.isEmpty()){
            edt.setError("Feild can't be empty");
            return false;
        }else{
            edt.setError(null);
            return true;
        }
    }

    private boolean validateautocompletetext(){
        String str2 = autoCompleteTxt.getText().toString();
        if(str2.isEmpty()){
            autoCompleteTxt.setError("Field can't be empty");
            return false;
        }else{
            autoCompleteTxt.setError(null);
            return true;
        }
    }

    public void confirmInput(View v){
        if(!validateeditext() | !validateautocompletetext()){
            return;
        }
    }

    private void addArea()
    {
        String area = String.valueOf(edt.getText());

        if(TextUtils.isEmpty(area))
        {
            edt.setError("This Field Can't Be Empty");
            return;
        }

        dbs.child("SchoolsDemo").child(selectedSchool).child(mobno).child("Areas").child(area).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AddArea.this, "Location added successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}