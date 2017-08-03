package webops.shaastra.iitm.shaastra2018.mainUI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import webops.shaastra.iitm.shaastra2018.MainActivity;
import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.imageCaching.ImageUtil;

public class InitialSplash extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 3000;

    String testImageURL = "http://placehold.it/240x120&text=image1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_splash);

        ImageLoader imloader = ImageUtil.getImageLoader(this);
        ImageView splashImView = (ImageView) findViewById(R.id.initSplashImage);
        imloader.displayImage(testImageURL, splashImView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(InitialSplash.this, InitialPromptActivity.class);
                InitialSplash.this.startActivity(mainIntent);
                InitialSplash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
