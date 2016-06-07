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

/**
 * Created by niekeichner on 07/06/16.
 */
public class Api {
    private final String URL = "http://80.57.4.176:523/login";
    private JSONObject apiResponse = null;

    public JSONObject request(JSONObject jsonBody, Context context) {
        JsonObjectRequest jsonReq = new JsonObjectRequest(URL, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        apiResponse = response;
                    }
                }, new Response.ErrorListener() {
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
        return apiResponse;
    }

}
