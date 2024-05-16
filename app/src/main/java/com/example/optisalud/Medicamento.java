package com.example.optisalud;

public class Medicamento {
    public String nombre;
    private String clinica;
    private int cantidadClinica;
    private int cantPedida;

    Medicamento(String tName, String tClinica, int Tcc, int tcantPedida){
        this.nombre=tName;
        this.clinica=tClinica;
        this.cantidadClinica=Tcc;
        this.cantPedida=tcantPedida;

    }
    public void aumentarPedido(){
        if((cantPedida+1)>cantidadClinica) return;
        cantPedida++;
    }
    public void disminuirPedido(){
        if(cantPedida==1) return;
        cantPedida--;
    }
    public boolean uunicoMed(){
        return cantPedida==1;
    }
    public int getCantPedida(){ return cantPedida;}

}
