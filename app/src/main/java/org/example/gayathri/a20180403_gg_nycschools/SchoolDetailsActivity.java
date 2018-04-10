package org.example.gayathri.a20180403_gg_nycschools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class SchoolDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SchoolDetailsActivity";
    private TextView tvSChoolName;
    private TextView tvSatReadingScore;
    private TextView tvSatWritingScore;
    private TextView tvSatMathScore;
    private TextView tvTotalNumber;
    private TextView tvSchoolDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);

        tvSChoolName = findViewById(R.id.tvSchoolName);
        tvSatReadingScore = findViewById(R.id.tvSatReading);
        tvSatWritingScore = findViewById(R.id.tvSatWriting);
        tvSatMathScore = findViewById(R.id.tvSatMath);
        tvTotalNumber = findViewById(R.id.tvNumberOfSatTakers);
        tvSchoolDetails = findViewById(R.id.tvSchoolDetails);
        final Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        //String url = "https://data.cityofnewyork.us/resource/734v-jeq5.json?dbn=";
        Intent intent = getIntent();
        String dbn = intent.getStringExtra("schooldbn");
        if (dbn != null && !dbn.equals("")) {
            String url = "https://data.cityofnewyork.us/resource/734v-jeq5.json?dbn="+dbn;
            fetchSchoolData(url);
        } else{
            tvSchoolDetails.setText(R.string.noData);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkSingleton.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll(TAG);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    private void fetchSchoolData(String url){
            Log.d(TAG, "fetchSchoolData: " + url);
            //RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest messageRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("requestQueue", response);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String schoolName = jsonObject.getString("school_name");
                            String satReadingScore = jsonObject.getString("sat_critical_reading_avg_score");
                            String satWritingScore = jsonObject.getString("sat_writing_avg_score");
                            String satMathScore = jsonObject.getString("sat_math_avg_score");
                            String satTakers = jsonObject.getString("num_of_sat_test_takers");
                            tvSChoolName.setText(getString(R.string.schoolName, schoolName));
                            tvSatMathScore.setText(getString(R.string.satMathScore, satMathScore));
                            tvSatReadingScore.setText(getString(R.string.satReadingScore, satReadingScore));
                            tvSatWritingScore.setText(getString(R.string.satWritingScore, satWritingScore));
                            tvTotalNumber.setText(getString(R.string.satTakers,satTakers));
                            tvSchoolDetails.setText(R.string.schoolsDetails);
                        } else {
                            tvSchoolDetails.setText(R.string.noData);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: ", e);
                        tvSchoolDetails.setText(R.string.noData);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: ", error);
                    tvSchoolDetails.setText(R.string.noData);
                }
            });

            NetworkSingleton.getInstance(this.getApplicationContext()).addToRequestQueue(messageRequest,TAG);

    }
}
