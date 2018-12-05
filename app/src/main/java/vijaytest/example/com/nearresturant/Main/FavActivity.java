package vijaytest.example.com.nearresturant.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

import vijaytest.example.com.nearresturant.Model.SavedData;
import vijaytest.example.com.nearresturant.R;
import vijaytest.example.com.nearresturant.SQLiteDatabase.SavedAdapter;

public class FavActivity extends AppCompatActivity {
    ArrayList<SavedData> Rpt;
    RecyclerView savedrestaurants_rv;
    LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        Rpt = (ArrayList<SavedData>) getIntent().getSerializableExtra("Result");
        ArrayList<SavedData> DataArrayList = new ArrayList<>();
        for (int index = 0; index < Rpt.size(); index++) {
            SavedData notificationRpt = Rpt.get(index);
            SavedData ReportData = new SavedData();
            ReportData.setSno(String.valueOf(index + 1));
            ReportData.setImg(notificationRpt.getImg());
            ReportData.setName(notificationRpt.getName());
            ReportData.setAdd(notificationRpt.getAdd());
            DataArrayList.add(ReportData);
        }

        savedrestaurants_rv = findViewById(R.id.savedrestaurants_rv);
        savedrestaurants_rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        savedrestaurants_rv.setLayoutManager(llm);


        SavedAdapter restaurantRVAdapter = new SavedAdapter(FavActivity.this, DataArrayList);
        savedrestaurants_rv.setAdapter(restaurantRVAdapter);
    }
}
