package com.example.optisalud;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class activity_user extends AppCompatActivity {
    private TextView Text_nombre;
    private TextView Text_curp;
    private TextView Text_nss;

    private String  nombre;
    private String curp;
    private String nss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        Text_nombre=findViewById(R.id.nombre_user);
        Text_curp=findViewById(R.id.curp_user);
        Text_nss=findViewById(R.id.nss_user);

        nombre=Datos.getInstance().getNombre();
        curp= Datos.getInstance().getCurp();
        nss= Datos.getInstance().getNss();


        Text_nombre.setText(nombre);
        Text_curp.setText(curp);
        Text_nss.setText(nss);

    }
}