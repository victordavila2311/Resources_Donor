package com.example.resourcesdonor;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.*;

public class ConnectionClass {
    private static Connection con;
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String user="root";
    private static final String pass="";
    private static final String url="jdbc:mysql://192.168.0.19:3306/estudiantes";
    //private static final String url="jdbc:mysql://192.168.0.19:3306/estudiantes";

    //@SuppressLint("NewApi")
    public static Connection getConnection() throws SQLException {
       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        con=null;
        //String CONN = null;
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,pass);
            //con = DriverManager.getConnection(CONN);
            if(con!=null){
                System.out.println("conexion establecida");
            }
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println("ERROR DE CONNEXION VICTOR  "+e);
        }
        return con;
        //INSERT INTO `registro` (`Nombre`, `Apellido`, `Materia1`, `Nota1`, `Materia2`, `Nota2`, `Materia3`, `Nota3`, `Materia4`, `Nota4`, `Promedio`) VALUES ('victor', 'davila', 'prob. y est.', '4.5', 'POO', '3', 'Ecuaciones Dif.', '4', 'TIE', '3.5', '3.75');
    }
}
