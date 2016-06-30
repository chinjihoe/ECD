package hr.ecd;

import android.app.Application;

import org.json.JSONObject;

/**
 * Used to set and get Global variables
 */
public class Ecd extends Application{
    private String[] speechText;
    private int accountId = 1;
    private int clientId = 70;
    private JSONObject employeeJSON;

    public void setSpeechText(String[] speechText){this.speechText = speechText;}
    public String[] getSpeechText(){
        return speechText;
    }

    public void setAccountId(int accountId){this.accountId = accountId;}
    public int getAccountId(){return accountId;}

    public void setClientId(int clientId){this.clientId = clientId;}
    public int getClientId(){return clientId;}

    public void setEmployeeJSON(JSONObject JSON){this.employeeJSON = JSON;}
    public JSONObject getEmployeeJSON(){return this.employeeJSON;}


}
