package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class menu_activity extends AppCompatActivity {
    private Button enviar;
    private ImageView conf;
    @Override
    protected void onCreate(Bundle estadoInstancia){
        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_principal);
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
    public void ir_menu(View vier){
        Intent intent = new Intent(menu_activity.this, menu_activity.class );
        startActivity(intent);
    }
    public void ir_medicamentos(View vier){
        Intent intent = new Intent(menu_activity.this, buscarMedActivity.class );
        startActivity(intent);
    }
    public void ir_domicilio(View vier){
        Intent intent = new Intent(menu_activity.this, domicilioActivity.class );
        startActivity(intent);
    }
    public void ir_Escaner(View view){
        Toast.makeText(this,"Esta funcion será añadida pronto",Toast.LENGTH_SHORT).show();
        return;
    }
    public void ir_Dudas(View view){
        Intent intent=new Intent(this, ActivityDudas.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        helperFB conexion=new helperFB(this);
        // Verifica si el usuario ha iniciado sesión
        if (!conexion.activo()) {
            super.onBackPressed();
        }
    }


}
