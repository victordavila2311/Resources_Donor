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
 * Este es el Activity usado para actualizar las donaciones recibidas
 * por medio de Firebase Firestore
 * @author victor manuel davila 1001218585
 * @version 1.0
 */

public class ActD extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button b, recibido;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID, correo;
    /**
     * Se identifica el correo del usuario a travez del ID del usuario actua.<br/>
     * se convierte el JSON en un objeto UsuarioClass<br/>
     * Onclicklistener que ejecuta una busqueda adaptada a bases de datos no relacionales <br/>
     * convirtiendo el JSON en un objeto DonacionesClass
     * @see DonacionesClass
     * @see android.view.View.OnClickListener
     * @see UsuariosClass
     * @param savedInstanceState -unused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_d);
        b = findViewById(R.id.BBUSCAR);
        recibido = findViewById(R.id.button14);
        et = findViewById(R.id.editText3);
        tv = findViewById(R.id.textView10);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        CollectionReference us = fStore.collection("usuarios");
        userID = fAuth.getCurrentUser().getUid();

        us.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                    UsuariosClass u = qds.toObject(UsuariosClass.class);
                    String id=qds.getId();
                    if(id.equals(userID)){
                        correo = u.getCorreo();
                    }
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference user = fStore.collection("donaciones");
                user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String resultado = "";
                        for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                            DonacionesClass d = qds.toObject(DonacionesClass.class);
                            String c = et.getText().toString();
                            if(d.getDe().equals(c)){
                                if(d.getPara().equals(correo)){
                                    resultado +="De: "+ d.getDe() +"\ndescripcion: "+d.getDescripcion()+
                                            "\n--------------------------------------------------\n";
                                }
                            }
                        }
                        tv.setText(resultado);
                    }
                });
            }
        });
    }

    /**
     * Boton que ejecuta la actualizacion de la base de datos Firebase <br/>
     * realizando un algoritmo para simula lo que seria en SQL un <br/>
     * SELECT FROM * WHERE de = 'correoorigen' AND para = 'correodestino' <br/>
     * con el dRef.update(map) se pasa el hashmap y se revisan y actualizan <br/>
     * para revisar los requisitos se convierte el JSON en un objeto DonacionesClass <br/>
     * como un UPDATE 'donaciones' SET recibido = Si <br/>
     * solo los campos nombrados en el hashmap
     * @see DonacionesClass
     * @param view -unused
     */
    public void Actualizar(View view){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        et.findViewById(R.id.editText3);
        CollectionReference user= fStore.collection("donaciones");
        user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                    DonacionesClass d = qds.toObject(DonacionesClass.class);
                    String c = et.getText().toString();
                    if(d.getDe().equals(c)){
                        if(d.getPara().equals(correo)){
                            String id = qds.getId();
                            DocumentReference dRef = FirebaseFirestore.getInstance()
                                    .collection("donaciones")
                                    .document(id);
                            Map<String,Object> map = new HashMap<>();
                            map.put("recibido","Si");
                            dRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ActD.this, "Actualizado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ActD.this, "Fallo por "+e, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    /**
     * boton para volver al Activity anterior
     * @param view -unused
     * @see Beneficiario
     */
    public void volver(View view){
        Intent vol = new Intent(this, Beneficiario.class);
        startActivity(vol);
    }
}
