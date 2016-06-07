package hr.ecd;

import android.app.Application;

/**
 * Created by niekeichner on 31/05/16.
 */
public class Ecd extends Application{
    private boolean debugging = false;
    private String speechText = "";





    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }

    public boolean getDebugging() {
        return debugging;
    }

    public void setSpeechText(String speechText){this.speechText = speechText;}
    public String getSpeechText(){
        return speechText;
    }

}
