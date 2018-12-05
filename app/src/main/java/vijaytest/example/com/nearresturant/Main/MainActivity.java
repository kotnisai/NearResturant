package vijaytest.example.com.nearresturant.Main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vijaytest.example.com.nearresturant.Model.RestData;
import vijaytest.example.com.nearresturant.Model.SavedData;
import vijaytest.example.com.nearresturant.R;
import vijaytest.example.com.nearresturant.SQLiteDatabase.SavedDatabaseHelper;
import vijaytest.example.com.nearresturant.Utils.Constants;
import vijaytest.example.com.nearresturant.Volly.Helper;
import vijaytest.example.com.nearresturant.Volly.JsonParser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<RestData> restDataArrayList;
    RecyclerView restaurants_rv;
    LinearLayoutManager llm;
    EditText search_edt;
    ImageView serch_img;
    String radius = "500";
    Button get_btn;

    double latitude, longitude;

    LocationManager locationManager;

    private GpsTracker gpsTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Get current lattitude and longitude
        gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }
        init();
    }

    private void init() {
        //Initlize Widgets
        restaurants_rv = findViewById(R.id.restaurants_rv);
        search_edt = findViewById(R.id.search_edt);
        serch_img = findViewById(R.id.serch_img);
        get_btn = findViewById(R.id.get_btn);

        restDataArrayList = new ArrayList<>();
        //OnClickListner Callbacks
        serch_img.setOnClickListener(this);
        get_btn.setOnClickListener(this);
        //fetch data from places API
        fetchRestaurants();

    }

    public void fetchRestaurants() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/nearbysearch/json?");
        stringBuilder.append("location=" + latitude + "," + longitude);
        //stringBuilder.append("location=" + "17.443649" + "," + "78.445824");
        stringBuilder.append("&radius=" + radius + "&type=restaurant&key=" + Constants.API_KEY);
        callWebService(stringBuilder.toString());
    }

    private void callWebService(String stringBulider) {
        new JsonParser(MainActivity.this).parseVollyJsonObj(Constants.BASE_URL + stringBulider, 1, stringBulider, new Helper() {
            @Override
            public void backResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    //Add JsonArray Reponse into ArrayList
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RestData restData = new RestData();
                        JSONObject object = jsonArray.getJSONObject(i);
                        restData.setIcon(object.getString("icon"));
                        restData.setName(object.getString("name"));
                        restData.setVicinity(object.getString("vicinity"));
                        restDataArrayList.add(restData);
                    }

                    //Display data into Recyclerview
                    restaurants_rv.setHasFixedSize(true);
                    llm = new LinearLayoutManager(getApplicationContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    restaurants_rv.setLayoutManager(llm);

                    RestaurantRVAdapter restaurantRVAdapter = new RestaurantRVAdapter(MainActivity.this, restDataArrayList);
                    restaurants_rv.setAdapter(restaurantRVAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.serch_img) {
            //To change radious dynamicaly Enter Edittext Value
            radius = search_edt.getText().toString();
            if (radius.trim().equals("")) {
                Toast.makeText(this, "Please Enter Radious", Toast.LENGTH_SHORT).show();
            } else {
                restDataArrayList.clear();
                fetchRestaurants();
            }
        } else if (v.getId() == R.id.get_btn) {
            //Get The Saved details from SQLITE Database
            ArrayList<SavedData> notificationRpts = (ArrayList<SavedData>) new SavedDatabaseHelper(getApplicationContext()).getAllNotifications();
            if (notificationRpts.size() > 0) {
                Intent intent = new Intent(MainActivity.this, FavActivity.class);
                intent.putExtra("Result", notificationRpts);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You have no Favourites", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
