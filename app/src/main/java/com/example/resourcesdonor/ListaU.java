package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * Esta Activity le muestra a los administradores una lista con los usuarios no verificados
 */
public class ListaU extends AppCompatActivity {
    TextView l;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_u);
        fAuth = FirebaseAuth.getInstance();
        fStore =  FirebaseFirestore.getInstance();
        l = findViewById(R.id.lista_b);
        CollectionReference user = fStore.collection("usuarios");
        /**
         * En esta seccion se realiza una busqueda dentro de los decumentos de la Firestore <br/>
         * luego los resultados de esta busqueda se convierten los JSON a objetos UsuariosClass
         * @see UsuariosClass
         */
        user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String resultado = "";
                for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                    UsuariosClass u = qds.toObject(UsuariosClass.class);
                    String ver = u.getVerificado();
                    String id=qds.getId();
                    if(ver.equals("No")){
                        resultado +="nombre: "+ u.getNombre() +"\napellido: "+u.getApellido()+"\ncorreo: "+u.getCorreo()+
                                "\ncelular: "+u.getCelular()+"\nid: "+id+"\n--------------------------------------------------\n";
                    }
                }
                l.setText(resultado);
            }
        });
    }

    /**
     * Esta funcion devuelve al usuario al menu de opciones de administrador
     * @param view -unused
     * @see admin
     */
    public void volver(View view){
        Intent vol = new Intent(this,admin.class);
        startActivity(vol);
    }
}
