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
    private TextView Text_clinica;

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
        Text_clinica=findViewById(R.id.unidadMedicaUser);

        nombre=Datos.getInstance().getNombre();
        curp= Datos.getInstance().getCurp();
        nss= Datos.getInstance().getNss();


        Text_nombre.setText(nombre);
        Text_curp.setText(curp);
        Text_nss.setText(nss);
        Text_clinica.setText("Clínnica 1 IMSS");

    }
    public void ir_config (View view){
        Intent intent = new Intent(activity_user.this, activity_conf.class );
        startActivity(intent);
    }
    public void ir_user(View vier){
        Intent intent = new Intent(activity_user.this, activity_user.class );
        startActivity(intent);
    }
    public void ir_menu(View vier){
        Intent intent = new Intent(activity_user.this, menu_activity.class );
        startActivity(intent);
    }
}