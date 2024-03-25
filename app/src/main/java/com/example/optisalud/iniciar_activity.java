package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class iniciar_activity extends AppCompatActivity {
    private Button enviar;
    private TextView msj;
    private EditText curp;
    private  EditText nss;

    @Override
    protected void onCreate(Bundle estadoInstancia){

        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_iniciar);
        enviar=findViewById(R.id.enviar_inicio);

        msj = findViewById(R.id.Texto_msj);
        curp = findViewById(R.id.curp_iniciar);
        nss= findViewById(R.id.nss_iniciar);





    }
    private void guardarInstancia(String name){



    }
    private void verificar_cuenta(String nss, String Curp){
        msj.setText("");
        Intent intent = new Intent(iniciar_activity.this, menu_activity.class );
        helperFB conexion=new helperFB(this);
        conexion.iniciarSesion(nss, Curp, new helperFB.callbackAuth() {
            @Override
            public void authExitosa() {
                conexion.consultarNombre(nss, Curp,iniciar_activity.this,intent);
                Intent intent = new Intent(iniciar_activity.this, menu_activity.class );
                startActivity(intent);

            }

            @Override
            public void authFallida() {
                msj.setText("Fallo en la autenticación");

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


        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);

        }
        if(Datos.existe()){
            Intent intent = new Intent(iniciar_activity.this, menu_activity.class );
            startActivity(intent);
        }




    }

}
