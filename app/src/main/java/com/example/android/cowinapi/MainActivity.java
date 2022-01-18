package com.example.android.cowinapi;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runnable.run();
}

private Runnable runnable = new Runnable() {
    @Override
    public void run() {
        int district = 737;
        String date=getDate(1);
        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id="+district+"&date="+date;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name, address, state_name, pincode, district_name, from, to, vaccine, fee_type, fee, date,center_id;
                    int dose1, dose2, minAgeLimit;
                    TextView textView = findViewById(R.id.text_view);
                    JSONArray jsonArray = response.getJSONArray("centers");
                    JSONObject elements;
                    if (jsonArray.length() > 0) {
                        int counter=0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            elements = jsonArray.getJSONObject(i);
                            name = elements.getString("name");
                            center_id=elements.getString("center_id");

                            fee_type = elements.getString("fee_type").toUpperCase();
                            if (!fee_type.equalsIgnoreCase("Free")) {
                                fee = "Rs. " + elements.getJSONArray("vaccine_fees").getJSONObject(0).getString("fee");
                            } else {
                                fee = "Rs. 0";
                            }

                            address = elements.getString("address");
                            state_name = elements.getString("state_name");
                            pincode = elements.getString("pincode");
                            district_name = elements.getString("district_name");
                            from = elements.getString("from");
                            to = elements.getString("to");
                            dose1 = elements.getJSONArray("sessions").getJSONObject(0).getInt("available_capacity_dose1");
                            dose2 = elements.getJSONArray("sessions").getJSONObject(0).getInt("available_capacity_dose2");
                            vaccine = elements.getJSONArray("sessions").getJSONObject(0).getString("vaccine");
                            minAgeLimit = elements.getJSONArray("sessions").getJSONObject(0).getInt("min_age_limit");
                            date = elements.getJSONArray("sessions").getJSONObject(0).getString("date");
                            if(address.toLowerCase(Locale.ROOT).contains("durgapur")) {
                                if(counter==0) {
                                    textView.setText(String.format(Locale.getDefault(), "%s\nCenter id: %s\n%s\n%s\n%s\nAge Limit: %s+\n%s\nDOSE 1: %d\nDOSE 2: %d\n%s\nTimings: %s - %s\n%s - %s\nFEE: %s\n", fee_type, center_id, name, address, pincode, minAgeLimit, vaccine, dose1, dose2, date, from, to, district_name, state_name, fee));
                                    textView.append("\n------------------------------------------------------------------------\n");
                                    counter++;
                                }else{
                                    textView.append(String.format(Locale.getDefault(), "%s\nCenter id: %s\n%s\n%s\n%s\nAge Limit: %s+\n%s\nDOSE 1: %d\nDOSE 2: %d\n%s\nTimings: %s - %s\n%s - %s\nFEE: %s\n", fee_type, center_id, name, address, pincode, minAgeLimit, vaccine, dose1, dose2, date, from, to, district_name, state_name, fee));
                                    textView.append("\n------------------------------------------------------------------------\n");
                                }
                            }
                        }
                    } else {
                        textView.setText("Not Available");
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Some error in try block.\n" + e, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong. " + error, Toast.LENGTH_LONG).show();
            }
        });
//    {
//        @Override
//        public Map<String, String> getHeaders() throws AuthFailureError {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("accept", "application/json; charset=UTF-8");
//            params.put("Accept-Language", "en_US");
//            return params;
//        }
//    };
        requestQueue.add(jsonObjectRequest);
        handler.postDelayed(this,5000);
    }
};

//public void dotask()
//{
//    Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
//    int district = 737;
//    String date=getDate(1);
//    String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id="+district+"&date="+date;
//    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//        @Override
//        public void onResponse(JSONObject response) {
//            try {
//                String name, address, state_name, pincode, district_name, from, to, vaccine, fee_type, fee, date;
//                int dose1, dose2, minAgeLimit;
//                TextView textView = findViewById(R.id.text_view);
//                JSONArray jsonArray = response.getJSONArray("centers");
//                JSONObject elements;
//                if (jsonArray.length() > 0) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        elements = jsonArray.getJSONObject(i);
//                        name = elements.getString("name");
//
//                        fee_type = elements.getString("fee_type").toUpperCase();
//                        if (!fee_type.equalsIgnoreCase("Free")) {
//                            fee = "Rs. " + elements.getJSONArray("vaccine_fees").getJSONObject(0).getString("fee");
//                        } else {
//                            fee = "Rs. 0";
//                        }
//
//                        address = elements.getString("address");
//                        state_name = elements.getString("state_name");
//                        pincode = elements.getString("pincode");
//                        district_name = elements.getString("district_name");
//                        from = elements.getString("from");
//                        to = elements.getString("to");
//                        dose1 = elements.getJSONArray("sessions").getJSONObject(0).getInt("available_capacity_dose1");
//                        dose2 = elements.getJSONArray("sessions").getJSONObject(0).getInt("available_capacity_dose2");
//                        vaccine = elements.getJSONArray("sessions").getJSONObject(0).getString("vaccine");
//                        minAgeLimit = elements.getJSONArray("sessions").getJSONObject(0).getInt("min_age_limit");
//                        date = elements.getJSONArray("sessions").getJSONObject(0).getString("date");
//                        if (i == 0) {
//                            textView.setText(String.format(Locale.getDefault(), "%s\n%s\n%s\n%s\nAge Limit: %s+\n%s\nDOSE 1: %d\nDOSE 2: %d\n%s\nTimings: %s - %s\n%s - %s\nFEE: %s\n", fee_type, name, address, pincode, minAgeLimit, vaccine, dose1, dose2, date, from, to, district_name, state_name, fee));
//                            textView.append("\n------------------------------------------------------------------------\n");
//                        } else {
//                            textView.append(String.format(Locale.getDefault(), "\n%s\n%s\n%s\n%s\nAge Limit: %s+\n%s\nDOSE 1: %d\nDOSE 2: %d\n%s\nTimings: %s - %s\n%s - %s\nFEE: %s\n", fee_type, name, address, pincode, minAgeLimit, vaccine, dose1, dose2, date, from, to, district_name, state_name, fee));
//                            textView.append("\n------------------------------------------------------------------------\n");
//                        }
//                    }
//                } else {
//                    textView.setText("Not Available");
//                }
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Some error in try block.\n" + e, Toast.LENGTH_LONG).show();
//            }
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            Toast.makeText(getApplicationContext(), "Something Went Wrong. " + error, Toast.LENGTH_LONG).show();
//        }
//    });
//    {
//        @Override
//        public Map<String, String> getHeaders() throws AuthFailureError {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("accept", "application/json; charset=UTF-8");
//            params.put("Accept-Language", "en_US");
//            return params;
//        }
//    };

//    requestQueue.add(jsonObjectRequest);


private String getDate(int toIncreaseDayBy)
{
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR,toIncreaseDayBy);
    Date date= calendar.getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
    return dateFormat.format(date);
}

//private String getCurrentTime()
//{
//    Date date= Calendar.getInstance().getTime();
//    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss",Locale.getDefault());
//    return timeFormat.format(date);
//}


    public void refresh(View view) {
        runnable.run();
    }
}