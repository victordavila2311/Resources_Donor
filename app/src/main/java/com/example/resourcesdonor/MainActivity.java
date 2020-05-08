package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.sql.*;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
