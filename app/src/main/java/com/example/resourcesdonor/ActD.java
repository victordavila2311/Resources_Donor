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
                    String ver = u.getVerificado();
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

    public void volver(View view){
        Intent vol = new Intent(this, Beneficiario.class);
        startActivity(vol);
    }
}
