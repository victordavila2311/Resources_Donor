package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class ImagenesU extends AppCompatActivity {
    EditText u;
    ImageView doc, ced, pCed;
    Button s;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference sReference;
    String url = "gs://resources-donor.appspot.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        u = findViewById(R.id.correobusq);
        doc = findViewById(R.id.fotoDoc);
        ced = findViewById(R.id.fotoCed);
        pCed = findViewById(R.id.fotoPCed);
        s = findViewById(R.id.searchB);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes_u);


        /*
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */



    }

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



    public void Volver(View view){
        Intent intent = new Intent(getApplicationContext(),admin.class);
        startActivity(intent);
    }
}
