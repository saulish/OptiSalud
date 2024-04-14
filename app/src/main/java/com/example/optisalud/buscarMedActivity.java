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


    }
}