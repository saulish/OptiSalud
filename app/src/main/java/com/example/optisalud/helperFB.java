//AQUI SE HARA LA CONEXION CON LA BASE DE DATOS PEDORRA 
package com.example.optisalud;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class helperFB extends AppCompatActivity{
    private DatabaseReference conexion;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private boolean activo;

    private String nombreConsultado;

    private Context contextoConexion;




    public  helperFB(Context context){
        FirebaseApp.initializeApp(context);
        contextoConexion=context;
        conexion=  FirebaseDatabase.getInstance().getReference();
        nombreConsultado="";
    }
    public String consultarNombre(String nss, String curp, Context context,Intent intent){
        DatabaseReference usuariosRef = conexion.child("usuarios");
        usuariosRef.child(nss).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    nombreConsultado = dataSnapshot.getValue(String.class);
                    Toast.makeText(context, nombreConsultado, Toast.LENGTH_SHORT).show();
                    Datos.crear(nombreConsultado, nss, curp );
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores, si es necesario
            }

        });

        return nombreConsultado;
    }

    public interface callbackAuth{
        void authExitosa();
        void authFallida();
    }
    public void iniciarSesion(String nss, String pass, callbackAuth callback){
        String correo=nss+"@gmail.com";

        mAuth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            activo=true;

                            callback.authExitosa();
                        } else {
                            callback.authFallida();

                        }
                    }
                });
    }
    public void registrarDB(String nss, String curp,String name, TextView msj){
        String email=nss+"@gmail.com";
        mAuth.createUserWithEmailAndPassword(email, curp)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Datos.crear(name, nss, curp );


                        //FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        Exception e = task.getException();
                        msj.setText("Error en la base de datos, "+e);
                    }
                });
    }
    public void guardarNombre(String name, String nss){
        DatabaseReference nuevoUsuarioRef = conexion.child("usuarios");
        nuevoUsuarioRef.child(nss).setValue(name);

    }

}