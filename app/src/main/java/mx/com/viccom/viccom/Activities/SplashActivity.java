package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import mx.com.viccom.viccom.R;

public class SplashActivity extends AppCompatActivity {

    private Animation animAparecer,animDesaparecer;
    private ImageView imgLogComapa,imgLogVictoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogVictoria = (ImageView) findViewById(R.id.logovictoria);
        imgLogComapa = (ImageView) findViewById(R.id.logocomapa);

        animAparecer = AnimationUtils.loadAnimation(this,R.anim.aparecer);
        animDesaparecer = AnimationUtils.loadAnimation(this,R.anim.desaparecer);

        imgLogVictoria.setAnimation(animAparecer);
        imgLogComapa.setAnimation(animDesaparecer);

        final Intent intent = new Intent(this,LoginActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                   // imgLogComapa.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
