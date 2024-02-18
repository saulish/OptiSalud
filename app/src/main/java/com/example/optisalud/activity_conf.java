package com.example.optisalud;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_conf extends AppCompatActivity {
    private TextView pruebD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);
        pruebD = findViewById(R.id.pruebaBD);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("prueba").child("usuario1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.getValue(String.class);
                pruebD.setText(nombre);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}