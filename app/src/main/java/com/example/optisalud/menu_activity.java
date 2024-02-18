package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class menu_activity extends AppCompatActivity {
    private Button enviar;
    private ImageView conf;
    @Override
    protected void onCreate(Bundle estadoInstancia){
        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_principal);
        /*
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("prueba").child("usuario1").setValue("Masturbin");
        */

        conf=findViewById(R.id.conf_logo);




    }
    public void ir_config (View view){
        Intent intent = new Intent(menu_activity.this, activity_conf.class );
        startActivity(intent);
    }
    public void ir_user(View vier){
        Intent intent = new Intent(menu_activity.this, activity_user.class );
        startActivity(intent);
    }


}
