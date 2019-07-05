package m.derakhshan.travelinkapp;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        YoYo.AnimatorCallback callback=new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                Intent intent=new Intent(Splash.this,Signup.class);
                startActivity(intent);
                Splash.this.finish();
            }
        };

        YoYo.with(Techniques.Landing)
                .duration(2500)
                .repeat(1)
                .onEnd(callback)
                .playOn(findViewById(R.id.splash_logo));

    }


}
