package hr.ecd;

import android.app.Application;

/**
 * Created by niekeichner on 31/05/16.
 */
public class Ecd extends Application{
    private boolean debugging = false;

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }

    public boolean getDebugging() {
        return debugging;
    }
}
