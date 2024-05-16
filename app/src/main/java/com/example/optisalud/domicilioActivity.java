package com.example.optisalud;

import static com.example.optisalud.R.id.vistaMeds;
import static com.example.optisalud.R.id.vistaTextMeds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class domicilioActivity extends AppCompatActivity {

    private EditText direccion;
    private EditText colonia;
    private EditText codigoPostal;
    private EditText telefonoVista;
    private EditText referenciaVista;

    private TextView curpVista;
    private TextView nssVista;
    private TextView clinicaVista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);
        curpVista=findViewById(R.id.curpView);
        curpVista.setText(Datos.getInstance().getCurp());

        nssVista=findViewById(R.id.nssView);
        nssVista.setText(Datos.getInstance().getNss());

        clinicaVista=findViewById(R.id.clinicaView);
        clinicaVista.setText(Datos.getInstance().getClinica());

        direccion=findViewById(R.id.textDIreccion);
        colonia=findViewById(R.id.coloniaText);
        codigoPostal=findViewById(R.id.codigoPostalText);
        telefonoVista=findViewById(R.id.textTelefono);
        referenciaVista=findViewById(R.id.textReferencia);
        ListView listView = findViewById(R.id.vistaMeds);
        TextView emptyTextView = findViewById(R.id.vistaTextMeds);
        CustomAdapter adapter = null;

        if (Datos.medsGuardados.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            adapter=new CustomAdapter(this, getLista(Datos.medsGuardados),listView,emptyTextView);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }

        if (Datos.getSolMed() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Se encontró una solicitud previa. ¿Deseas cargarla?")
                    .setTitle("Confirmación")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            listView.setVisibility(View.VISIBLE);
                            emptyTextView.setVisibility(View.GONE);
                            cargarSol();
                            CustomTemporalAdapter cA=new CustomTemporalAdapter(getApplicationContext(),getLista(Datos.getSolMed().getMeds()),listView,emptyTextView);
                            listView.setAdapter(cA);



                            Toast.makeText(getApplicationContext(), "Se cargó una solicitud previa", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Acción al cancelar
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }



        Places.initialize(getApplicationContext(), "AIzaSyBtabHB_xWUdM9LGRBVTw4foSYkaWCNSLE");


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.editDireccion);
        LatLngBounds guadalajaraBounds = new LatLngBounds(
                new LatLng(20.5965, -103.2891),
                new LatLng(20.7224, -103.1973));

        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(guadalajaraBounds));

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));

        // Configura el listener de selección de lugar
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String fullDireccion=place.getAddress();
                String[] partes = fullDireccion.split(", ");
                direccion.setText(fullDireccion.split(", ")[0]);
                String[] codigo=partes[2].split(" ");
                try {
                    int numero = Integer.parseInt(codigo[0]);
                    codigoPostal.setText(codigo[0]);
                } catch (NumberFormatException e) {
                    codigoPostal.setText(partes[1].split(" ")[0]);
                }


                if(Objects.equals(partes[3], "Jal.")){
                    colonia.setText("Jalisco");
                }else{
                    colonia.setText(partes[3]);
                }

            }
            @Override
            public void onError(@NonNull Status status) {
                autocompleteFragment.setText("Ingresa una direccion");
                direccion.setText("Selecciona una direccion");
            }
        });
    }
    private void cargarSol(){
        direccion.setText(Datos.getSolMed().getDireccion());
        colonia.setText(Datos.getSolMed().getMunicipioLocalidad());
        codigoPostal.setText(String.valueOf(Datos.getSolMed().getCodigoPostal()));
        telefonoVista.setText(String.valueOf(Datos.getSolMed().getTelefono()));
        referenciaVista.setText(Datos.getSolMed().getReferencia());

    }

    private ArrayList<String> getLista(ArrayList<Medicamento> lista) {
        int n=lista.size();
        ArrayList<String> a = new ArrayList<String>(n);

        for (int i = 0; i < n; i++) {
            a.add(lista.get(i).nombre+" cantidad: "+lista.get(i).getCantPedida());
        }

        return a;
    }
    public void revisarFomrulario(View view){
        if(Datos.medsGuardados.isEmpty() || referenciaVista.getText().length()==0 || telefonoVista.getText().length()==0 || codigoPostal.getText().length()==0 || colonia.getText().length()==0|| direccion.getText().length()==0 ){
            Toast.makeText(this, "Datos faltantes",Toast.LENGTH_SHORT).show();
            return;
        }

        String name=Datos.getInstance().getNombre(),clinica=Datos.getInstance().getClinica(), calle=direccion.getText().toString(),mun=colonia.getText().toString(),ref=referenciaVista.getText().toString();
        int codP=Integer.parseInt(codigoPostal.getText().toString()),numS=Integer.parseInt(Datos.getInstance().getNss());
        long tel=Long.parseLong(telefonoVista.getText().toString());
        Solicitud sol=new Solicitud(name,clinica,calle,mun,ref,tel,codP,numS);
        Datos.getInstance().setSolMed(sol);


    }

}