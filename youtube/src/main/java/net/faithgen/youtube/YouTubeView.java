package net.faithgen.youtube;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import net.faithgen.sdk.SDK;
import net.innoflash.iosview.ChipView;

public class YouTubeView extends LinearLayout {

    private TextView videoCaptionTextView;
    private TextView videoTitleTextView;
    private ChipView chipView;
    private ImageView videoImageView;
    private String videoDescription;
    private String videoDate;
    private String videoTitle;
    private int videoImage;
    private YouTubeObject youTubeObject;

    public YouTubeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.single_youtube_item, this);
        videoCaptionTextView = findViewById(R.id.video_caption);
        chipView = findViewById(R.id.videoChip);
        videoImageView = findViewById(R.id.video_image);
        videoTitleTextView = findViewById(R.id.video_title);
    }

    public YouTubeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YouTubeView);
        readAttributes(typedArray);
        typedArray.recycle();
    }

    private void readAttributes(TypedArray typedArray) {
        setVideoTitle(typedArray.getString(R.styleable.YouTubeView_video_title));
        setVideoDescription(typedArray.getString(R.styleable.YouTubeView_video_caption));
        setVideoDate(typedArray.getString(R.styleable.YouTubeView_video_date));
        setVideoImage(typedArray.getResourceId(R.styleable.YouTubeView_video_image, R.drawable.ic_android_blue_24dp));

        if (SDK.getThemeColor() != null) {
            getVideoTitleTextView().setTextColor(Color.parseColor(SDK.getThemeColor()));
        }
    }

    public TextView getVideoTitleTextView() {
        return videoTitleTextView;
    }

    public TextView getVideoCaptionTextView() {
        return videoCaptionTextView;
    }

    public ChipView getChipView() {
        return chipView;
    }

    public ImageView getVideoImageView() {
        return videoImageView;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public String getVideoDate() {
        return videoDate;
    }

    public int getVideoImage() {
        return videoImage;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
        videoTitleTextView.setText(videoTitle);
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
        videoCaptionTextView.setText(videoDescription);
    }

    public void setVideoDate(String videoDate) {
        this.videoDate = videoDate;
        chipView.setChipText(videoDate);
    }

    public void setVideoImage(int videoImage) {
        this.videoImage = videoImage;
        videoImageView.setImageResource(videoImage);
    }

    public YouTubeObject getYouTubeObject() {
        return youTubeObject;
    }

    public void setYouTubeObject(YouTubeObject youTubeObject) {
        this.youTubeObject = youTubeObject;
        setVideoTitle(youTubeObject.getTitle());
        setVideoDescription(youTubeObject.getDescription());
        setVideoDate(youTubeObject.getDate());
        Picasso.get()
                .load(youTubeObject.getThumbnailUrl())
                .placeholder(R.drawable.youtube_logo)
                .error(R.drawable.youtube_logo)
                .into(getVideoImageView());
    }
}