package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import java.sql.*;
public class MainActivity extends AppCompatActivity {
    private static int t = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inicio = new Intent(getApplicationContext(), Inicio.class);
            }
        },t);
        /*
        try {
            Connection con= ConnectionClass.getConnection();
        } catch (SQLException e) {
            System.out.println("otro error HUEVON"+e);
            e.printStackTrace();
        }
        */

    }

    public void registroDonar(View view){
        Intent registro_donar = new Intent(this,registroD.class);
        startActivity(registro_donar);
    }
    public void registroBenef(View view){
        Intent registro_benef = new Intent(this,registroB.class);
        startActivity(registro_benef);
    }
}
