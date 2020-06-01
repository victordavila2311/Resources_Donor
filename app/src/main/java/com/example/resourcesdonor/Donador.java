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

public class Donador extends AppCompatActivity {
    Button reenviar, mapaB;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donador);
        reenviar = findViewById(R.id.verificacion);
        mapaB = findViewById(R.id.button);
        fAuth = FirebaseAuth.getInstance();

        FirebaseUser fuser = fAuth.getCurrentUser();
        if(!(fuser.isEmailVerified())){
            reenviar.setVisibility(View.VISIBLE);
            mapaB.setVisibility(View.GONE);
            reenviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Donador.this, "SE ENVIO UN EMAIL DE VERIFICACION", Toast.LENGTH_SHORT).show();
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
