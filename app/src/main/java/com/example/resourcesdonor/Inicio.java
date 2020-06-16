package com.example.resourcesdonor;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * Este es un Splash screen que se muestra al abrir la aplicación
 */

public class Inicio extends AppCompatActivity {
    ImageView logo;
    int t = 2000;
    private ObjectAnimator animatorAlpha;
    private AnimatorSet animatorSet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logo = findViewById(R.id.imageView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        animatorAlpha = ObjectAnimator.ofFloat(logo, "alpha", 0.0f, 1.0f);
        animatorAlpha.setDuration(500);
        animatorSet = new AnimatorSet();
        animatorSet.play(animatorAlpha);
        animatorSet.start();
        /**
         * Este es una forma de usar hilos para correr el Activity que en realidad es el inicio<br/>
         * inicia el activity principal luego de una pequeña animacion
         * @see MainActivity
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inicio = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(inicio);
                finish();
            }
        },t);


    }
}
