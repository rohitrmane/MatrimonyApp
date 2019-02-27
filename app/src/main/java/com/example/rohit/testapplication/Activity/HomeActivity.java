package com.example.rohit.testapplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rohit.testapplication.Adapter.DataAdapter;
import com.example.rohit.testapplication.Model.UserProfile;
import com.example.rohit.testapplication.R;
import com.example.rohit.testapplication.Service.Service;
import com.example.rohit.testapplication.SqlLite.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    LinearLayout mainLayout;
    RecyclerView recyclerView;
    DataAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserProfile> profileList = new ArrayList<>();
    ArrayList<UserProfile> offlineDataList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        mainLayout = findViewById(R.id.mainLayout);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        databaseHelper =  new DatabaseHelper(HomeActivity.this);



        if(isNetworkAvailable()){
            new WebServiceHandler().execute(Service.URL);
        }
        else {
            showSnackbar("Please Turn on Internet");
            showOfflineData();
        }
    }

    private void showSnackbar(String msg){
        Snackbar.make(mainLayout,msg,Snackbar.LENGTH_SHORT).show();
    }

    private class WebServiceHandler extends AsyncTask<String, Void, String>{

        private ProgressDialog dialog = new ProgressDialog(HomeActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                String result = Service.getProfileData(urls[0]);
                return result;
            } catch (Exception e) {
                Log.d(HomeActivity.class.getSimpleName(), e.toString());
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            if(!TextUtils.isEmpty(result)){
                try {
                    databaseHelper.deleteData();
                    JSONObject json = new JSONObject(result);
                    JSONArray jsonArray = json.getJSONArray("results");
                    if(jsonArray.length()>0){
                        for(int i=0; i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String gender = jsonObject.getString("gender");
                            String email= jsonObject.getString("email");
                            String phone= jsonObject.getString("phone");
                            String cell = jsonObject.getString("cell");
                            JSONObject  nameObj =new JSONObject(jsonObject.getString("name"));
                            String completName = nameObj.getString("title")+" "+ nameObj.getString("first")+ " "+ nameObj.getString("last");
                            JSONObject  dobAgeObj =new JSONObject(jsonObject.getString("dob"));
                            String age = dobAgeObj.getString("age");
                            String dob = getProperDob(dobAgeObj.getString("date"));
                            JSONObject  locationObj =new JSONObject(jsonObject.getString("location"));
                            String location = locationObj.getString("street")+" , "+locationObj.getString("city")+" ,"+locationObj.getString("state");
                            JSONObject  pictureObj =new JSONObject(jsonObject.getString("picture"));
                            String largeImgUrl = pictureObj.getString("large");

                            profileList.add(new UserProfile(completName.toUpperCase(),gender,email,age,dob,largeImgUrl,phone,cell,location));
                            databaseHelper.insertDataTable(completName.toUpperCase(),gender,email,age,dob,largeImgUrl,phone,cell,location);
                        }
                        adapter = new DataAdapter(profileList,HomeActivity.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    Log.d(HomeActivity.class.getSimpleName(), e.toString());
                }
            }
        }
    }

    private String getProperDob(String dob){
        String[]formatDob = dob.split("T");
        return  formatDob[0];
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showOfflineData(){
        offlineDataList = databaseHelper.getDataTableDetails();
        if(offlineDataList.size()>0){
            adapter = new DataAdapter(offlineDataList,HomeActivity.this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
        else{
            repeatedInternetCheck();
        }
    }

    private void repeatedInternetCheck(){
        Snackbar.make(mainLayout,"Please Turn On Internet",Snackbar.LENGTH_INDEFINITE).setAction("OK",new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    new WebServiceHandler().execute(Service.URL);
                }
                else {
                    repeatedInternetCheck();
                }
            }
        }).show();
    }

}
