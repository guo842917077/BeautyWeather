package com.crazyorange.beauty.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import com.crazyorange.beauty.R;

/**
 * @author crazyorange
 * <p>
 * login page
 * 1. use Lottie animation
 * 2. use MotionLayout
 * 3. use Room database
 * 4. use ARouter
 */
public class LoginActivity extends AppCompatActivity {
    private LottieAnimationView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mImageView = findViewById(R.id.img_over);
        mImageView.playAnimation();
        mImageView.setSpeed(0.6f);
    }


}
