package hr.ecd;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopupActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //int width = dm.widthPixels; //Max. pixels of fullscreen
        //int height = dm.heightPixels;

        int width = 1000;
        int height = 500;


        getWindow().setLayout(width,height); //set window size

    }

}