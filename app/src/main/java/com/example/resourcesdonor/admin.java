package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * En este Activity se muestran las opciones que tiene el administrador para desarrollar cambios
 * @author victor manuel davila 1001218585
 * @version 1.0
 */

public class admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

    }

    /**
     * Esta funcion encvia al usuario a la seccion de añadir marcadores al mapa
     * @param view -unused
     * @see AnadirMarcador
     */
    public void irMarcador(View view){
        startActivity(new Intent(getApplicationContext(),AnadirMarcador.class));
    }

    /**
     * En esta funcion se envia al usuario a revisar los beneficiarios que aun no han sido verificados
     * @param view -unused
     * @see ListaU
     */
    public void irLista(View view){
        startActivity(new Intent(getApplicationContext(),ListaU.class));
    }

    /**
     * En esta funcion se envia al usuario a la ventana para revisar las imagenes subidas por los beneficiarios
     * @param view -unused
     * @see ImagenesU
     */
    public void irImg(View view){
        startActivity(new Intent(getApplicationContext(),ImagenesU.class));
    }

    /**
     * En esta funcion se envia al usuario a verificar a los beneficiarios revisados
     * @param view -unused
     * @see Verificar
     */
    public void irVerif(View view){
        startActivity(new Intent(getApplicationContext(),Verificar.class));
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
