package com.example.optisalud;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class activity_user extends AppCompatActivity {
    private TextView Text_nombre;
    private TextView Text_curp;
    private TextView Text_nss;
    private Button salir;

    private String  nombre;
    private String curp;
    private String nss;
    private String clinica;


    private Spinner selectClinica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        Text_nombre=findViewById(R.id.nombre_user);
        Text_curp=findViewById(R.id.curp_user);
        Text_nss=findViewById(R.id.nss_user);

        salir=findViewById(R.id.salida);

        nombre=Datos.getInstance().getNombre();
        curp= Datos.getInstance().getCurp();
        nss= Datos.getInstance().getNss();
        clinica=Datos.getInstance().getClinica();
        Text_nombre.setText(nombre);
        Text_curp.setText(curp);
        Text_nss.setText(nss);


        selectClinica=findViewById(R.id.clinicaSelect);
        pedirClinicas(selectClinica);
        //PENSAR EN QUE SE PUEDA CAMBIAR LA CLINICA PREDETERMINADA DE ESTA FORMA





    }

    private void pedirClinicas(Spinner clinicas){
        helperFB conexion= new helperFB(this,clinicas);
        conexion.getClinicas();

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

    public void logOut(View vier){
        helperFB conexion=new helperFB(this);
        conexion.cerrarSesion();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);



    }

}
