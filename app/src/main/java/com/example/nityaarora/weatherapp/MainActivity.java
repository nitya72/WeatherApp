package com.example.nityaarora.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.nityaarora.weatherapp.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //"https://api.openweathermap.org/data/2.5/weather?q=Varanasi&appid=100eb1ddd5e790c6b5cb2d32708313f9"

        final String basicURL="https://api.openweathermap.org/data/2.5/weather?q=";
        final String key="&appid=100eb1ddd5e790c6b5cb2d32708313f9";

        final EditText city=(EditText) findViewById(R.id.city);

        Button button=(Button) findViewById(R.id.button);

        final TextView lat=(TextView)findViewById(R.id.lat);

        final TextView lon=(TextView)findViewById(R.id.lon);

        final TextView forecast=(TextView)findViewById(R.id.forcast);

        final TextView description=(TextView)findViewById(R.id.description) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cities=city.getText().toString();
                final String url=basicURL+cities+key;
                Log.i("yo","tapped"+url);

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response",response.toString());

                                try {
                                    String coord=response.getString("coord");
                                    Log.i("tag",coord);
                                    JSONObject coo=new JSONObject(coord);
                                    String lati=coo.getString("lat");
                                    String longi=coo.getString("lon");
                                    lat.setText(lati);
                                    lon.setText(longi);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String weather=response.getString("weather");
                                    JSONArray array=new JSONArray(weather);
                                    for(int i=0;i<array.length();i++)
                                    {
                                       JSONObject qw=array.getJSONObject(i);
                                       String main=qw.getString("main");
                                       String des=qw.getString("description");
                                       forecast.setText(main);
                                       description.setText(des);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error","Occured"+error);
                    }
                });
                MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
            }
        });
    }
}
