package com.example.optisalud;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class iniciar_activity extends AppCompatActivity {
    private Button enviar;
    private TextView msj;
    private EditText curp;
    private  EditText nss;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle estadoInstancia){

        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_iniciar);
        enviar=findViewById(R.id.enviar_inicio);

        msj = findViewById(R.id.Texto_msj);
        curp = findViewById(R.id.curp_iniciar);
        nss= findViewById(R.id.nss_iniciar);

        mAuth = FirebaseAuth.getInstance();




    }
    private void guardarInstancia(String name){



    }
    private void getName_BD(String nss) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usuariosRef = databaseReference.child("usuarios");

        usuariosRef.child(nss).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String name = dataSnapshot.getValue(String.class);

                    Toast.makeText(iniciar_activity.this, name, Toast.LENGTH_SHORT).show();

                    Datos.getInstance().setDatosUsuario(name, curp.getText().toString(),nss);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores, si es necesario
            }

        });

    }

    private void verificar_cuenta(String nss, String Curp){
        String email=nss+"@gmail.com";
        String password=Curp;
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            getName_BD(nss);

                        } else{
                            msj.setText("Fallo en la autenticación");
                        }
                    }
                });

    }
    public void inicio_entrar_menu (View view){
        String NSS =nss.getText().toString();
        String CURP =curp.getText().toString();
        if(NSS.equals("") || CURP.equals(" ")){
            msj.setText("Datos faltantes");
            return;
        }
       verificar_cuenta(nss.getText().toString(),curp.getText().toString());
        if(!Datos.existe()){
            Intent intent = new Intent(iniciar_activity.this, menu_activity.class );
            startActivity(intent);
        }



    }

}
