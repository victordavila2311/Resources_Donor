package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * Esta Activity es el manejo logico del formulario de Registro de Donadores con Firebase
 */

public class registroD extends AppCompatActivity {
    EditText mNombre, mApellido, mCorreo, mContrasena, mDireccion, mCel;
    Button mBotonRD;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_d);

        mNombre = findViewById(R.id.nombre);
        mApellido = findViewById(R.id.apellido);
        mCorreo = findViewById(R.id.correo);
        mContrasena = findViewById(R.id.password);
        mDireccion = findViewById(R.id.direccion);
        mCel = findViewById(R.id.celular);
        mBotonRD = findViewById(R.id.botonRD);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mBotonRD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = mCorreo.getText().toString().trim();
                String password = mContrasena.getText().toString().trim();
                final String nombre = mNombre.getText().toString();
                final String apellido = mApellido.getText().toString();
                final String direccion = mDireccion.getText().toString();
                final String celular = mCel.getText().toString();


                if(correo.isEmpty()){
                    mCorreo.setError("ingrese un correo");
                    return;
                }
                if(password.isEmpty()){
                    mContrasena.setError("ingrese una contraseña");
                }
                if(password.length()<8){
                    mContrasena.setError("la contraseña debe tener minimo 8 caracteres");
                }
                /**
                 * En esta seccion se usa Firebase Authentication para realizar el nuevo usuario <br/>
                 * luego se conecta el id con FireStore y se agrega un nuevo documento <br/>
                 * a la coleccion usuarios en forma de HashMap
                 */
                fAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(registroD.this, "SE ENVIO UN EMAIL DE VERIFICACION", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("tag","Fallo" + e.getMessage());
                                }
                            });

                            Toast.makeText(registroD.this,"usuario creado", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("usuarios").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Nombre",nombre);
                            user.put("Apellido", apellido);
                            user.put("Correo", correo);
                            user.put("Direccion", direccion);
                            user.put("DireccionD", null);
                            user.put("Cant. Donaciones", 0);
                            user.put("Celular", celular);
                            user.put("Tipo", "Donador");
                            user.put("Verificado", "Si");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onSuccess: usuario creado para"+userID);
                                }
                            });
                            Intent registradoB = new Intent(getApplicationContext(),Donador.class);
                            startActivity(registradoB);
                        }else{
                            Toast.makeText(registroD.this,"error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }

    /**
     * Esta funcion devuelve al usuario a la pagina inicial
     * @param view -unused
     * @see MainActivity
     */
    public void volverPrincipal(View view){
        Intent volver_principal = new Intent(this,MainActivity.class);
        startActivity(volver_principal);
    }

    /**
     * Esta funcion envia al usuario al Activity de LOGIN
     * @param view -unused
     * @see Login
     */
    public void irLogin(View view){
        Intent ir_Login = new Intent(this,Login.class);
        startActivity(ir_Login);
    }
}
