package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * desde esta Activity se revisan las imagenes subidas por los beneficiarios
 */

public class ImagenesU extends AppCompatActivity {
    EditText u;
    ImageView doc, ced, pCed;
    Button s, down;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference sReference;
    /**
     * url del fire base storage donde se guardan las imagenes de los beneficiarios
     */
    String url = "gs://resources-donor.appspot.com/";
    OutputStream oStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        u = findViewById(R.id.correobusq);
        doc = findViewById(R.id.fotoDoc);
        ced = findViewById(R.id.fotoCed);
        pCed = findViewById(R.id.fotoPCed);
        s = findViewById(R.id.searchB);
        down = findViewById(R.id.downloadB);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes_u);

    }

    /**
     * Esta funcion permite a los administradores descargar las imagenes en el alacenamiento interno del celular <br/>
     * en caso de no verlas correctamente desde la aplicacion<br/>
     * si es la primera vez que se requiere esta funcion la aplicacion pedira los permisos correspondientes
     * @param view -unused
     */
    public void Descargar(View view){
        doc = findViewById(R.id.fotoDoc);
        ced = findViewById(R.id.fotoCed);
        pCed = findViewById(R.id.fotoPCed);
        ImageView[] imgs = {doc, ced, pCed};
        for(ImageView down : imgs){
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    BitmapDrawable down1 = (BitmapDrawable) down.getDrawable();
                    Bitmap b1 = down1.getBitmap();
                    File filepath = Environment.getExternalStorageDirectory();
                    File dir = new File(filepath.getAbsolutePath()+"/resources_donor/");
                    dir.mkdir();
                    File file = new File(dir, System.currentTimeMillis()+".jpg");
                    try {
                        oStream = new FileOutputStream(file);

                    } catch (Exception e) {
                        Toast.makeText(ImagenesU.this,"Fallo la descarga"+e, Toast.LENGTH_LONG).show();
                    }
                    b1.compress(Bitmap.CompressFormat.JPEG, 100, oStream);
                    Toast.makeText(ImagenesU.this,"se completo la descarga", Toast.LENGTH_LONG).show();
                    try {
                        oStream.flush();
                        oStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ActivityCompat.requestPermissions(ImagenesU.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},44);
                }
            }else{
                ActivityCompat.requestPermissions(ImagenesU.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},44);
            }
        }
    }

    /**
     * Esta funcion hace uso del API de Firebase Storage que es la seccion de Firebase encargada de archivos multimedia <br/>
     * aunque primero se conecta con Firestore para reconocer el correo y el id del usuario buscado <br/>
     * y despues transforma ese archivo en un Bitmap que puede ser visualizado en los distintos ImageView del Actiivity
     * @param view -unused
     */
    public void verImag(View view){
        u = findViewById(R.id.correobusq);
        doc = findViewById(R.id.fotoDoc);
        ced = findViewById(R.id.fotoCed);
        pCed = findViewById(R.id.fotoPCed);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        CollectionReference user = fStore.collection("usuarios");
        user.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String correo = u.getText().toString();
                for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
                    UsuariosClass usuario = qds.toObject(UsuariosClass.class);
                    String ver = usuario.getCorreo();
                    if(ver.equals(correo)){
                        String id=qds.getId();

                        //imagen1
                        sReference = fStorage.getReferenceFromUrl(url+"usuarios/"+id+"/doc");
                        try {
                            final File file = File.createTempFile("doc","jpg");
                            sReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bMap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    doc.setImageBitmap(bMap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ImagenesU.this,"Fallo subir la img por"+e, Toast.LENGTH_LONG).show();
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(ImagenesU.this,"Fallo por"+e, Toast.LENGTH_SHORT).show();
                        }

                        //imagen2
                        sReference = fStorage.getReferenceFromUrl(url).child("usuarios/"+id+"/cedula");
                        try {
                            final File file = File.createTempFile("ced","jpg");
                            sReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bMap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    ced.setImageBitmap(bMap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ImagenesU.this,"Fallo subir la img por"+e, Toast.LENGTH_LONG).show();
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(ImagenesU.this,"Fallo por"+e, Toast.LENGTH_SHORT).show();
                        }

                        //imagen3
                        sReference = fStorage.getReferenceFromUrl(url).child("usuarios/"+id+"/Pcedula");
                        try {
                            final File file = File.createTempFile("Pced","jpg");
                            sReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bMap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    pCed.setImageBitmap(bMap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ImagenesU.this,"Fallo subir la img por"+e, Toast.LENGTH_LONG).show();
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(ImagenesU.this,"Fallo por"+e, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
    }

    /**
     * Esta funcion devuelve al usuario a la barra de seleccion de funciones de administrador
     * @param view-unused
     * @see admin
     */
    public void Volver(View view){
        Intent intent = new Intent(getApplicationContext(),admin.class);
        startActivity(intent);
    }
}
