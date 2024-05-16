//AQUI SE HARA LA CONEXION CON LA BASE DE DATOS PEDORRA 
package com.example.optisalud;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class helperFB extends AppCompatActivity{
    private DatabaseReference conexion;
    private Spinner listaClinica;
    private AutoCompleteTextView listaMedCodigos;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private boolean activo;

    private String nombreConsultado;

    private Context contextoConexion;
    static private ArrayList<String> meds;
    static private ArrayList<String> clinicas;





    public  helperFB(Context context){
        FirebaseApp.initializeApp(context);
        contextoConexion=context;
        conexion=  FirebaseDatabase.getInstance().getReference();
        nombreConsultado="";
    }
    public  helperFB(Context context,Spinner spinnerClinica){
        FirebaseApp.initializeApp(context);
        contextoConexion=context;
        conexion=  FirebaseDatabase.getInstance().getReference();
        nombreConsultado="";
        listaClinica=spinnerClinica;
    }

    public  helperFB(Context context,AutoCompleteTextView entrada){
        FirebaseApp.initializeApp(context);
        contextoConexion=context;
        conexion=  FirebaseDatabase.getInstance().getReference();
        nombreConsultado="";
        listaMedCodigos=entrada;
    }

    private boolean medExiste(){
        return (meds!=null);
    }
    private boolean clinicaExiste(){
        return (clinicas!=null);
    }
    public String consultarNombre(String nss, String curp, Context context,Intent intent){
        DatabaseReference usuariosRef =conexion;
        usuariosRef.child("usuarios").child(nss).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    dataSnapshot.getValue();
                    nombreConsultado = dataSnapshot.child("nombre").getValue(String.class);
                    String clinicaConsultada = dataSnapshot.child("clinica").getValue(String.class);
                    Toast.makeText(context, nombreConsultado, Toast.LENGTH_SHORT).show();
                    Datos.crear(nombreConsultado, nss, curp,clinicaConsultada );
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
    public boolean activo() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }
    public interface RegistroCallback {
        void onRegistroExitoso();
        void onRegistroFallido(String mensaje);
    }
    public void registrarDB(String nss, String curp, String name,String clinica, RegistroCallback callback) {
        String email = nss + "@gmail.com";

        mAuth.createUserWithEmailAndPassword(email, curp)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(contextoConexion, name, Toast.LENGTH_SHORT).show();

                        Datos.crear(name, nss, curp, clinica);
                        guardarNombre(name, nss, clinica);
                        callback.onRegistroExitoso();
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            callback.onRegistroFallido("El NSS ya fue registrado");
                        } else {
                            Exception exception = task.getException();
                            callback.onRegistroFallido("Error al registrar usuario");
                        }
                    }
                });
    }
    public void guardarNombre(String name, String nss, String clinica){
        DatabaseReference nombreRef = conexion.child("usuarios").child(nss);
        nombreRef.child("nombre").setValue(name);
        DatabaseReference clinicaRef = conexion.child("usuarios").child(nss);
        clinicaRef.child("clinica").setValue(clinica);


    }
    public ArrayList<String> getMedicamentos(Context contexto){
        if(medExiste()) return meds;
        meds = new ArrayList<>();
        DatabaseReference ref=conexion.child("medicamentos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String clinica=Datos.getInstance().getClinica();
                for(DataSnapshot child: snapshot.getChildren()){
                    if(!child.child(clinica).exists()){
                        continue;
                    }
                    meds.add(child.getKey());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(contexto, "Error al conectarse", Toast.LENGTH_SHORT).show();

            }
        });
        return meds;

    }
    public void getCantMed(String nombre, String clinica,Context contexto){
        DatabaseReference med=conexion.child("medicamentos").child(nombre).child(clinica).child("cantidad");
        //med.child("cantidad");
        med.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Toast.makeText(contexto, "Cantidad: " + snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contexto, "No encontrado", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(contexto, "Error al conectarse ", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void buscarMedClinica(String nombre, String clinica, TextView vistaTexto, TextView vistaCodigo, TextView vistaCantidad){
        DatabaseReference medRef=conexion.child("medicamentos");
        medRef.child(nombre).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(clinica).exists()){
                    String codigo=snapshot.child("codigo").getValue(String.class);
                    String cantidad=snapshot.child(clinica).child("cantidad").getValue(String.class);

                    //PROBAR TENER MAS DE UNA CLINICA
                    //vista.setText("El medicamento "+nombre+" Existe en la clinica "+clinica+" con "+snapshot.child("cantidad").getValue(String.class));
                    vistaTexto.setText(nombre);
                    vistaCodigo.setText(codigo);
                    vistaCantidad.setText(cantidad);
                }else{
                    vistaTexto.setText("Este medicamento no existe");
                    vistaCantidad.setText("0");
                    vistaCodigo.setText("-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                vistaTexto.setText("Error "+error.getMessage());

            }
        });
    }
    public void prueba(){
        DatabaseReference usuariosRef = conexion;
        usuariosRef.child("usuarios").child("2555").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    dataSnapshot.getValue();
                }else{

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores, si es necesario
            }

        });

    }
    public ArrayList<String> getClinicas(){
        clinicas=new ArrayList<>();
        DatabaseReference ref=conexion.child("clinicas");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){

                    if(child.getValue(int.class)==0){
                        continue;
                    }
                    clinicas.add(child.getKey());
                }
                String[] opciones = new String[clinicas.size()];
                opciones = clinicas.toArray(opciones);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(contextoConexion, android.R.layout.simple_spinner_item, opciones);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                listaClinica.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(contextoConexion, "Error al conectarse: "+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        return clinicas;
    }
    public void cerrarSesion(){

        FirebaseAuth.getInstance().signOut();
        mAuth.signOut();

    }
    public void getMedicamentosCodigos(){
        ArrayList<String> datos=new ArrayList<String>();
        DatabaseReference ref=conexion.child("medicamentos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String clinica=Datos.getInstance().getClinica();
                for(DataSnapshot child: snapshot.getChildren()){
                    if(!child.child(clinica).exists()){
                        continue;
                    }
                    datos.add(child.getKey());
                    datos.add(child.child("codigo").getValue(String.class));
                }
                String[] opciones = new String[datos.size()];
                opciones = datos.toArray(opciones);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(contextoConexion, android.R.layout.simple_dropdown_item_1line, opciones);
                listaMedCodigos.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(contextoConexion, "Error al conectarse", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void verificarCodigo (String nombre, String clinica, TextView vistaTexto, TextView vistaCodigo, TextView vistaCantidad){


        DatabaseReference usuariosRef = conexion.child("codigoMeds").child(nombre.toUpperCase());
        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String nuevoNombre=dataSnapshot.getValue(String.class);
                    buscarMedClinica(nuevoNombre,clinica,vistaTexto,vistaCodigo,vistaCantidad);
                }else{
                    buscarMedClinica(nombre,clinica,vistaTexto,vistaCodigo,vistaCantidad);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores, si es necesario
            }

        });




    }

}