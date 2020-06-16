package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * Esta es la Activity inicial de la aplicación
 */

public class MainActivity extends AppCompatActivity {
    private static int t = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Esta funcion te manda a la activity de registro de Donadores
     * @param view -unused
     * @see registroD
     */
    public void registroDonar(View view){
        Intent registro_donar = new Intent(this,registroD.class);
        startActivity(registro_donar);
    }

    /**
     * Esta funcion te manda a la activity de registro de Beneficiarios
     * @param view -unused
     * @see registroB
     */
    public void registroBenef(View view){
        Intent registro_benef = new Intent(this,registroB.class);
        startActivity(registro_benef);
    }
}
