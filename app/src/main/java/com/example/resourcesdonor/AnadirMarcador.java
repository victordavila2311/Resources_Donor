package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * Esta activity genera es la forma de los administradores para añadir marcadores al mapa
 */

public class AnadirMarcador extends AppCompatActivity {
    Button anadir;
    EditText des, la, lon;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_marcador);
        des = findViewById(R.id.descripcion);
        la = findViewById(R.id.latitud_et);
        lon = findViewById(R.id.longitud_et);

        fAuth = FirebaseAuth.getInstance();
        anadir = findViewById(R.id.anadirB);
        /**
         * funcion del boton anadir con la API FireStore en este caso no<br/>
         * se usa un Hashmap sino un objeto DireccionesClass
         * @see DireccionesClass
         * @see android.view.View.OnClickListener
         */
        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore = FirebaseFirestore.getInstance();
                CollectionReference lats = fStore.collection("direcciones");
                String lat = la.getText().toString();
                String lo = lon.getText().toString();
                String desc = des.getText().toString();
                DireccionesClass dir = new DireccionesClass(lat,lo,desc);
                lats.add(dir).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"añadido", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"fallo"+e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * En esta funcion se devuelve al admin a la
     * @param view -unused
     * @see admin
     */
    public void Volver(View view){
        Intent intent = new Intent(getApplicationContext(),admin.class);
        startActivity(intent);
    }


}
