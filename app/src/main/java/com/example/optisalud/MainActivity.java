package com.example.optisalud;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button inicio;
    private Button registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);




        inicio=findViewById(R.id.boton_inicio_main);
        registro=findViewById(R.id.boton_registro);
        //Datos.eliminarDatos();
    }

    public void crear_cuenta (View view){
        Intent intent = new Intent(MainActivity.this, registrar_activity.class );
        startActivity(intent);
    }
    public void iniciar_sesion (View view){

        Intent intent = new Intent(MainActivity.this, iniciar_activity.class );
        startActivity(intent);


    }





}
