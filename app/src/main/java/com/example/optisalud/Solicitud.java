package com.example.optisalud;

import java.util.ArrayList;
import java.util.Objects;

public class Solicitud {
    private String nombre;
    private String direccion;
    private String municipioLocalidad;
    private String referencia;
    private String clinica;
    private long telefono;
    private int codigoPostal;
    private int nss;
    private static ArrayList<Medicamento> listaSolicitud;
    public Solicitud(){

    }
    public Solicitud(String tNombre,String Tclinica,String Tdireccion,String tMuniLocl, String TRef,  long Ttelefono, int Tcp, int Tnss){
        this.nombre=tNombre;
        this.direccion=Tdireccion;
        this.municipioLocalidad = tMuniLocl;
        this.referencia=TRef;
        this.clinica=Tclinica;
        this.telefono=Ttelefono;
        this.codigoPostal=Tcp;
        this.nss=Tnss;
        listaSolicitud = new ArrayList<>(Datos.medsGuardados);
        Datos.limpiarLista();



    }
    ArrayList<Medicamento> getMeds(){
        return listaSolicitud;
    }
    String getDireccion(){
        return this.direccion;
    }
    String getMunicipioLocalidad(){
        return this.municipioLocalidad;
    }
    int getCodigoPostal(){
        return this.codigoPostal;
    }
    long getTelefono(){
        return this.telefono;
    }
    String getReferencia(){
        return this.referencia;
    }
    public int getIndex(String name){
        if(listaSolicitud==null) return -1;
        for(int i=0;i<listaSolicitud.size();i++){
            if(Objects.equals(listaSolicitud.get(i).nombre, name)){
                return i;
            }
        }
        return -1;
    }

}
