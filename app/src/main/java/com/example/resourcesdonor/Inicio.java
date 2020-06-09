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
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animatorAlpha = ObjectAnimator.ofFloat(logo, "alpha", 0.0f, 1.0f);
        animatorAlpha.setDuration(500);
        animatorSet = new AnimatorSet();
        animatorSet.play(animatorAlpha);
        animatorSet.start();


        /*
        Animation anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(1000);
        logo.startAnimation(anim);

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
