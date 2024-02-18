package com.example.optisalud;

public class Datos {
    private static Datos instance;
    private String nombre;
    private String curp;
    private String nss;

    private Datos() {}

    public static Datos getInstance() {
        if (instance == null) {
            instance = new Datos();
        }
        return instance;
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
