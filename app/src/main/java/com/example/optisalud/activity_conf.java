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

    }
    public void ir_config (View view){
        Intent intent = new Intent(activity_conf.this, activity_conf.class );
        startActivity(intent);
    }
    public void ir_user(View vier){
        Intent intent = new Intent(activity_conf.this, activity_user.class );
        startActivity(intent);
    }
    public void ir_menu(View vier){
        Intent intent = new Intent(activity_conf.this, menu_activity.class );
        startActivity(intent);
    }}