package in.trentweet.myapplication.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface INetworkInterface {


    void JsonObjectRequest(int REQUEST_TYPE, String url, JSONObject input, cbJsonObjectRequest cbJsonObjectRequest);


    void JsonArrayRequest(int REQUEST_TYPE, String url, JSONArray input, cbJsonArrayRequest cbJsonArrayRequest);


    interface cbJsonObjectRequest {

        void onResponse(JSONObject response);

        void onError(int errorCode, String errorMsg);

    }

    interface cbJsonArrayRequest {

        void onResponse(JSONArray response);

        void onError(int errorCode, String errorMsg);

    }

}
