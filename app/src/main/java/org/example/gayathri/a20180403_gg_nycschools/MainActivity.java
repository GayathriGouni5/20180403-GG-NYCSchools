package org.example.gayathri.a20180403_gg_nycschools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SchoolListAdapter schoolDataArrayAdapter;

    ListView lstSchoolData;
    TextView tvSchoolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstSchoolData = findViewById(R.id.lstSchools);
        tvSchoolList = findViewById(R.id.tvSchoolList);
        String url = "https://data.cityofnewyork.us/resource/97mf-9njv.json";
        fetchSchoolsData(url);
    }


    @Override
    protected void onStop() {
        super.onStop();
        NetworkSingleton.getInstance(this.getApplicationContext()).getRequestQueue().cancelAll(TAG);
    }

    private void fetchSchoolsData(String url){
        StringRequest messageRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                //Parsing data
                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<SchoolData>>(){}.getType();
                final ArrayList<SchoolData> details = gson.fromJson(response, collectionType);
                schoolDataArrayAdapter = new SchoolListAdapter(MainActivity.this,0,details);
                lstSchoolData.setAdapter(schoolDataArrayAdapter);
                tvSchoolList.setText(R.string.schoolsList);
                lstSchoolData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(MainActivity.this,SchoolDetailsActivity.class);
                        String dbn = "";
                        try {
                            dbn = schoolDataArrayAdapter.getItem(position).getDbn();

                        } catch (NullPointerException ex){
                            Log.e(TAG, "onItemClick: NullPointer Exception", ex);
                        } catch(Exception e){
                            Log.e(TAG, "onItemClick: Exception", e);
                        }
                        intent.putExtra("schooldbn", dbn);
                        startActivity(intent);
                    }
                });

                Log.d(TAG,details.get(0).getSchoolName()+"");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
                tvSchoolList.setText(R.string.error);

            }
        });

        NetworkSingleton.getInstance(this.getApplicationContext()).addToRequestQueue(messageRequest,TAG);
    }
}
