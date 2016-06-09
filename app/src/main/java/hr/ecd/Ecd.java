package hr.ecd;

import android.app.Application;

import org.json.JSONObject;

/**
 * Created by niekeichner on 31/05/16.
 */
public class Ecd extends Application{
    private boolean debugging = false;
    private String[] speechText;
    private int accountId = 0;
    private JSONObject employeeJSON;


    public void setDebugging(boolean debugging) {this.debugging = debugging;}
    public boolean getDebugging() {
        return debugging;
    }

    public void setSpeechText(String[] speechText){this.speechText = speechText;}
    public String[] getSpeechText(){
        return speechText;
    }

    public void setAccountId(int accountId){this.accountId = accountId;}
    public int getAccountId(){return accountId;}

    public void setEmployeeJSON(JSONObject JSON){this.employeeJSON = JSON;}
    public JSONObject getEmployeeJSON(){return this.employeeJSON;}


}
