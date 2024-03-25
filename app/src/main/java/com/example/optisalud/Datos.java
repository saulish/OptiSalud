package com.example.optisalud;
import  java.lang.*;
import android.widget.Toast;

public class Datos {

    private static Datos instance;
    private String nombre;
    private String curp;
    private String nss;

    //private Datos() {}
    private Datos(String name, String nss, String CURP){
        this.curp=CURP;
        this.nss=nss;
        this.nombre=name;
    }

    public static Datos getInstance() {

        //System.out.print("aca");

        return instance;
    }

    public static void crear(String name, String nss, String CURP){

        instance= new Datos(name,nss,CURP);

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
