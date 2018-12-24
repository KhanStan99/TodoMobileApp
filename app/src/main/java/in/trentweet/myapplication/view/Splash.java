package in.trentweet.myapplication.view;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.trentweet.myapplication.R;
import in.trentweet.myapplication.utilities.AppSharedPreferences;

public class Splash extends AppCompatActivity implements Animation.AnimationListener {

    private Animation animationFadeIn, animationFadeOut;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;

    private int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        animationFadeIn.setAnimationListener(this);
        animationFadeOut.setAnimationListener(this);

        setFadeOutAnimation(view1);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                AppSharedPreferences sharedPreferences = new AppSharedPreferences(getApplicationContext());

                if (sharedPreferences.getId() == 0 || sharedPreferences.getUserName() == null ||
                        sharedPreferences.getToken() == null) {
                    finish();
                    startActivity(new Intent(Splash.this, Home.class));
                } else {
                    finish();
                    startActivity(new Intent(Splash.this, TodoTask.class));
                }
            }
        }, 3000);


    }

    private void setFadeOutAnimation(View view) {
        view.startAnimation(animationFadeOut);
        viewId = view.getId();
    }

    private void setFadeInAnimation(View view) {
        view.startAnimation(animationFadeIn);
        viewId = view.getId();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animationFadeOut) {

            switch (viewId) {
                case R.id.view1:
                    setFadeInAnimation(view1);
                    setFadeOutAnimation(view2);
                    break;
                case R.id.view2:
                    setFadeInAnimation(view2);
                    setFadeOutAnimation(view3);
                    break;
                case R.id.view3:
                    setFadeInAnimation(view3);
                    setFadeOutAnimation(view1);
                    break;
            }
        } else {

            switch (viewId) {
                case R.id.view1:
                    setFadeOutAnimation(view2);
                    break;
                case R.id.view2:
                    setFadeOutAnimation(view3);
                    break;
                case R.id.view3:
                    setFadeOutAnimation(view1);
                    break;
            }
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
