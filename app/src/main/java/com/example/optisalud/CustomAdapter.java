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
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String>{

    private ArrayList<String> dataList;
    private TextView texto;
    private ListView lista;

    public CustomAdapter(Context context, ArrayList<String> dataList,ListView lista ,TextView text) {
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
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el texto del elemento actual
                int posicion = Datos.getIndex(Datos.medsGuardados.get(position).nombre);
                Datos.medsGuardados.get(posicion).aumentarPedido();
                String texto=Datos.medsGuardados.get(position).nombre+" cantidad: "+Datos.medsGuardados.get(position).getCantPedida();
                dataList.set(position, texto);

                notifyDataSetChanged();
            }
        });

        // Configurar el listener del segundo botón
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el texto del elemento actual
                String name=Datos.medsGuardados.get(position).nombre;
                int posicion = Datos.getIndex(name);

                if(Datos.medsGuardados.get(posicion).uunicoMed()) {
                    Datos.medsGuardados.remove(Datos.medsGuardados.get(posicion));
                    reImprimir();
                }

                else {
                    Datos.medsGuardados.get(posicion).disminuirPedido();
                    String texto=Datos.medsGuardados.get(position).nombre+" cantidad: "+Datos.medsGuardados.get(position).getCantPedida();
                    dataList.set(position, texto);
                }


                notifyDataSetChanged();
            }
        });

        return convertView;
    }
    private void reImprimir(){
        if(Datos.medsGuardados.isEmpty()){
            lista.setVisibility(View.GONE);
            texto.setVisibility(View.VISIBLE);
        }
        dataList.clear();
        for (Medicamento med : Datos.medsGuardados) {
            String texto = med.nombre + " cantidad: " + med.getCantPedida();
            dataList.add(texto);
        }


    }
}
