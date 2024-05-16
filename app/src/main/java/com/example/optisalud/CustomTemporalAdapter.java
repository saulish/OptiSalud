package com.example.optisalud;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomTemporalAdapter extends ArrayAdapter<String>{

    private ArrayList<String> dataList;
    private TextView texto;
    private ListView lista;

    public CustomTemporalAdapter(Context context, ArrayList<String> dataList, ListView lista , TextView text) {
        super(context, 0, dataList);
        this.dataList = dataList;
        this.texto=text;
        this.lista=lista;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listameds, parent, false);
        }

        // Obtener el elemento de la lista en la posición actual
        final String item = getItem(position);

        // Obtener referencias a los elementos de la vista
        TextView textView = convertView.findViewById(R.id.list_nameMedicamento);
        Button button1 = convertView.findViewById(R.id.list_buttonAdd);
        Button button2 = convertView.findViewById(R.id.list_buttonRemove);

        // Asignar el texto al TextView
        textView.setText(item);

        // Configurar el listener del primer botón
        button1.setOnClickListener(new View.OnClickListener() {//AUMENTAR
            @Override
            public void onClick(View v) {
                // Cambiar el texto del elemento actual
                int posicion = Datos.getSolMed().getIndex(Datos.getSolMed().getMeds().get(position).nombre);
                Datos.getSolMed().getMeds().get(posicion).aumentarPedido();
                String texto=Datos.getSolMed().getMeds().get(position).nombre+" cantidad: "+Datos.getSolMed().getMeds().get(position).getCantPedida();
                dataList.set(position, texto);

                notifyDataSetChanged();
            }
        });

        // Configurar el listener del segundo botón
        button2.setOnClickListener(new View.OnClickListener() {//DISMINUIR
            @Override
            public void onClick(View v) {
                // Cambiar el texto del elemento actual
                String name=Datos.getSolMed().getMeds().get(position).nombre;
                int posicion = Datos.getSolMed().getIndex(name);

                if(Datos.getSolMed().getMeds().get(posicion).uunicoMed()) {
                    Datos.getSolMed().getMeds().remove(Datos.getSolMed().getMeds().get(posicion));
                    reImprimir();
                }

                else {
                    if(Datos.getSolMed().getMeds().get(posicion).getCantPedida()==1){
                        //VALIDAR PARA UNA MANERA DE QUE NO SE PUEDA SIMPLEMENTE LA SOLICITUD VACIA
                        return;
                    }
                    Datos.getSolMed().getMeds().get(posicion).disminuirPedido();
                    String texto=Datos.getSolMed().getMeds().get(position).nombre+" cantidad: "+Datos.getSolMed().getMeds().get(position).getCantPedida();
                    dataList.set(position, texto);
                }


                notifyDataSetChanged();
            }
        });

        return convertView;
    }
    private void reImprimir(){
        if(Datos.getSolMed().getMeds().isEmpty()){
            lista.setVisibility(View.GONE);
            texto.setVisibility(View.VISIBLE);
        }
        dataList.clear();
        for (Medicamento med : Datos.getSolMed().getMeds()) {
            String texto = med.nombre + " cantidad: " + med.getCantPedida();
            dataList.add(texto);
        }


    }
}
