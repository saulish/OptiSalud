package com.example.optisalud;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class activity_conf extends AppCompatActivity {
    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    private Button num5;
    private Button num6;
    private Button num7;
    private Button num8;
    private Button num9;
    private Button num0;
    private Button punto;

    private Button igual;
    private Button suma;
    private Button resta;
    private Button multi;
    private Button divi;
    private Button clear;
    private TextView titulo;
    private TextView respuesta;

    private double operando1 = 0;
    private String operador = "";
    private boolean nuevaOperacion = true;

    private VideoView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        video=(VideoView)findViewById(R.id.videoGato);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.gatos2;
        Uri uri = Uri.parse(videoPath);
        video.setVideoURI(uri);

        // Configurar MediaController
        MediaController mediaController = new MediaController(this);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);

        // Configurar un OnPreparedListener para detectar cuando el video está listo para reproducirse
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Habilitar la reproducción en bucle
                mp.setLooping(true);
            }
        });

        // Iniciar la reproducción del video
        video.start();


        /*
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Habilitar la reproducción en bucle
                mp.setLooping(true);
            }
        });

        // Iniciar la reproducción del video*/
        video.start();



        num1= findViewById(R.id.num1);
        num2= findViewById(R.id.num2);
        num3= findViewById(R.id.num3);
        num4= findViewById(R.id.num4);
        num5= findViewById(R.id.num5);
        num6= findViewById(R.id.num6);
        num7= findViewById(R.id.num7);
        num8= findViewById(R.id.num8);
        num9= findViewById(R.id.num9);
        num0= findViewById(R.id.num0);
        punto= findViewById(R.id.punto);
        igual= findViewById(R.id.igual);
        suma= findViewById(R.id.suma);
        resta= findViewById(R.id.resta);
        multi=findViewById(R.id.multi);
        divi=findViewById(R.id.divi);
        clear=findViewById(R.id.clear);
        respuesta= findViewById(R.id.respuesta);
        //titulo = findViewById(R.id.calculadora);

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("1");
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("2");
            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("3");
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("4");
            }
        });

        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("5");
            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("6");
            }
        });


        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("7");
            }
        });

        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("8");
            }
        });

        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("9");
            }
        });

        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNumero("0");
            }
        });

        punto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nuevaOperacion) {
                    respuesta.setText("0.");
                    nuevaOperacion = false;
                } else if (!respuesta.getText().toString().contains(".")) {
                    respuesta.append(".");
                }
            }
        });

        //botón de igual
        igual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado();
                operador = "";
            }
        });

        // botones de operación
        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacion("+");
            }
        });

        resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacion("-");
            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacion("*");
            }
        });

        divi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacion("/");
            }
        });

        // botón de clear
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respuesta.setText("");
                operando1 = 0;
                operador = "";
                nuevaOperacion = true;
            }
        });
    }

    private void agregarNumero(String numero) {
        if (nuevaOperacion) {
            respuesta.setText(numero);
            nuevaOperacion = false;
        } else {
            respuesta.append(numero);
        }
    }

    private void realizarOperacion(String nuevoOperador) {
        if (!respuesta.getText().toString().isEmpty()) {
            if (!operador.isEmpty()) {
                calcularResultado();
            }
            operando1 = Double.parseDouble(respuesta.getText().toString());
            operador = nuevoOperador;
            nuevaOperacion = true;
        }
    }

    private void calcularResultado() {
        double operando2 = Double.parseDouble(respuesta.getText().toString());
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = operando1 + operando2;
                break;
            case "-":
                resultado = operando1 - operando2;
                break;
            case "*":
                resultado = operando1 * operando2;
                break;
            case "/":
                if (operando2 != 0) {
                    resultado = operando1 / operando2;
                } else {
                    // Manejar división por cero
                }
                break;
        }

        respuesta.setText(String.valueOf(resultado));
        nuevaOperacion = true;

    }
}

//prueba1