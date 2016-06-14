package hr.ecd;

import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by niekeichner on 07/06/16.
 */


public class Api {

    public enum Errors {
        USER_NOT_FOUND,
        NO_RECORDS_FOUND,
        ERROR_NOT_FOUND,
        FOUR_O_FOUR,
        ECONNREFUSED;

        private int num;

        public static Errors fromInteger(Integer in) {
            switch(in) {
                case 10:
                    return USER_NOT_FOUND;
                case 404:
                    return FOUR_O_FOUR;
                case 161:
                    return NO_RECORDS_FOUND;
                case 500:
                    return ECONNREFUSED;
            }
            return ERROR_NOT_FOUND;
        }

    }

    private final String rootUrl = "http://80.57.4.176:523";


    public void request(Context context, String apiUrl, JSONObject jsonBody, final Response.Listener<JSONObject> callback) throws JSONException {
        JsonObjectRequest jsonReq = new JsonObjectRequest(rootUrl + apiUrl, jsonBody,
                callback, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.v("ERROR:%n %s", error.getMessage());
            }
        });

        // Adding request to volley request queue
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonReq);
        }
        catch(Exception e) {

        }

    }

}
