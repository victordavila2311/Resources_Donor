package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Beneficiario extends AppCompatActivity {
    Button reenviar, mapaB, aDon, pDon, rDon, datosB;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiario);

        reenviar = findViewById(R.id.verificacion);
        mapaB = findViewById(R.id.button9);
        aDon = findViewById(R.id.actuDon);
        pDon = findViewById(R.id.penDon);
        rDon = findViewById(R.id.recDon);
        datosB = findViewById(R.id.datosBB);

        fAuth = FirebaseAuth.getInstance();

        FirebaseUser fuser = fAuth.getCurrentUser();
        if(!(fuser.isEmailVerified())){
            reenviar.setVisibility(View.VISIBLE);
            mapaB.setVisibility(View.GONE);
            aDon.setVisibility(View.GONE);
            pDon.setVisibility(View.GONE);
            rDon.setVisibility(View.GONE);
            datosB.setVisibility(View.GONE);
            reenviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Beneficiario.this, "SE ENVIO UN EMAIL DE VERIFICACION", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag","Fallo" + e.getMessage());
                        }
                    });
                }
            });
        }else{
            reenviar.setVisibility(View.GONE);
            mapaB.setVisibility(View.VISIBLE);
            aDon.setVisibility(View.VISIBLE);
            pDon.setVisibility(View.VISIBLE);
            rDon.setVisibility(View.VISIBLE);
            datosB.setVisibility(View.VISIBLE);
        }

    }

    public void irMapaD(View view){
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
