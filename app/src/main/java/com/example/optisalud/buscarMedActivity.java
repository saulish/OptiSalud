package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;


public class buscarMedActivity extends AppCompatActivity {
    private EditText nombre;
    private TextView nombreTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_med);

        nombre=findViewById(R.id.medBuscar);
        nombreTxt=findViewById(R.id.nombreObtenido);


    }
    public void buscarMed(View view){
        String medicamento = nombre.getText().toString();
        medicamento = medicamento.toLowerCase();
        medicamento = medicamento.substring(0, 1).toUpperCase() + medicamento.substring(1);

        String clinica =Datos.getInstance().getClinica();
        if(nombre.getText().toString().equals("")){
            nombreTxt.setText("INGRESA ALGO");
            return;
        }
        helperFB conexion=new helperFB(this);
        conexion.buscarMedClinica(medicamento,clinica,nombreTxt);



    }
}