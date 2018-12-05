package vijaytest.example.com.nearresturant.Main;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vijaytest.example.com.nearresturant.Model.RestData;
import vijaytest.example.com.nearresturant.Model.SavedData;
import vijaytest.example.com.nearresturant.R;
import vijaytest.example.com.nearresturant.SQLiteDatabase.SavedDatabaseHelper;

/**
 * Created by saiprasanthk on 03-12-2018.
 */

public class RestaurantRVAdapter extends RecyclerView.Adapter<RestaurantRVAdapter.RestViewHolder> {
    private Context context;
    private ArrayList<RestData> restDataArrayList;

    public RestaurantRVAdapter(Context context, ArrayList<RestData> restDataArrayList) {
        this.context = context;
        this.restDataArrayList = restDataArrayList;
    }

    @NonNull
    @Override
    public RestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestViewHolder holder, int position) {
        final RestData data = restDataArrayList.get(position);
        Picasso.get().load(data.getIcon()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
                .resize(150, 120).into(holder.restaurant_item_image);
        holder.restaurant_item_name.setText(data.getName());
        holder.restaurant_item_vicinity.setText(data.getVicinity());
        holder.fav_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.fav_image.setBackgroundColor(Color.YELLOW);
                //Saved Favouriets When click
                SavedData savedData = new SavedData();
                savedData.setImg(data.getIcon());
                savedData.setName(data.getName());
                savedData.setAdd(data.getVicinity());
                savedData.setAdd(data.getVicinity());
                savedData.setId("1");
                SavedDatabaseHelper savedDatabaseHelper = new SavedDatabaseHelper(context);
                savedDatabaseHelper.addNotification(savedData);
                Toast.makeText(context, "Item added into favourites", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restDataArrayList.size();
    }

    public static class RestViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurant_item_image, fav_image;
        TextView restaurant_item_name, restaurant_item_vicinity;

        public RestViewHolder(View itemView) {
            super(itemView);
            restaurant_item_image = itemView.findViewById(R.id.restaurant_item_image);
            restaurant_item_name = itemView.findViewById(R.id.restaurant_item_name);
            restaurant_item_vicinity = itemView.findViewById(R.id.restaurant_item_vicinity);
            fav_image = itemView.findViewById(R.id.fav_image);


        }
    }

}
