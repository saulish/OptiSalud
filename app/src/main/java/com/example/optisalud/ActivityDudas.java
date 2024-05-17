package com.example.optisalud;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ActivityDudas extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dudas);



        expandableListView = findViewById(R.id.expandableListView);
        prepareListData();
        listAdapter = new com.example.optisalud.ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding header data
        listDataHeader.add("¿Cómo puedo pedir médicamentos a domicilio?");
        listDataHeader.add("¿Que pasa si no se encuentra en médicamento que necesito?");
        listDataHeader.add("¿Cómo escaneo mi receta?");
        listDataHeader.add("¿Qué pasa si cambié de domicilio?");

        // Adding child data
        List<String> duda1 = new ArrayList<>();
        duda1.add("Solución a la duda 1.");

        List<String> duda2 = new ArrayList<>();
        duda2.add("El sistema maneja una lista de espera por lo que si no contamos con alguno de los médicamentos de su receta se le notificará y se le enviará una vez que este vuelva a esta disponible.");

        List<String> duda3 = new ArrayList<>();
        duda3.add("Solución a la duda 3.");
        List<String> duda4 = new ArrayList<>();
        duda4.add("Solución a la duda 4.");

        listDataChild.put(listDataHeader.get(0), duda1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), duda2);
        listDataChild.put(listDataHeader.get(2), duda3);
        listDataChild.put(listDataHeader.get(3), duda4);
    }


}
