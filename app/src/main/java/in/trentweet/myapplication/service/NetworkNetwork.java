package in.trentweet.myapplication.service;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import in.trentweet.myapplication.utilities.AppConstant;
import in.trentweet.myapplication.utilities.AppController;
import in.trentweet.myapplication.utilities.ConnectionDetector;
import in.trentweet.myapplication.utilities.LoggerUtils;

public class NetworkNetwork implements INetworkInterface {

    private Context _context;
    private ConnectionDetector connectionDetector;


    public NetworkNetwork(Context context) {
        this._context = context;
        this.connectionDetector = connectionDetector;
    }

    @Override
    public void JsonObjectRequest(int REQUEST_TYPE, String url, JSONObject input, final cbJsonObjectRequest cbJsonObjectRequest) {

        LoggerUtils.error("\n================================\n");
        LoggerUtils.error("\nREQUEST TYPE ==> " + REQUEST_TYPE + "");
        LoggerUtils.error("\nURL ==> " + AppConstant.BASE_URL + url);
        LoggerUtils.error("\nINPUT ==> " + input.toString());
        LoggerUtils.error("\n================================\n");

        JsonObjectRequest request = new JsonObjectRequest(REQUEST_TYPE, AppConstant.BASE_URL + url, input,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LoggerUtils.error("Response: " + response);
                        cbJsonObjectRequest.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String body, errorMessage = "undefined";
                int errorCode = 0;

                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                        JSONObject errorObj = new JSONObject(body).optJSONObject("error");
                        errorMessage = errorObj.optString("message");
                        errorCode = errorObj.optInt("statusCode");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                LoggerUtils.error("Error Code: " + errorCode + " Error Message: " + errorMessage);
                cbJsonObjectRequest.onError(errorCode, errorMessage);
            }
        });


        AppController.getInstance().addToRequestQueue(request);


    }

    @Override
    public void JsonArrayRequest(int REQUEST_TYPE, String url, JSONArray input, final cbJsonArrayRequest cbJsonArrayRequest) {

        LoggerUtils.error("\n================================\n");
        LoggerUtils.error("\nREQUEST TYPE ==> " + REQUEST_TYPE + "");
        LoggerUtils.error("\nURL ==> " + AppConstant.BASE_URL + url);
        LoggerUtils.error("\n================================\n");


        JsonArrayRequest request = new JsonArrayRequest(REQUEST_TYPE, AppConstant.BASE_URL + url, input,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        LoggerUtils.error("Response: " + response);
                        cbJsonArrayRequest.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String body, errorMessage = "undefined";
                int errorCode = 0;

                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                        JSONObject errorObj = new JSONObject(body).optJSONObject("error");
                        errorMessage = errorObj.optString("message");
                        errorCode = errorObj.optInt("statusCode");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                LoggerUtils.error("Error Code: " + errorCode + " Error Message: " + errorMessage);
                cbJsonArrayRequest.onError(errorCode, errorMessage);
            }
        });


        AppController.getInstance().addToRequestQueue(request);


    }


}
