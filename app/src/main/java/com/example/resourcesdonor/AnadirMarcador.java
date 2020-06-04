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

import java.util.HashMap;
import java.util.Map;

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
        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore = FirebaseFirestore.getInstance();
                CollectionReference lats = fStore.collection("direcciones");
                String lat = la.getText().toString();
                String lo = lon.getText().toString();
                String desc = des.getText().toString();
                DireccionesClass dir = new DireccionesClass(lat,lo,desc);
                /*
                Map<String,Object> dir = new HashMap<>();
                dir.put("latitud", d.getLatitud());
                dir.put("longitud", d.getLongitud());
                dir.put("descripcion", d.getDescripcion());
                 */
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

    public void Volver(View view){
        Intent intent = new Intent(getApplicationContext(),admin.class);
        startActivity(intent);
    }


}
