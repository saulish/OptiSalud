package com.example.optisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.google.android.gms.maps.model.LatLngBounds;

import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class buscarMedActivity extends AppCompatActivity {
    private AutoCompleteTextView entradaNombre;
    private TextView nombre;
    private TextView codigo;
    private TextView cantidad;
    private TextView setClinica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_med);

        entradaNombre=findViewById(R.id.medBuscar);
        nombre=findViewById(R.id.nombreObtenido);
        codigo=findViewById(R.id.codigoObtenido);
        cantidad=findViewById(R.id.cantidadObtenida);
        setClinica=findViewById(R.id.textView15);



        String actual= (String) setClinica.getText();
        setClinica.setText(actual+=Datos.getInstance().getClinica());


        pedirMedCodigos(entradaNombre);



    }
    private void pedirMedCodigos(AutoCompleteTextView entrada){
        helperFB conexion=new helperFB(this,entrada);
        conexion.getMedicamentosCodigos();
    }

    public void buscarMed(View view){
        String medicamento = entradaNombre.getText().toString();
        if(medicamento.equals("")){
            nombre.setText("INGRESA ALGO");
            return;
        }
        medicamento = medicamento.toLowerCase();
        medicamento = medicamento.substring(0, 1).toUpperCase() + medicamento.substring(1);

        String clinica =Datos.getInstance().getClinica();

        helperFB conexion=new helperFB(this);
        conexion.verificarCodigo(medicamento,clinica,nombre,codigo,cantidad);


    }
    private Medicamento crearMed(){
        String nameMedicamento=nombre.getText().toString();
        String clinica=Datos.getInstance().getClinica();
        String cant=cantidad.getText().toString();
        int cantClinica=Integer.parseInt(cantidad.getText().toString());
        int cantPedida=0;
        Medicamento med=new Medicamento(nameMedicamento,clinica,cantClinica,cantPedida);
        return med;
    }
    private boolean validarCasilla(){
        if(nombre.getText().equals("") || nombre.getText().equals("Este medicamento no existe")){
            Toast.makeText(this, "Busca un medicamento correctamente", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void addMed(View view){
        if(validarCasilla()){
            return;
        }
        Medicamento med=crearMed();
        int posicion = Datos.getIndex(med.nombre);
        if(posicion>-1){
            Datos.medsGuardados.get(posicion).aumentarPedido();
            Toast.makeText(this, nombre.getText()+" aumentado", Toast.LENGTH_SHORT).show();

        }else{
            med.aumentarPedido();
            Datos.medsGuardados.add(med);
            Toast.makeText(this, nombre.getText()+" añadido correctamente", Toast.LENGTH_SHORT).show();

        }



    }
    public void quitarMed(View view){
        if(validarCasilla()){
            return;
        }
        int index=Datos.getIndex(nombre.getText().toString());
        if(index==-1){
            Toast.makeText(this,nombre.getText()+" no esta en la lista",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Datos.medsGuardados.get(index).uunicoMed()) {
            Datos.medsGuardados.remove(index);
            Toast.makeText(this, nombre.getText().toString()+" eliminado",Toast.LENGTH_SHORT).show();
        }

        else{
            Datos.medsGuardados.get(index).disminuirPedido();
            Toast.makeText(this, nombre.getText().toString()+" disminuido",Toast.LENGTH_SHORT).show();

        }


    }

}