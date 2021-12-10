package ir.adrianet.uploaddownloadimage.Core;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


@SuppressWarnings("unchecked")
public class Presenter {


    private static Presenter _global;
    public  static Presenter get_global()
    {
        if (_global != null)
            return _global;
        else
            return new Presenter();
    }
    public Presenter()
    {
        _global = this;
    }



    RequestQueue QUEUE;

    public void OnCreate(Context context)
    {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
            handleSSLHandshake();

        QUEUE = Volley.newRequestQueue(context);
    }


    public <T,U> void PostAction(final IView<T> RelMaster, String URL, U requestModel, final Class<T> responseType) {
        String strJson;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            strJson =  objectMapper.writeValueAsString(requestModel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            RelMaster.OnError(ErrorGenetaror(611),611);
            return;
        }

        JsonRequest<String> jsObjRequest = new JsonRequest<String>(Request.Method.POST, URL  ,strJson , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try{
                    T responseModel  = objectMapper.readValue(response, responseType);;
                    RelMaster.OnSucceed(responseModel);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(612),612);
                }
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError(ErrorGenetaror(error.networkResponse.statusCode),error.networkResponse.statusCode);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(610),610);
                }
            }

        }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return getAllHeader();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,  "UTF-8");
                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        jsObjRequest.setRetryPolicy(getDefaultRetryPolicy());
        QUEUE.add(jsObjRequest);
    }


    public <U> void PostNotResJson(final IView<String> RelMaster, String URL, U requestModel) {
        String strJson;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            strJson =  objectMapper.writeValueAsString(requestModel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            RelMaster.OnError(ErrorGenetaror(611),611);
            return;
        }

        JsonRequest<String> jsObjRequest = new JsonRequest<String>(Request.Method.POST, URL  ,strJson , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                RelMaster.OnSucceed(response);
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError(ErrorGenetaror(error.networkResponse.statusCode),error.networkResponse.statusCode);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(610),610);
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getAllHeader();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,  "UTF-8");
                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        jsObjRequest.setRetryPolicy(getDefaultRetryPolicy());
        QUEUE.add(jsObjRequest);
    }

    public <T,U> void PutAction(final IView<T> RelMaster, String URL, U requestModel, final Class<T> responseType) {
        String strJson;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            strJson =  objectMapper.writeValueAsString(requestModel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            RelMaster.OnError(ErrorGenetaror(611),611);
            return;
        }

        JsonRequest<String> jsObjRequest = new JsonRequest<String>(Request.Method.PUT, URL  ,strJson , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try{
                    T responseModel  = objectMapper.readValue(response, responseType);;
                    RelMaster.OnSucceed(responseModel);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(612),612);
                }
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError(ErrorGenetaror(error.networkResponse.statusCode),error.networkResponse.statusCode);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(610),610);
                }
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getAllHeader();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,  "UTF-8");
                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        jsObjRequest.setRetryPolicy(getDefaultRetryPolicy());
        QUEUE.add(jsObjRequest);
    }


    public <T> void GetAction(final IView RelMaster, String URL, final Class<T> responseType) {

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, URL , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try{
                    T responseModel  = objectMapper.readValue(response, responseType);
                    RelMaster.OnSucceed(responseModel);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(612),612);
                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError(ErrorGenetaror(error.networkResponse.statusCode),error.networkResponse.statusCode);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(610),610);
                }
            }


        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getAllHeader();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        } ;


        jsObjRequest.setRetryPolicy(getDefaultRetryPolicy());
        QUEUE.add(jsObjRequest);
    }

    public <T> void GetNotResJson(final IView<String> RelMaster, String URL) {

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, URL , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                RelMaster.OnSucceed(response);
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError(ErrorGenetaror(error.networkResponse.statusCode),error.networkResponse.statusCode);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(610),610);
                }
            }


        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/json");
                headers.put("Accept","text/plain");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        } ;


        jsObjRequest.setRetryPolicy(getDefaultRetryPolicy());
        QUEUE.add(jsObjRequest);
    }

    public <T> void DeleteAction(final IView RelMaster, String URL, final Class<T> responseType) {

        StringRequest jsObjRequest = new StringRequest(Request.Method.DELETE, URL , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try{
                    T responseModel  = objectMapper.readValue(response, responseType);
                    RelMaster.OnSucceed(responseModel);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(612),612);
                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError(ErrorGenetaror(error.networkResponse.statusCode),error.networkResponse.statusCode);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    RelMaster.OnError(ErrorGenetaror(610),610);
                }
            }


        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
               return getAllHeader();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        } ;


        jsObjRequest.setRetryPolicy(getDefaultRetryPolicy());
        QUEUE.add(jsObjRequest);
    }

    private RetryPolicy getDefaultRetryPolicy()
    {
        return new DefaultRetryPolicy(9000,0,1f);
    }

    private Map<String, String> getAllHeader()
    {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");
        return headers;
    }


    private String ErrorGenetaror(int statusCode)
    {
        if (statusCode == 500)
            return "خطا در سرور";
        else
            return "عدم برقراری ارتباط ، لطفا دوباره تلاش کنید";
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}
