package com.example.resourcesdonor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class VerificadoFotos extends AppCompatActivity {
    ImageView fD,fC,fPC,img;
    Button fDB,fCB,fPCB;
    String userID,location;
    String url = "gs://resources-donor.appspot.com/";

    FirebaseAuth fAuth;
    StorageReference storageReference;
    FirebaseStorage fStorage;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificado_fotos);
        storageReference = FirebaseStorage.getInstance().getReference();
        fD = findViewById(R.id.fotoDocumento);
        fC = findViewById(R.id.fotoCedula);
        fPC = findViewById(R.id.fotoPCedula);
        fDB = findViewById(R.id.fotoDB);
        fCB = findViewById(R.id.fotoCB);
        fPCB = findViewById(R.id.fotoPCB);
        fStorage = FirebaseStorage.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fAuth = FirebaseAuth.getInstance();
        fDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupimagen1();
            }
        });
        fCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupimagen2();
            }
        });
        fPCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupimagen3();
            }
        });


    }

    private void setupimagen1() {
        img =findViewById(R.id.fotoDocumento);
        location = "doc";
        Intent abrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(abrirGaleria, 1000);
    }

    private void setupimagen2() {
        img =findViewById(R.id.fotoCedula);
        location = "cedula";
        Intent abrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(abrirGaleria, 1000);
    }

    private void setupimagen3() {
        img =findViewById(R.id.fotoPCedula);
        location = "Pcedula";
        Intent abrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(abrirGaleria, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                img.setImageURI(imageUri);
                subirFoto(imageUri);
            }
        }
    }

    private void subirFoto(Uri imageUri) {
        userID = fAuth.getCurrentUser().getUid();
        StorageReference fileRef = storageReference.child("usuarios/"+userID+"/"+location);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(VerificadoFotos.this,"subido", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VerificadoFotos.this,"Fallo por "+e, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

}
