package com.indushospitals.api;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.utils.AppController;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by think360 on 16/05/17.
 */

public class ApiGetPostNoProgressBar {


    private StringRequest sr;


    public ApiGetPostNoProgressBar(final Activity activity, int method , String url, final Map<String, String> params, final ServerCallBackObj callback)
    {
        sr = new StringRequest(method, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {


                try
                {
                    final JSONObject jsonObject = new JSONObject(response);

                    callback.onSuccess(jsonObject);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                {
                    Custom_Toast(activity,""+error);
                }
                else if (error instanceof AuthFailureError)
                {
                    Custom_Toast(activity,""+error);
                }
                else if (error instanceof ServerError)
                {
                    Custom_Toast(activity,""+error);
                }
                else if (error instanceof NetworkError)
                {
                    Custom_Toast(activity,""+error);
                }
                else if (error instanceof ParseError)
                {
                    Custom_Toast(activity,""+error);
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<>();
                params.put("Accept", "json");
                return params;
            }
        };
    }
    public void addQueue()
    {
        AppController.getInstance().addToRequestQueue(sr);
    }

    public void Custom_Toast(Activity activity,String abc)
    {
        TastyToast.makeText(activity, abc, TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }
}