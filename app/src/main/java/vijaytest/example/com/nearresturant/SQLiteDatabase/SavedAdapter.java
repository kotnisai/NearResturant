package vijaytest.example.com.nearresturant.SQLiteDatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vijaytest.example.com.nearresturant.Model.SavedData;
import vijaytest.example.com.nearresturant.R;

/**
 * Created by saiprasanthk on 04-12-2018.
 */

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SaveAdapterViewHolder> {
    Context context;
    ArrayList<SavedData> savedDataArrayList;

    public SavedAdapter(Context context, ArrayList<SavedData> savedDataArrayList) {
        this.context = context;
        this.savedDataArrayList = savedDataArrayList;
    }

    @NonNull
    @Override
    public SaveAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_adapter, parent, false);
        return new SaveAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveAdapterViewHolder holder, int position) {
        final SavedData data = savedDataArrayList.get(position);
        Picasso.get().load(data.getImg()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
                .resize(150, 120).into(holder.savedrestaurant_item_image);
        holder.savedrestaurant_item_name.setText(data.getName());
        holder.savedrestaurant_item_vicinity.setText(data.getAdd());
    }

    @Override
    public int getItemCount() {
        return savedDataArrayList.size();
    }

    public static class SaveAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView savedrestaurant_item_image;
        TextView savedrestaurant_item_name, savedrestaurant_item_vicinity;

        public SaveAdapterViewHolder(View itemView) {
            super(itemView);
            savedrestaurant_item_image = itemView.findViewById(R.id.savedrestaurant_item_image);
            savedrestaurant_item_name = itemView.findViewById(R.id.savedrestaurant_item_name);
            savedrestaurant_item_vicinity = itemView.findViewById(R.id.savedrestaurant_item_vicinity);
        }
    }
}
