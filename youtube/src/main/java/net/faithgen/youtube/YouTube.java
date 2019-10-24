package net.faithgen.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.faithgen.sdk.FaithGenActivity;
import net.faithgen.sdk.SDK;
import net.faithgen.sdk.enums.Subscription;
import net.faithgen.sdk.http.API;
import net.faithgen.sdk.http.ErrorResponse;
import net.faithgen.sdk.http.types.ServerResponse;
import net.faithgen.sdk.singletons.GSONSingleton;
import net.faithgen.sdk.utils.Dialogs;
import net.faithgen.youtube.activities.YTBPlayer;
import net.faithgen.youtube.utils.Constants;
import net.faithgen.youtube.utils.Utils;
import net.innoflash.iosview.recyclerview.RecyclerTouchListener;
import net.innoflash.iosview.recyclerview.RecyclerViewClickListener;
import net.innoflash.iosview.swipelib.SwipeRefreshLayout;

import java.util.List;

public class YouTube extends FaithGenActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView videosListView;
    private YouTubeAdapter youTubeAdapter;
    private List<YouTubeObject> youTubeObjects;
    private YouTubeResponse youTubeResponse;
    private Intent intent;

    @Override
    public String getPageTitle() {
        return Constants.MODULE_NAME;
    }

    @Override
    public int getPageIcon() {
        return R.drawable.youtube;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        swipeRefreshLayout = findViewById(R.id.videosSwiper);
        videosListView = findViewById(R.id.videosList);

        swipeRefreshLayout.setPullPosition(Gravity.BOTTOM);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.ios_blue);

        videosListView.setLayoutManager(new LinearLayoutManager(this));
        videosListView.addOnItemTouchListener(new RecyclerTouchListener(this, videosListView, this));
    }

    @Override
    public void onRefresh() {
        if (SDK.getSubscription().equals(Subscription.Free)) {
            swipeRefreshLayout.setRefreshing(false);
            Dialogs.showOkDialog(this, Constants.VIDEOS_IN_FREE_MODE, false);
        } else loadYouTubeVideos(youTubeResponse.getNextPageToken());
    }

    private void loadYouTubeVideos(String pageToken) {
        API.get(this, Utils.getYouTubeUrl(pageToken), null, false, new ServerResponse() {
            @Override
            public void onServerResponse(String serverResponse) {
                Log.d("Tage", "onServerResponse: " + serverResponse);
                youTubeResponse = GSONSingleton.getInstance().getGson().fromJson(serverResponse, YouTubeResponse.class);
                if (youTubeObjects == null || youTubeObjects.size() == 0)
                    youTubeObjects = youTubeResponse.getItems();
                else
                    youTubeObjects.addAll(youTubeResponse.getItems());
                if (videosListView.getAdapter() == null || videosListView.getAdapter().getItemCount() == 0) {
                    youTubeAdapter = new YouTubeAdapter(YouTube.this, youTubeObjects);
                    videosListView.setAdapter(youTubeAdapter);
                } else {
                    youTubeAdapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                try {
                    super.onError(errorResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (youTubeObjects == null || youTubeObjects.size() == 0)
            loadYouTubeVideos(null);
    }

    @Override
    public void onClick(View view, int position) {
        if (SDK.getSubscription().equals(Subscription.Free))
            Utils.openVideo(this, youTubeObjects.get(position).getVideoId());
        else {
            intent = new Intent(this, YTBPlayer.class);
            intent.putExtra(YouTubeObject.VIDEO_ID, youTubeObjects.get(position).getVideoId());
            startActivity(intent);
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
