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

/**
 * Esta Activity es multiproposito segun desde donde venga ya que lo que hace es mostrar una lista, <br/>
 * pero lo que se muestra en la lista depende de como se llegaron hasta el Activity
 * @author victor manuel davila 1001218585
 * @version 1.0
 */

public class listaDonaciones extends AppCompatActivity {
    String tipo;
    TextView tv;
    String volver;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userID;
    String correo;
    CollectionReference user = fStore.collection("usuarios");

    /**
     * se genera una funcion que emula en el API de una base de datos no relacional como Firebase <br/>
     * donde luego de obtener el JSON este se convierte en un objeto UsuariosClass <br/>
     * lo que seria una busqueda en SQL de forma SELECT * FROM 'usuarios' WHERE ID = id <br/>
     * y se consigue el correo que sera necesario para relacionar las siguientes funciones
     * Esta funcion actua segun de donde venga el usuario y muestra distinta informacion en las listas <br/>
     * se convierte el JSON en un objeto Donacionesclass <br/>
     * para despues filtrar en una busqueda que emula SQL
     * @see DonacionesClass
     * @see UsuariosClass
     * @param savedInstanceState -unused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_donaciones);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        tv = findViewById(R.id.listaTxt);
        tipo = getIntent().getStringExtra("tipo");

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

    /**
     * Esta funcion devuelve al usuario a la pagina de la que vienen (ya sea Benficiario o Donador)
     * @param view -unused
     * @see Donador
     * @see Beneficiario
     */
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
