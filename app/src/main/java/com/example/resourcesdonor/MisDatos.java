package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * En este activity se muestran los datos del usuario
 * @author victor manuel davila 1001218585
 * @version 1.0
 */

public class MisDatos extends AppCompatActivity {

    TextView tv, correo, direccionD;
    EditText nombre, apellido, celular, direccion;
    String tipo, userID;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    CollectionReference user = fStore.collection("usuarios");

    /**
     * En esta seccion se realiza una busqueda dentro de los decumentos de la Firestore <br/>
     * luego los resultados de esta busqueda se convierten los JSON a objetos UsuariosClass </br>
     * y desde con los getters se ubica en los campos correspondientes
     * @param savedInstanceState -unused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);
        tv = findViewById(R.id.textView18);
        direccionD = findViewById(R.id.direccion_d_u);
        nombre = findViewById(R.id.nombre_u);
        apellido = findViewById(R.id.apellido_u);
        correo = findViewById(R.id.correo_u);
        celular = findViewById(R.id.celular_u);
        direccion = findViewById(R.id.direccion_u);
        tipo = getIntent().getStringExtra("tipo");
        if(tipo.equals("Beneficiario")){
            tv.setVisibility(View.VISIBLE);
            direccionD.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
            direccionD.setVisibility(View.GONE);
        }


        userID = fAuth.getCurrentUser().getUid();
        user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                    UsuariosClass u = qds.toObject(UsuariosClass.class);
                    String id=qds.getId();
                    if(id.equals(userID)){
                        nombre.setText(u.getNombre());
                        apellido.setText(u.getApellido());
                        correo.setText(u.getCorreo());
                        celular.setText(u.getCelular());
                        direccion.setText(u.getDireccion());
                        if(tipo.equals("Beneficiario")){
                            direccionD.setText(u.getDireccionD());
                        }
                    }
                }
            }
        });
    }

    /**
     * Boton que ejecuta la actualizacion de la base de datos Firebase <br/>
     * realizando un algoritmo para simula lo que seria en SQL un <br/>
     * SELECT * FROM usuarios WHERE id = userID <br/>
     * con el dRef.update(map) se pasa el hashmap y se revisan y actualizan <br/>
     * solo los campos nombrados en el hashmap
     * @param view -unused
     */
    public void Actualizar(View view){
        user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                    UsuariosClass u = qds.toObject(UsuariosClass.class);
                    String id = qds.getId();
                    if(id.equals(userID)){
                        DocumentReference dRef = FirebaseFirestore.getInstance()
                                .collection("usuarios")
                                .document(id);
                        Map<String,Object> map = new HashMap<>();
                        map.put("Nombre",nombre.getText().toString());
                        map.put("Apellido", apellido.getText().toString());
                        map.put("Celular", celular.getText().toString());
                        map.put("Direccion", direccion.getText().toString());
                        dRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MisDatos.this, "Actualizado", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MisDatos.this, "Fallo por "+e, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * Esta funcion envia al usuario al menu de opciones dependiendo si es beneficiario o donador
     * @param view -unused
     * @see Donador
     * @see Beneficiario
     */
    public void Volver(View view){
        Intent intent;
        if(tipo.equals("Donador")){
            intent = new Intent(getApplicationContext(), Donador.class);
        }else{
            intent = new Intent(getApplicationContext(), Beneficiario.class);
        }
        startActivity(intent);
    }
}
