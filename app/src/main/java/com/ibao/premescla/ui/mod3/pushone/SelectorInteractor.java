package com.ibao.premescla.ui.mod3.pushone;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ibao.premescla.ConectionConfig;
import com.ibao.premescla.app.AppController;
import com.ibao.premescla.models.Muestra;
import com.ibao.premescla.models.Tancada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectorInteractor {

    private String TAG = SelectorInteractor.class.getSimpleName();

    private SelectorPresenter presenter;

    public SelectorInteractor(SelectorPresenter presenter) {
        this.presenter = presenter;
    }

    public void requestPushMuestra(Muestra mueestra){
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                ConectionConfig.POST_MUESTRA,
                this::onResponseMuestra, error -> onError(error)
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                String data = new Gson().toJson(mueestra);
                Log.i(TAG,"data:"+data);
                params.put("data",data);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void onError(VolleyError error){
        Log.e(TAG,error.toString());
        presenter.showError(error.toString());
        error.printStackTrace();
    }

    private void onResponseMuestra(String response) {
        Log.d(TAG, "resp:" + response);
        try {
            JSONObject main = new JSONObject(response);
            JSONObject data = main.getJSONObject("data");
            int success = data.getInt("success");
            if(success>0){
                presenter.respSuccess();
            }else{
                presenter.respFailed("No se pudo insertar");
            }
        } catch (JSONException e) {
            presenter.showError(e.getMessage());
        }
    }
}
