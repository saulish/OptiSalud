package com.example.optisalud;
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

    private boolean verificar_repetido(String curp,String nss){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("usuarios");
        Query query = databaseReference.orderByKey().equalTo(nss);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    msj.setText("NSS ya registrado");
                    DataSnapshot nssSnapshot = dataSnapshot.child(nss);
                    String curp_bd = nssSnapshot.child("Curp").getValue(String.class);
                    if (curp != null & Objects.equals(curp, curp_bd)) {

                        msj.setText("Curp y NSS ya registrados");
                        return;


                    } else {
                        //msj.setText("No se encontro este CURP");
                    }

                } else {
                    //msj.setText("No se encontro este NSS");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                msj.setText("Error");
            }
        });
        return (msj.getText()=="\"Curp y NSS ya registrados\"" || msj.getText()=="\"NSS ya registrado\"");

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
        /*
        if(verificar_repetido(Curp, nss)){
            //msj.setText("NSS duplicado");
            return false;
        }

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

