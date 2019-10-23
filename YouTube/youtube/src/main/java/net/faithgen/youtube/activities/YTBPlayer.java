package net.faithgen.youtube.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import net.faithgen.sdk.utils.Dialogs;
import net.faithgen.youtube.R;
import net.faithgen.youtube.YouTubeObject;
import net.faithgen.youtube.utils.Constants;
import net.faithgen.youtube.utils.Utils;
import net.innoflash.iosview.Toolbar;

public class YTBPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private String videoId;
    private YouTubePlayerView playerView;
    private final static int RECOVERY_DIALOG_REQUEST = 1;
    private String API_KEY = "AIzaSyC64i92ZZaZFE4-PyV_XgC2Zg3af5QuMyY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ytbplayer);

        videoId = getIntent().getStringExtra(YouTubeObject.VIDEO_ID);

        playerView = findViewById(R.id.playerView);

        playerView.initialize(getString(R.string.youtube_api), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError())
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        else
            Utils.openVideo(this, videoId);
        //  Dialogs.showOkDialog(this, youTubeInitializationResult.toString(), true);
    }
}
