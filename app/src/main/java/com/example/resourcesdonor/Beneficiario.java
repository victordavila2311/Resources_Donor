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

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * este activity le muestra al beneficiario ya verificado por los administradores las distintas <br/>
 * opciones disponibles a su disposicion
 */

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
        /**
         * se revisa si el usuario ya verifico su correo <br/>
         * por medio del Firebase Authentication API <br/>
         * y se configuran las opciones visibles
         */
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

    /**
     * Esta funcion envia al usuario a el mapa para ver los centros de donaciones
     * @param view -unused
     * @see MapsActivity
     */
    public void irMapaD(View view){
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    /**
     * Esta funcion envia al usuario a las donaciones que no le han entregado
     * @param view -unused
     * @see listaDonaciones
     */
    public void pendientes(View view) {
        Intent intent = new Intent(getApplicationContext(), listaDonaciones.class);
        intent.putExtra("tipo", "beneficiarioPen");
        startActivity(intent);
    }

    /**
     * Esta funcion envia al usuario a las donaciones que ya le entregaron
     * @param view -unused
     * @see listaDonaciones
     */
    public void recibidas(View view) {
        Intent intent = new Intent(getApplicationContext(), listaDonaciones.class);
        intent.putExtra("tipo", "beneficiarioRecibidas");
        startActivity(intent);
    }

    /**
     * Esta funcion envia al usuario a un Activity donde se actualizan los
     * @param view -unused
     * @see ActD
     */
    public void irAct(View view){
        Intent intent = new Intent(getApplicationContext(), ActD.class);
        startActivity(intent);
    }

    /**
     * se realiza el Log-out por medio de la API de Firebase y se vuelve a la pagina principal
     * @param view -unused
     * @see MainActivity
     */
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
