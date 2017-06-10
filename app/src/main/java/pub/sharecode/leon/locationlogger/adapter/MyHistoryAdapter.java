package pub.sharecode.leon.locationlogger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pub.sharecode.leon.locationlogger.R;
import pub.sharecode.leon.locationlogger.model.LocationData;
import pub.sharecode.leon.locationlogger.utils.TimeUtils;

/**
 * Created by leon on 17-6-9.
 */

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.ViewHolder> {
    private final Context mContext;
    private final List<LocationData> locationDataList;

    public MyHistoryAdapter(Context context, List<LocationData> locationDataList) {
        this.mContext = context;
        this.locationDataList = locationDataList;
    }

    @Override
    public MyHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_info, parent, false);
        return new MyHistoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHistoryAdapter.ViewHolder holder, int position) {
        holder.mTvLatitude.setText("" + locationDataList.get(position).getLatitude());
        holder.mTvLongitude.setText("" + locationDataList.get(position).getLongitude());
        holder.mTvSpeed.setText("" + locationDataList.get(position).getSpeed());
        holder.mTvTime.setText(TimeUtils.millis2String(locationDataList.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return locationDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView mTvTime;
        TextView mTvLongitude;
        TextView mTvLatitude;
        TextView mTvSpeed;

        ViewHolder(View itemview) {
            super(itemview);
            this.view = itemview;
            this.mTvTime = (TextView) itemview.findViewById(R.id.tv_time);
            this.mTvLongitude = (TextView) itemview.findViewById(R.id.tv_longitude);
            this.mTvLatitude = (TextView) itemview.findViewById(R.id.tv_latitude);
            this.mTvSpeed = (TextView) itemview.findViewById(R.id.tv_speed);
        }
    }
}
