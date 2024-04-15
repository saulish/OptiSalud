package com.example.optisalud;
import  java.lang.*;
import android.widget.Toast;

public class Datos {

    private static Datos instance;
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
    public  void setCurp (String curp){
        this.curp=curp;
    }
    public void setNss (String nss){
        this.nss=nss;
    }

    public void setDatosUsuario(String nombre, String curp, String nss) {
        this.nombre = nombre;
        this.curp = curp;
        this.nss = nss;
    }
}
