package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
        lista=(ListView) findViewById(R.id.listMedicamentos);



        ArrayList<String> medicamentos =  pedirLista();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.style_list, medicamentos);
        if(medicamentos.isEmpty()){
            //Toast.makeText(getApplicationContext(), "Valio barriga señor verga", Toast.LENGTH_SHORT).show();

        }
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String elementoSeleccionado = (String) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), "Elemento seleccionado: " + elementoSeleccionado, Toast.LENGTH_SHORT).show();
                helperFB conexion=new helperFB(MedicamentosActivity.this);
                conexion.getCantMed(elementoSeleccionado,"IMSS 180",MedicamentosActivity.this);

            }
        });
    }
    private ArrayList<String> pedirLista() {
        helperFB conexion = new helperFB(this);

        return conexion.getMedicamentos(this);
    }

    public void ir_config (View view){
        Intent intent = new Intent(MedicamentosActivity.this, activity_conf.class );
        startActivity(intent);
    }
    public void ir_user(View vier){
        Intent intent = new Intent(MedicamentosActivity.this, activity_user.class );
        startActivity(intent);
    }
    public void ir_menu(View vier){
        Intent intent = new Intent(MedicamentosActivity.this, menu_activity.class );
        startActivity(intent);
    }
}