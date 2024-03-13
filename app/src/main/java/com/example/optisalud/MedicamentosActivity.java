package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicamentosActivity extends AppCompatActivity {
    private ListView lista;
    private ArrayList<String> medicamentos;


    @Override
    protected void onCreate(Bundle estadoInstancia) {
        super.onCreate(estadoInstancia);
        setContentView(R.layout.activity_medicamentos);

        medicamentos = new ArrayList<>();
        medicamentos.add("Paracetamol");
        medicamentos.add("Ibuprofeno");
        medicamentos.add("Acarbosa");
        medicamentos.add("Aciclovir");
        medicamentos.add("Acido Acetilsalicilico");
        medicamentos.add("Amoxiciina");
        medicamentos.add("Dextrosa");
        medicamentos.add("Minopac");
        medicamentos.add("Lievricin");

        lista=(ListView) findViewById(R.id.listMedicamentos);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.style_list, medicamentos);





        //AÑADIR MEDICAMENTOS CON LA BASE DE DATOS


        lista.setAdapter(adapter);
    }
}