package pub.sharecode.leon.locationlogger.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import pub.sharecode.leon.locationlogger.R;
import pub.sharecode.leon.locationlogger.adapter.MyHistoryAdapter;
import pub.sharecode.leon.locationlogger.model.LocationData;
import pub.sharecode.leon.locationlogger.utils.TimeUtils;

public class LocationHistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private MyHistoryAdapter myHistoryAdapter;
    private List<LocationData> locationDatas;
    private SwipeRefreshLayout mSwiperefreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);
        initView();
    }

    private void initView() {
        setTitle("定位记录");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        locationDatas = DataSupport.where("time < ?", TimeUtils.getNowMills() + "")
                .order("time DESC").limit(20).find(LocationData.class);
        myHistoryAdapter = new MyHistoryAdapter(this, locationDatas);
        mRecyclerView.setAdapter(myHistoryAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }
                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {

                        long t = locationDatas.get(lastPosition).getTime();
                        List<LocationData> data = DataSupport.where("time < ?", t + "")
                                .order("time DESC").limit(20).find(LocationData.class);
                        if (data.size() > 0) {
                            Toast.makeText(LocationHistoryActivity.this, "已经加载更多", Toast.LENGTH_SHORT).show();
                            locationDatas.addAll(data);
                            myHistoryAdapter.notifyDataSetChanged();
                        } else
                            Toast.makeText(LocationHistoryActivity.this, "已经全部加载完毕", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
        mSwiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mSwiperefreshlayout.setColorSchemeResources(android.R.color.holo_blue_light,
                +android.R.color.holo_red_light, android.R.color.holo_orange_light,
                +android.R.color.holo_green_light);
        mSwiperefreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                locationDatas.clear();
                locationDatas.addAll(DataSupport.where("time < ?", TimeUtils.getNowMills() + "")
                        .order("time DESC").limit(20).find(LocationData.class));
                myHistoryAdapter.notifyDataSetChanged();
                mSwiperefreshlayout.setRefreshing(false);
            }
        }, (long) (Math.random() * 200));

    }
}
