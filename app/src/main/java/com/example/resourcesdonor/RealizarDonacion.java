package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RealizarDonacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_donacion);
    }
    public void volver(View view){
        Intent vol = new Intent(this,Donador.class);
        startActivity(vol);
    }
}
