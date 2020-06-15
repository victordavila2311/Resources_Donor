package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class listaDonaciones extends AppCompatActivity {
    String tipo;
    TextView tv;
    String volver;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userID;
    String correo;
    CollectionReference user = fStore.collection("usuarios");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_donaciones);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



        tv = findViewById(R.id.listaTxt);
        tipo = getIntent().getStringExtra("tipo");

        userID = fAuth.getCurrentUser().getUid();
        String resultado = "";

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

        CollectionReference donaciones = fStore.collection("donaciones");

        if(tipo.equals("donadorPen")){
            donaciones.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String resultado = "";
                    for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                        DonacionesClass u = qds.toObject(DonacionesClass.class);
                        String de = u.getDe();
                        String ver = u.getRecibido();
                        System.out.println(de);
                        if(de.equals(correo)){
                            if(ver.equals("No")){
                                resultado +="Para: "+ u.getPara() +"\nDescripción: "+u.getDescripcion() +
                                        "\n--------------------------------------------------\n";
                            }
                        }
                    }
                    tv.setText(resultado);
                }
            });
            volver = "Donador";
        }else if(tipo.equals("donadorRealizado")){
            donaciones.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String resultado = "";
                    for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                        DonacionesClass u = qds.toObject(DonacionesClass.class);
                        String de = u.getDe();
                        String ver = u.getRecibido();
                        if(de.equals(correo)){
                            if(ver.equals("Si")){
                                resultado +="Para: "+ u.getPara() +"\nDescripción: "+u.getDescripcion() +
                                        "\n--------------------------------------------------\n";
                            }
                        }
                    }
                    tv.setText(resultado);
                }
            });
            volver = "Donador";
        }else if(tipo.equals("beneficiarioPen")){
            donaciones.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String resultado = "";
                    for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                        DonacionesClass u = qds.toObject(DonacionesClass.class);
                        String para = u.getPara();
                        String ver = u.getRecibido();
                        if(para.equals(correo)){
                            if(ver.equals("No")){
                                resultado +="De: "+ u.getDe() +"\nDescripción: "+u.getDescripcion() +
                                        "\n--------------------------------------------------\n";
                            }
                        }
                    }
                    tv.setText(resultado);
                }
            });
            volver = "Beneficiario";
        }else if(tipo.equals("beneficiarioRecibidas")){
            donaciones.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String resultado = "";
                    for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                        DonacionesClass u = qds.toObject(DonacionesClass.class);
                        String para = u.getPara();
                        String ver = u.getRecibido();
                        if(para.equals(correo)){
                            if(ver.equals("Si")){
                                resultado +="De: "+ u.getDe() +"\nDescripción: "+u.getDescripcion() +
                                        "\n--------------------------------------------------\n";
                            }
                        }
                    }
                    tv.setText(resultado);
                }
            });
            volver = "Beneficiario";
        }

    }





    public void Volver(View view){
        Intent intent;
        if(volver.equals("Donador")){
            intent = new Intent(getApplicationContext(),Donador.class);
        }else{
            intent = new Intent(getApplicationContext(),Beneficiario.class);
        }
        startActivity(intent);
    }
}
