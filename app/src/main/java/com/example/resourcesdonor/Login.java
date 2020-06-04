package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Login extends AppCompatActivity {
    EditText mCorreo, mContrasena;
    Button mBotonLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCorreo = findViewById(R.id.correoL);
        mContrasena = findViewById(R.id.password);
        mBotonLogin = findViewById(R.id.Login);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mBotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = mCorreo.getText().toString().trim();
                String password = mContrasena.getText().toString().trim();
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
                fAuth.signInWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"logged", Toast.LENGTH_SHORT).show();
                            // aqui falta empezar la new activity que seria el menu de opciones del beneficiario
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("usuarios").document(userID);
                            documentReference.addSnapshotListener(Login.this, new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    String tipo = documentSnapshot.getString("Tipo");
                                    if(tipo.equals("Beneficiario")){
                                        String verificado = documentSnapshot.getString("Verificado");
                                        if(verificado.equals("No")){
                                            Intent registradoB = new Intent(getApplicationContext(),VerificadoFotos.class);
                                            startActivity(registradoB);
                                        }else{
                                            Intent registradoB = new Intent(getApplicationContext(),Beneficiario.class);
                                            startActivity(registradoB);
                                        }

                                    }else if(tipo.equals("Donador")){
                                        Intent registradoD = new Intent(getApplicationContext(),Donador.class);
                                        startActivity(registradoD);
                                    }else if(tipo.equals("Administrador")){
                                        Intent registradoA = new Intent(getApplicationContext(), admin.class);
                                        startActivity(registradoA);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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


}
