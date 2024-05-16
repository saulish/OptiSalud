package com.example.optisalud;
import static androidx.core.content.ContextCompat.startActivity;

import  java.lang.*;
import java.util.ArrayList;
import java.util.Objects;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Datos extends AppCompatActivity {

    private static Datos instance;

    public static ArrayList<Medicamento> medsGuardados=new ArrayList<Medicamento>();

    private static Solicitud solMed;
    private String nombre;
    private String curp;
    private String nss;
    private String clinica;

    //private Datos() {}
    private Datos(String name, String nss, String CURP,String clinica){
        this.curp=CURP;
        this.nss=nss;
        this.nombre=name;
        this.clinica=clinica;
    }


    public static Datos getInstance() {
        return instance;
    }

    public static void crear(String name, String nss, String CURP, String clinica){

        instance= new Datos(name,nss,CURP,clinica);

    }


    public static boolean existe(){

        return !(instance==null);
    }


    public String getNombre() {
        return nombre;
    }
    public String getCurp(){
        return curp;
    }
    public String getNss(){
        return nss;
    }
    public String getClinica(){return clinica;}
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public static int getIndex(String name){
        for(int i=0;i<medsGuardados.size();i++){
            if(Objects.equals(medsGuardados.get(i).nombre, name)){
                return i;
            }
        }
        return -1;
    }

    public static void limpiarLista(){
        medsGuardados.clear();
    }

    public void setSolMed(Solicitud solicitud){
        solMed=solicitud;

    }
    public static Solicitud getSolMed(){
        return solMed;
    }


}
