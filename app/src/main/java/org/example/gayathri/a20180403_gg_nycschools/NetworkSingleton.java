package org.example.gayathri.a20180403_gg_nycschools;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkSingleton {
    public static NetworkSingleton networkInstance;
    private static Context context;
    private RequestQueue requestQueue;


    public NetworkSingleton(Context ctx){
        context = ctx;
        requestQueue = getRequestQueue();
    }

    public static synchronized NetworkSingleton getInstance(Context ctx){
        if(networkInstance == null){
            networkInstance = new NetworkSingleton(ctx);
        }
        return networkInstance;
    }


    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return  requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(tag);
        getRequestQueue().add(req);
    }
}
