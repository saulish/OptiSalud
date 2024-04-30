package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;


public class registrar_activity extends AppCompatActivity {
    private Button registro;
    private TextView msj;
    private EditText nombre;
    private EditText curp;
    private EditText nss;
    private Spinner clinica;


    @Override
    protected void onCreate(Bundle estadoInstancia){
        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_registro);
        registro=findViewById(R.id.enviar_crear);
        msj = findViewById(R.id.Text_msj);

        nombre = findViewById(R.id.nombre);
        curp   = findViewById(R.id.curp);
        nss    = findViewById(R.id.nss);
        clinica=findViewById(R.id.clinicaSelect);
        pedirClinicas(clinica);
    }

    private  ArrayList<String> pedirClinicas(Spinner clinica){
        helperFB conexion=new helperFB(this,clinica);
        return conexion.getClinicas();
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
        Intent intent = new Intent(registrar_activity.this, menu_activity.class);
        String clinicaText = clinica.getSelectedItem().toString();
        helperFB conexion=new helperFB(this);
        if (verificar_cuenta(nombre.getText().toString(), curp.getText().toString(), nss.getText().toString())) {
            conexion.registrarDB(nss.getText().toString(),curp.getText().toString(),nombre.getText().toString(),clinicaText, new helperFB.RegistroCallback(){
                public void onRegistroExitoso() {
                    startActivity(intent);
                }

                @Override
                public void onRegistroFallido(String mensaje) {
                    msj.setText(mensaje);
                }
            });

        }

    }


}

