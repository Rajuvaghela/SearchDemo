package com.example.lujayn_15.searchdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et_search_product;
    AdapterSearch adapterSearch;
    RequestQueue queue;
    Activity activity;
    ArrayList<BeanSearchProduct> beanSearchProducts = new ArrayList<>();
    RecyclerView rv_product_list;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        queue = Volley.newRequestQueue(this);
        et_search_product = (EditText) findViewById(R.id.et_search_product);
        rv_product_list = (RecyclerView) findViewById(R.id.rv_product_list);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_product_list.setLayoutManager(gridLayoutManager);
        makeJsonObjReq();
        et_search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterSearch.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void makeJsonObjReq() {
        Map<String, String> postParam = new HashMap<String, String>();
        //postParam.put("email", "test@gmail.com");
        // postParam.put("p", "somepasswordhere");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://phpjoomlacoders.com/wp/?webservice=1&vootouchservice=get_search_products", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res2", "" + response.toString());
                        try {
                            JSONObject object1 = new JSONObject(response.toString());
                            JSONObject object = object1.getJSONObject("data");
                            if (object.has("products")) {
                                JSONArray jsonArray = object.getJSONArray("products");
                                if (jsonArray.length() != 0) {
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        beanSearchProducts.clear();
                                        beanSearchProducts.addAll((Collection<? extends BeanSearchProduct>) new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<BeanSearchProduct>>() {
                                        }.getType()));
                                         adapterSearch = new AdapterSearch(beanSearchProducts);
                                        rv_product_list.setAdapter(adapterSearch);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error2", "" + error.toString());
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag(MainActivity.this);

        queue.add(jsonObjReq);

    }

}
