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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


public class registroB extends AppCompatActivity {
    EditText mNombre, mApellido, mCorreo, mContrasena, mDireccion, mDireccionD;
    Button mBotonRB;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_b);

        mNombre = findViewById(R.id.nombre);
        mApellido = findViewById(R.id.apellido);
        mCorreo = findViewById(R.id.correo);
        mContrasena = findViewById(R.id.password);
        mDireccion = findViewById(R.id.direccion);
        mDireccionD = findViewById(R.id.direcciond);
        mBotonRB = findViewById(R.id.botonRB);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        /*
        if(fAuth.getCurrentUser() != null){
            userID = fAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fStore.collection("usuarios").document(userID);
            documentReference.addSnapshotListener(registroB.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    String tipo = documentSnapshot.getString("Tipo");
                    if(tipo=="Beneficiario"){
                        Intent registradoB = new Intent(getApplicationContext(),Beneficiario.class);
                        startActivity(registradoB);
                    }else if(tipo=="Donador"){
                        Intent registradoD = new Intent(getApplicationContext(),Donador.class);
                        startActivity(registradoD);
                    }
                }
            });
        }

         */



        mBotonRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = mCorreo.getText().toString().trim();
                String password = mContrasena.getText().toString().trim();
                final String nombre = mNombre.getText().toString();
                final String apellido = mApellido.getText().toString();
                final String direccion = mDireccion.getText().toString();
                final String direccionD = mDireccionD.getText().toString();

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
                fAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(registroB.this,"usuario creado", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("usuarios").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Nombre",nombre);
                            user.put("Apellido", apellido);
                            user.put("Correo", correo);
                            user.put("Direccion", direccion);
                            user.put("DireccionD", direccionD);
                            user.put("Cant. Donaciones", 0);
                            user.put("Tipo", "Beneficiario");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onSuccess: usuario creado para"+userID);
                                }
                            });
                            Intent registradoB = new Intent(getApplicationContext(),Beneficiario.class);
                            startActivity(registradoB);
                        }else{
                            Toast.makeText(registroB.this,"error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }

    public void volverPrincipal(View view){
        Intent volver_principal = new Intent(this,MainActivity.class);
        startActivity(volver_principal);
    }

    public void irLogin(View view){
        Intent ir_Login = new Intent(this,Login.class);
        startActivity(ir_Login);
    }
}
