package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle estadoInstancia){
        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_iniciar);
        enviar=findViewById(R.id.enviar_inicio);

        msj = findViewById(R.id.Texto_msj);
        curp = findViewById(R.id.curp_iniciar);
        nss= findViewById(R.id.nss_iniciar);

    }
    private boolean verificar_cuenta(String nss, String Curp){
        boolean verifcado;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("usuarios");
        Query query = databaseReference.orderByKey().equalTo(nss);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot nssSnapshot = dataSnapshot.child(nss);
                    String curp = nssSnapshot.child("Curp").getValue(String.class);
                    String nombre = nssSnapshot.child("nombre").getValue(String.class);
                    if (curp != null & Objects.equals(curp, Curp)) {

                        Datos.getInstance().setDatosUsuario(nombre, Curp, nss);

                        Intent intent = new Intent(iniciar_activity.this, menu_activity.class );
                        startActivity(intent);

                    } else {
                        msj.setText("No se encontro este CURP");
                    }

                } else {
                    msj.setText("No se encontro este NSS");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                msj.setText("Error");
            }
        });
        verifcado= msj.getText() == "Verificado";
        return verifcado;
    }
    public void inicio_entrar_menu (View view){
        String NSS =nss.getText().toString();
        String CURP =curp.getText().toString();
        if(NSS.equals("") || CURP.equals(" ")){
            msj.setText("Datos faltantes");
            return;
        }
        verificar_cuenta(nss.getText().toString(),curp.getText().toString());



    }

}
