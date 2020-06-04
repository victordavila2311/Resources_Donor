package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

    }

    public void irMarcador(View view){
        startActivity(new Intent(getApplicationContext(),AnadirMarcador.class));
    }

    public void irLista(View view){
        startActivity(new Intent(getApplicationContext(),ListaU.class));
    }

    public void irImg(View view){
        startActivity(new Intent(getApplicationContext(),ImagenesU.class));
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
