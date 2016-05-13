package hr.ecd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.ImageView;


/*
    De activity die gebeurd aan het begin van de app
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final ImageView image = (ImageView)findViewById(R.id.hpimage);

        image.startAnimation(animationFadeIn);
        image.setRotation(0); //startAnimation is een infinite loop, dus rotation(0) om het maar 1 keer te animeren


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,LoginActivity.class)); //To login screen
                finish(); //maakt een einde aan de activity zodat je niet "Back" kan naar deze activiy
            }
        }, 2000);




    }

}
