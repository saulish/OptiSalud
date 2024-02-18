package com.example.optisalud;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
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
    private boolean verificar_curp(String cupr){
        return false;
    }
    private boolean verificar_cuenta(String nombre, String Curp,String nss ){
        if(Objects.equals(nombre, "") || Objects.equals(Curp, "") || Objects.equals(nss, "")){
            msj.setText("Datos faltantes");
            return false;
        }

        if(verificar_curp(Curp)){
            return false;
        }
        /*
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.datos.gob.mx/v1/curp/" + Curp;

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseData = response.body().string();

                msj.setText(responseData);
            } else {
                msj.setText("Error al realizar la solicitud");
                return false;
            }
        } catch (IOException e) {
            msj.setText("ERROR");
            return false;
        }
        */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nuevoUsuarioRef = databaseReference.child("usuarios").child(nss);

        nuevoUsuarioRef.child("nombre").setValue(nombre);
        nuevoUsuarioRef.child("Curp").setValue(Curp);
        Datos.getInstance().setDatosUsuario(nombre, Curp, nss);

        return true;
    }
    public void crear_entrar_menu(View view) {
        if(verificar_cuenta(nombre.getText().toString(),curp.getText().toString(),nss.getText().toString())){
            Intent intent = new Intent(registrar_activity.this, menu_activity.class);

            startActivity(intent);
        }

    }

}

