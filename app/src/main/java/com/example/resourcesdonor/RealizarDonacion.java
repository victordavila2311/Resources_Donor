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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * @author victor manuel davila 1001218585
 */

public class RealizarDonacion extends AppCompatActivity {
    Button b;

    EditText para, des;
    String userID, correo;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    CollectionReference user = fStore.collection("usuarios");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_donacion);
        para = findViewById(R.id.editText);
        des = findViewById(R.id.editText2);
        fAuth = FirebaseAuth.getInstance();
        b = findViewById(R.id.EnviarBot);

        userID = fAuth.getCurrentUser().getUid();

        user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                fStore = FirebaseFirestore.getInstance();
                CollectionReference don = fStore.collection("donaciones");
                DonacionesClass dir = new DonacionesClass(correo, para.getText().toString(), des.getText().toString(), "No");
                don.add(dir).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"añadido", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"fallo "+e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public void volver(View view){
        Intent vol = new Intent(this,Donador.class);
        startActivity(vol);
    }
}
