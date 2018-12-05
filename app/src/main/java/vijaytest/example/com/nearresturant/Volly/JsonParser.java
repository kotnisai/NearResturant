package vijaytest.example.com.nearresturant.Volly;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import vijaytest.example.com.nearresturant.Utils.Logger;


/**
 * Created by saiprasanthk on 07-11-2017.
 */

public class JsonParser {
    private Context context;
    public String encrypt;

    public JsonParser(Context context) {
        this.context = context;
    }

    public void parseVollyJsonObj(String url, int i, final String input, final Helper postResponse) {
        Logger.log("zzz:::url::" + url);
        if (NetworkUtil.getConnectivityStatus(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    postResponse.backResponse(response.toString());
                    Logger.log("zzz :::::Response:: " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return encrypt.getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(90000, 1, 1f));
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "No Internet Connection ", Toast.LENGTH_SHORT).show();
        }
    }
}