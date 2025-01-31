package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * este activity le muestra al beneficiario ya verificado por los administradores las distintas <br/>
 * opciones disponibles a su disposicion
 * @author victor manuel davila 1001218585
 * @version 1.0
 */

public class Donador extends AppCompatActivity {
    Button reenviar, mapaB, penB, donB, datosB, misD;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    /**
     * se revisa si el usuario ya verifico su correo por medio del Firebase Authentication API <br/>
     * y se configuran las opciones visibles
     * @param savedInstanceState -unused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donador);
        reenviar = findViewById(R.id.verificacion);
        mapaB = findViewById(R.id.button);
        penB = findViewById(R.id.pendB);
        donB = findViewById(R.id.donacionDB);
        datosB = findViewById(R.id.datosDB);
        misD = findViewById(R.id.button12);

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        FirebaseUser fuser = fAuth.getCurrentUser();

        if(!(fuser.isEmailVerified())){
            reenviar.setVisibility(View.VISIBLE);
            mapaB.setVisibility(View.GONE);
            penB.setVisibility(View.GONE);
            donB.setVisibility(View.GONE);
            datosB.setVisibility(View.GONE);
            misD.setVisibility(View.GONE);
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
            penB.setVisibility(View.VISIBLE);
            donB.setVisibility(View.VISIBLE);
            datosB.setVisibility(View.VISIBLE);
            misD.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Esta funcion envia al usuario a el mapa para ver otros centros de donaciones
     * @param view -unused
     * @see MapsActivity
     */
    public void irMapaD(View view){
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    /**
     * Esta funcion envia al usuario a las donaciones que no ha entregado
     * @param view -unused
     * @see listaDonaciones
     */
    public void pendientes(View view) {
        Intent intent = new Intent(getApplicationContext(), listaDonaciones.class);
        intent.putExtra("tipo", "donadorPen");
        startActivity(intent);
    }

    /**
     * Esta funcion envia al usuario a las donaciones que ya entrego
     * @param view -unused
     * @see listaDonaciones
     */
    public void realizadas(View view){
        Intent intent = new Intent(getApplicationContext(), listaDonaciones.class);
        intent.putExtra("tipo", "donadorRealizado");
        startActivity(intent);
    }

    /**
     * Esta funcion en via al usuario a la Activity desde donde puede realizar donaciones
     * @param view -unused
     * @see RealizarDonacion
     */
    public void irD(View view){
        Intent intent = new Intent(getApplicationContext(),RealizarDonacion.class);
        startActivity(intent);
    }

    /**
     * Esta funcion envia al usuario a un Activity donde se muestran los datos del usuario
     * @param view -unused
     * @see MisDatos
     */
    public void irDatos(View view) {
        Intent intent = new Intent(getApplicationContext(), MisDatos.class);
        intent.putExtra("tipo", "Donador");
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
