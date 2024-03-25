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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
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
        void authFallida(Task<AuthResult> task);
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
                            callback.authFallida(task);

                        }
                    }
                });
    }

    public void registrarDB(String nss, String curp, String name, TextView msj, Intent intent){
        String email=nss+"@gmail.com";
        mAuth.createUserWithEmailAndPassword(email, curp)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        guardarNombre(name,nss,intent);
                        Datos.crear(name, nss, curp );
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            msj.setText("El NSS ya fue registrado");
                        } else {
                            Exception exception = task.getException();
                            msj.setText("Error");
                        }
                    }
                });
    }
    public void guardarNombre(String name, String nss, Intent intent){
        DatabaseReference nuevoUsuarioRef = conexion.child("usuarios");
        nuevoUsuarioRef.child(nss).setValue(name);
        //startActivity(intent);

    }

}