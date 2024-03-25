package com.example.optisalud;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class registrar_activity extends AppCompatActivity {
    private Button registro;
    private TextView msj;
    private EditText nombre;
    private EditText curp;
    private EditText nss;


    @Override
    protected void onCreate(Bundle estadoInstancia){
        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_registro);
        registro=findViewById(R.id.enviar_crear);
        msj = findViewById(R.id.Text_msj);

        nombre = findViewById(R.id.nombre);
        curp   = findViewById(R.id.curp);
        nss    = findViewById(R.id.nss);

    }
    private boolean verificar_curp(String curp){
        return curp.length() != 18;
    }
    private boolean verificar_cuenta(String nombre, String Curp,String nss ){
        if(Objects.equals(nombre, "") || Objects.equals(Curp, "") || Objects.equals(nss, "")){
            msj.setText("Datos faltantes");
            return false;
        }

        if(verificar_curp(Curp)){
            msj.setText("Curp invalida");
            return false;
        }


        return true;
    }
    public void crear_entrar_menu(View view) {
        helperFB conexion=new helperFB(registrar_activity.this);
        if(verificar_cuenta(nombre.getText().toString(),curp.getText().toString(),nss.getText().toString())){
            conexion.registrarDB(nss.getText().toString(),curp.getText().toString(),nombre.getText().toString(),msj);
            conexion.guardarNombre(nombre.getText().toString(),nss.getText().toString());
            Intent intent = new Intent(registrar_activity.this, menu_activity.class);
            startActivity(intent);
        }

    }

}

