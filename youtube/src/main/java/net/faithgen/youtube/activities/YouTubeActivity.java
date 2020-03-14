package net.faithgen.youtube.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.faithgen.sdk.FaithGenActivity;
import net.faithgen.sdk.SDK;
import net.faithgen.sdk.enums.Subscription;
import net.faithgen.sdk.http.ErrorResponse;
import net.faithgen.sdk.http.FaithGenAPI;
import net.faithgen.sdk.http.types.ServerResponse;
import net.faithgen.sdk.singletons.GSONSingleton;
import net.faithgen.sdk.utils.Dialogs;
import net.faithgen.youtube.R;
import net.faithgen.youtube.YouTubeAdapter;
import net.faithgen.youtube.YouTubeObject;
import net.faithgen.youtube.YouTubeResponse;
import net.faithgen.youtube.utils.Constants;
import net.faithgen.youtube.utils.Utils;
import net.innoflash.iosview.recyclerview.RecyclerTouchListener;
import net.innoflash.iosview.recyclerview.RecyclerViewClickListener;
import net.innoflash.iosview.swipelib.SwipeRefreshLayout;

import java.util.List;

import br.com.liveo.searchliveo.SearchLiveo;

public class YouTubeActivity extends FaithGenActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchLiveo searchLiveo;
    private RecyclerView videosListView;
    private YouTubeAdapter youTubeAdapter;
    private List<YouTubeObject> youTubeObjects;
    private YouTubeResponse youTubeResponse;
    private Intent intent;
    private String filterText = "";
    private FaithGenAPI faithGenAPI;

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

        faithGenAPI = new FaithGenAPI(this);
        swipeRefreshLayout = findViewById(R.id.videosSwiper);
        videosListView = findViewById(R.id.videosList);

        searchLiveo = findViewById(R.id.search_liveo);
        searchLiveo.with(this, charSequence -> {
            filterText = (String) charSequence;
            faithGenAPI.cancelRequests();
            loadYouTubeVideos(null, true);
        })
                .showVoice()
                .hideKeyboardAfterSearch()
                .hideSearch(() -> {
                    getToolbar().setVisibility(View.VISIBLE);
                    if (!filterText.isEmpty()) {
                        filterText = "";
                        searchLiveo.text(filterText);
                        loadYouTubeVideos(null, true);
                    }
                })
                .build();


        swipeRefreshLayout.setPullPosition(Gravity.BOTTOM);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.ios_blue);

        videosListView.setLayoutManager(new LinearLayoutManager(this));
        videosListView.addOnItemTouchListener(new RecyclerTouchListener(this, videosListView, this));

        setOnOptionsClicked(R.drawable.ic_search_blue18dp, view -> {
            searchLiveo.setVisibility(View.VISIBLE);
            searchLiveo.show();
            getToolbar().setVisibility(View.GONE);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        faithGenAPI.cancelRequests();
    }

    private ServerResponse getServerResponse(boolean reload) {
        return new ServerResponse() {
            @Override
            public void onServerResponse(String serverResponse) {
                youTubeResponse = GSONSingleton.Companion.getInstance().getGson().fromJson(serverResponse, YouTubeResponse.class);
                if (youTubeObjects == null || youTubeObjects.size() == 0 || reload)
                    youTubeObjects = youTubeResponse.getItems();
                else
                    youTubeObjects.addAll(youTubeResponse.getItems());

                if (videosListView.getAdapter() == null || videosListView.getAdapter().getItemCount() == 0 || reload) {
                    youTubeAdapter = new YouTubeAdapter(YouTubeActivity.this, youTubeObjects);
                    videosListView.setAdapter(youTubeAdapter);
                } else
                    youTubeAdapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                if (youTubeResponse == null)
                    Dialogs.showOkDialog(YouTubeActivity.this, net.faithgen.sdk.utils.Constants.SERVER_ERROR, true);
                swipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    @Override
    public void onRefresh() {
        if (SDK.getSubscription().equals(Subscription.Free)) {
            swipeRefreshLayout.setRefreshing(false);
            Dialogs.showOkDialog(this, Constants.VIDEOS_IN_FREE_MODE, false);
        } else loadYouTubeVideos(youTubeResponse.getNextPageToken(), false);
    }

    private void loadYouTubeVideos(final String pageToken, boolean reload) {
        faithGenAPI
                .setProcess(Constants.FETCHING_VIDEOS)
                .setServerResponse(getServerResponse(reload))
                .request(Utils.getYouTubeUrl(pageToken, filterText));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (youTubeObjects == null || youTubeObjects.size() == 0)
            loadYouTubeVideos(null, true);
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
//https://www.googleapis.com/youtube/v3/search?key=AIzaSyC64i92ZZaZFE4-PyV_XgC2Zg3af5QuMyY&channelId=UCbP2HeYGC3kfHjHLMPplZuQ&part=snippet,id&order=date&maxResults=20&type=video&q=build
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == SearchLiveo.REQUEST_CODE_SPEECH_INPUT) {
                searchLiveo.resultVoice(requestCode, resultCode, data);
            }
        }
    }
}
