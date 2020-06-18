package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
 * En esta Activity se realiza la verificacio de los beneficirarios
 * @author victor manuel davila 1001218585
 * @version 1.0
 */

public class Verificar extends AppCompatActivity {
    EditText et;
    Button buscar, verificar;
    TextView l;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    /**
     * Onclicklistener que ejecuta una busqueda adaptada a bases de datos no relacionales <br/>
     * convirtiendo el JSON en un objeto UsuariosClass<br/>
     * Boton que ejecuta la actualizacion de la base de datos Firebase <br/>
     * realizando un algoritmo para simula lo que seria en SQL un <br/>
     * SELECT FROM * WHERE de = 'correo' <br/>
     * con el dRef.update(map) se pasa el hashmap y se revisan y actualizan <br/>
     * para revisar los requisitos se convierte el JSON en un objeto UsuariosClass <br/>
     * como un UPDATE 'usuarios' SET verificado = Si <br/>
     * solo los campos nombrados en el hashmap
     * @see UsuariosClass
     * @see UsuariosClass
     * @see android.view.View.OnClickListener
     * @param savedInstanceState -unused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar);
        et = findViewById(R.id.busCorreo);
        buscar = findViewById(R.id.buscarBo);
        verificar = findViewById(R.id.verifU);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        l = findViewById(R.id.datosU);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference user = fStore.collection("usuarios");
                user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String resultado = "";
                        for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                            UsuariosClass u = qds.toObject(UsuariosClass.class);
                            String id=qds.getId();
                            String c = et.getText().toString();
                            if(u.getCorreo().equals(c)){
                                resultado +="nombre: "+ u.getNombre() +"\napellido: "+u.getApellido()+"\ncorreo: "+u.getCorreo()+
                                        "\ncelular: "+u.getCelular()+"\nid: "+id+"\n--------------------------------------------------\n";
                            }
                        }
                        l.setText(resultado);

                    }
                });

            }
        });


        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference user = fStore.collection("usuarios");
                user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String resultado = "";
                        for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                            UsuariosClass u = qds.toObject(UsuariosClass.class);
                            String c = et.getText().toString();
                            if(u.getCorreo().equals(c)){
                                String id=qds.getId();
                                DocumentReference dRef = FirebaseFirestore.getInstance()
                                        .collection("usuarios")
                                        .document(id);
                                Map<String,Object> map = new HashMap<>();
                                map.put("Verificado","Si");
                                dRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Verificar.this, "Actualizado", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Verificar.this, "Fallo por "+e, Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
                    }
                });
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
